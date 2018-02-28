package com.downbracket.gatekeeper.mq;

import java.io.IOException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.Gate;
import com.downbracket.gatekeeper.db.entity.factory.RaceFactory;
import com.downbracket.gatekeeper.db.repository.RaceDataRepository;
import com.downbracket.gatekeeper.db.service.GateService;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class RaceToDbSubscriber implements MqttCallback
{
	private static final Logger log = LoggerFactory.getLogger(RaceToDbSubscriber.class);

    public static final String SUBSCRIBER_NAME = "subscriber-race2db";

	@Autowired
	RaceDataRepository repo ;
	
	@Autowired
	GateService gateService ;
	
	private MqttClient client ;
	
	private ObjectMapper om = new ObjectMapper() ;

	@PostConstruct
	public void init()
	{
		getMqttClient() ;
	}

	private synchronized MqttClient getMqttClient()
	{
		if( client == null )
		{
			log.info( "initializer Race2DbSubscriber");
			try {
				client = new MqttClient( MqConfig.BROKER_URL, SUBSCRIBER_NAME ) ;
				client.setCallback( this );
				client.connect();
				client.subscribe( MqConfig.RACE_TOPIC_NAME );	
			} catch (MqttException e) {
				log.error( "MqttException initializing connection: " + e.getMessage(), e );
			}
		}
		
		return client ;
	}
	
	
    @Override
    public void connectionLost(Throwable cause) {
    	log.warn( "connectionLost()!", cause );
    }

    protected static String getGateId( String topic )
    {
    	int indexStar = MqConfig.RACE_TOPIC_NAME.indexOf( '+' ) ;
    	
    	return topic.substring( indexStar, topic.length() - MqConfig.RACE_TOPIC_NAME.length() + indexStar + 1) ;
    }
    
    @Override
    public void messageArrived(String topic, MqttMessage message)
    {
         log.info("Message arrived. Topic: {}, Message: {}", topic, message ); 
         
         Gate gate = gateService.getOrCreateGate( getGateId( topic ), "mq gate" ) ;
         
         try {
        	 Map<String,Map<Long,Long>> map = om.readValue(message.toString(), new TypeReference<Map<String,Map<Long,Long>>>(){});
			 
        	 map.entrySet().stream().forEach( entry -> repo.save( RaceFactory.createRace( gate, entry.getKey(), entry.getValue() ) ) );

         } catch (JsonParseException e) {
        	 log.error( "JsonParseException reading message: " + e.getMessage(), e );
         } catch (JsonMappingException e) {
        	 log.error( "JsonMappingException reading message: " + e.getMessage(), e );
         } catch (IOException e) {
        	 log.error( "IOException reading message: " + e.getMessage(), e );
         }

         if ("home/LWT".equals(topic))
         {
 			log.error( "Sensor gone!" );
         }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) 
    {
    	log.info( "deliveryComplete({})", token.getMessageId() );
    	
    }


}