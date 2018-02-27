package com.downbracket.gatekeeper.mq;

import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Test;

public class RaceToDbSubscriberTest {

	@Test
	public void testGetGateId() {
		
		RaceToDbSubscriber r2d = new RaceToDbSubscriber() ;
		
		String uuid = UUID.randomUUID().toString() ;
		
		String topic = MqConfig.RACE_TOPIC_NAME.replace("*", uuid ) ;
		
		assertEquals( uuid, r2d.getGateId( topic ) ) ;
	}

}
