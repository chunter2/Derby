package com.downbracket.gatekeeper.db.entity.factory;

import java.util.Calendar;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downbracket.gatekeeper.db.entity.LaneData;
import com.downbracket.gatekeeper.db.entity.RaceData;

public class RaceFactory {

	private static final Logger log = LoggerFactory.getLogger(RaceFactory.class);

	static Random r = new Random() ;
	
	private RaceFactory()
	{
		// hidden constructor
	}

	public static RaceData createRace( int count )
	{
		return createRace( count, 4000, 1000 ) ;
	}
	
	private static long getTime( int meanms, int stddev )
	{
		double d = r.nextGaussian() * stddev + meanms ;
		return (long)d;
	}
	
	public static RaceData createRace( int count, int meanms, int stddev ) 
	{
		log.info( "createRace(count={}, meanms={}, stddev={})", count, meanms, stddev);
		
		RaceData race = new RaceData() ;
//		race.setId( UUID.randomUUID().toString() );
		race.setTimeStamp( Calendar.getInstance().getTime() ) ;
		
		for( long index = 0 ; index < count ; index++ )
			race.addLane( new LaneData( index, getTime(meanms, stddev) ));
		
		log.info( "created new race: {}", race );
		
		return race ;
	}
}
