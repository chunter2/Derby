package com.downbracket.gatekeeper.ui;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.eclipse.paho.client.mqttv3.MqttSecurityException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.Gate;
import com.downbracket.gatekeeper.db.entity.LaneData;
import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.entity.factory.RaceFactory;
import com.downbracket.gatekeeper.db.service.GateService;
import com.downbracket.gatekeeper.mq.MqConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Button;
import com.vaadin.ui.UI;

@SpringUI(path="/racecreator")
@Theme("valo")
public class RaceCreatorUI extends UI {

	private static final Logger log = LoggerFactory.getLogger(RaceCreatorUI.class);

	public static final String GATE_UUID = "eb4c5c2c-fb51-4f05-836f-05af4cb8acda" ;

	Button bnCreate;

    private MqttClient client;
	
	@Autowired
	GateService gateService;

	Gate gate ;
	
	@Autowired
	public RaceCreatorUI() {
		
		log.info( "creating RaceCreatorUI");
		
	    this.bnCreate = new Button( "Create race", event -> createRace() ) ;
	    
		connect() ; 
	}
	
	private Gate getGate()
	{
		return gateService.getOrCreateGate( GATE_UUID, "UI Gate" ) ;
	}

	private void createRace() {

		log.info( "createRace() button pressed");

		RaceData race = RaceFactory.createRace( getGate(), 3 ) ;

		Map<Long,Long> lmap = race.getLanes().stream().collect( Collectors.toMap( LaneData::getLaneId, LaneData::getTime ) ) ;
		Map<String,Map<Long,Long>> map = new HashMap<>() ;
		map.put( UUID.randomUUID().toString(), lmap ) ;
		
		String topicName = MqConfig.RACE_TOPIC_NAME.replace("+", GATE_UUID);
		
		log.debug( "generating json for race data...");

		try {
			String json = new ObjectMapper().writeValueAsString(map);
			log.debug( "send json message to topic <{}>...", topicName );
			sendMessageToTopic( json, topicName ) ;
		} catch (JsonProcessingException e) {
			log.error( "JsonProcessingException: " + e.getMessage(), e );
		}
	}

	@Override
	protected void init(VaadinRequest request) {
		
		log.info( "initializing RaceCreatorUI");
	    setContent(bnCreate);
	}

    private MqttClient getMqttClient()
    {
    	if( client == null )
    	{
    		String clientId = "gate-" + GATE_UUID ;
    		try
    		{
    			client = new MqttClient(MqConfig.BROKER_URL, clientId);
    		}
    		catch (MqttException e)
    		{
    			log.error( "MqttException creating MqttClient: " + e.getMessage(), e );
    		}		 
    	}
    	return client ;
    }

    private void connect()
    {

    	MqttConnectOptions options = new MqttConnectOptions();
    	options.setCleanSession(false);

    	try {
    		getMqttClient().connect(options);
    	} catch (MqttSecurityException e) {
    		log.error( "MqttSecurityException connecting: " + e.getMessage(), e );
    	} catch (MqttException e) {
    		log.error( "MqttException connecting: " + e.getMessage(), e );
    	}
    }

    private void sendMessageToTopic( String message, String topic ) {

    	final MqttTopic temperatureTopic = getMqttClient().getTopic(topic);
    	try {
    		temperatureTopic.publish(new MqttMessage(message.getBytes()));
    	} catch (MqttPersistenceException e) {
    		log.error( "MqttPersistenceException publishing to topic: " + e.getMessage(), e );
    	} catch (MqttException e) {
    		log.error( "MqttException publishing to topic: " + e.getMessage(), e );
    	}
    }

	
}