package com.downbracket.gatekeeper.db.entity.factory;

import java.util.Calendar;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downbracket.gatekeeper.db.entity.Gate;
import com.downbracket.gatekeeper.db.entity.LaneData;
import com.downbracket.gatekeeper.db.entity.RaceData;

public class RaceFactory {

	private static final Logger log = LoggerFactory.getLogger(RaceFactory.class);

	static Random r = new Random() ;
	
	private RaceFactory()
	{
		// hidden constructor
	}

	public static RaceData createRace( Gate gate, int count )
	{
		return createRace( gate, count, 4000, 1000 ) ;
	}
	
	private static long getTime( int meanms, int stddev )
	{
		double d = r.nextGaussian() * stddev + meanms ;
		return (long)d;
	}
	
	public static RaceData createRace( Gate gate, int count, int meanms, int stddev ) 
	{
		log.info( "createRace(gateId={}, count={}, meanms={}, stddev={})", 
				gate.getUniqueId(), count, meanms, stddev);
		
		RaceData race = new RaceData() ;
		race.setGate(gate);
		race.setUniqueId( UUID.randomUUID().toString() );
		race.setTimeStamp( Calendar.getInstance().getTime() ) ;
		
		for( long index = 0 ; index < count ; index++ )
			race.addLane( new LaneData( index, getTime(meanms, stddev)+index*stddev/10 ));
		
		log.info( "created new race: {}", race );
		
		return race ;
	}

	public static RaceData createRace( Gate gate, String key, Map<Long, Long> value) {
		RaceData race = new RaceData( gate, key ) ;
		
		value.entrySet().stream().forEach( m -> race.addLane( new LaneData( m.getKey(), m.getValue() ) ) );
		
		return race ;
	}
}
