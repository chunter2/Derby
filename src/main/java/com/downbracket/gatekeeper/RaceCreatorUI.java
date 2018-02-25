package com.downbracket.gatekeeper;

import java.util.Map;
import java.util.stream.Collectors;

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

    public static final String TOPIC_RACE = "gate/race";

	Button bnCreate;
	
	GateMqttClient client ;

	@Autowired
	public RaceCreatorUI() {
		
		client = new GateMqttClient() ;
	    this.bnCreate = new Button( "Create race", event -> createRace() ) ;
	    
	}

	private void createRace() {
		 
		 RaceData race = RaceFactory.createRace( 3 ) ;
		 
		 Map<Long,Long> map = race.getLanes().stream().collect( Collectors.toMap( LaneData::getLaneId, LaneData::getTime ) ) ;
 

		String json;
		try {
			json = new ObjectMapper().writeValueAsString(map);
			System.out.println(json);
			
			client.sendMessageToTopic( json, TOPIC_RACE ) ;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}

	@Override
	protected void init(VaadinRequest request) {
	    setContent(bnCreate);
	}

}