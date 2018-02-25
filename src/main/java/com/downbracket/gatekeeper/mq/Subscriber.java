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

import com.downbracket.gatekeeper.db.entity.LaneData;
import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.repository.RaceRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.spring.annotation.SpringComponent;

@SpringComponent
public class Subscriber implements MqttCallback
{
	private static final Logger log = LoggerFactory.getLogger(Subscriber.class);

    public static final String BROKER_URL = "tcp://localhost:1883";

	@Autowired
	RaceRepository repo ;
	
	MqttClient client ;

	@PostConstruct
	public void init()
	{
		log.info( "initializer Subscriber");
		try {
			client = new MqttClient( BROKER_URL, "racesub" ) ;
			client.setCallback( this );
			client.connect();
			client.subscribe("gate/race");	
		} catch (MqttException e) {
			log.error( "MqttException initializing connection: " + e.getMessage(), e );
		}
		
	}

	
    @Override
    public void connectionLost(Throwable cause) {
    	log.warn( "connectionLost()!", cause );
    	
    }

    @Override
    public void messageArrived(String topic, MqttMessage message)
    {
         log.info("Message arrived. Topic: {}, Message: {}", topic, message ); 
         
         try {
			Map<Long,Long> map = new ObjectMapper().readValue(message.toString(), new TypeReference<Map<Long,Long>>(){});
			
			RaceData race = new RaceData() ;
			
			map.entrySet().stream().forEach( m -> race.addLane( new LaneData( m.getKey(), m.getValue() ) ) );

			repo.save( race ) ;
			
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
    	log.info( "deliveryComplete(" + token.getMessageId() + ")" );
    	
    }


}