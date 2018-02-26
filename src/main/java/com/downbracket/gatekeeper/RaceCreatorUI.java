package com.downbracket.gatekeeper;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.downbracket.gatekeeper.db.entity.LaneData;
import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.entity.factory.RaceFactory;
import com.downbracket.gatekeeper.mq.client.GateMqttClient;
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

    public static final String TOPIC_RACE = "gate/race";

	Button bnCreate;
	
	GateMqttClient client ;

	@Autowired
	public RaceCreatorUI() {
		
		log.info( "creating RaceCreatorUI");
		
		client = new GateMqttClient() ;
	    this.bnCreate = new Button( "Create race", event -> createRace() ) ;
	    
	}

	private void createRace() {

		log.info( "createRace() button pressed");

		RaceData race = RaceFactory.createRace( 3 ) ;

		Map<Long,Long> lmap = race.getLanes().stream().collect( Collectors.toMap( LaneData::getLaneId, LaneData::getTime ) ) ;
		Map<String,Map<Long,Long>> map = new HashMap<>() ;
		map.put( UUID.randomUUID().toString(), lmap ) ;
		
		log.debug( "generating json for race data...");

		try {
			String json = new ObjectMapper().writeValueAsString(map);
			log.debug( "send json message to topic...");
			client.sendMessageToTopic( json, TOPIC_RACE ) ;
		} catch (JsonProcessingException e) {
			log.error( "JsonProcessingException: " + e.getMessage(), e );
		}


	}

	@Override
	protected void init(VaadinRequest request) {
		
		log.info( "initializing RaceCreatorUI");
	    setContent(bnCreate);
	}

}