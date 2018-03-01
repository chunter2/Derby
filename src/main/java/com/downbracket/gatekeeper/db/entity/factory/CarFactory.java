package com.downbracket.gatekeeper.db.entity.factory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downbracket.gatekeeper.db.entity.Car;

public class CarFactory {

	private static final Logger log = LoggerFactory.getLogger(CarFactory.class);

	static Random r = new Random() ;

	static List<String> names ;
	
	private CarFactory()
	{
		// hidden constructor
	}

	public static Car createCar( )
	{
		log.info( "createCar" );
		
		Car car = new Car() ;
		car.setUniqueId( UUID.randomUUID().toString() );
		car.setName( getRandomName() );		
		
		log.info( "created new car: {}", car );
		
		return car ;
	}

	public static Collection<Car> createCars( int count )
	{
		log.info( "createCars( count={} )", count );
		
		return IntStream.range(0, count).mapToObj(i -> createCar() ).collect(Collectors.toList());
	}

	private static synchronized List<String> getCarNames( ) 
	{
		if( names == null )
		{
	    	InputStream is = CarFactory.class.getResourceAsStream("carnames.txt") ;
	
			// get the input as a list of strings.
			names = new BufferedReader(new InputStreamReader( is )).lines().collect(Collectors.toList());
		}
		
		return names ;
	}

	protected static String getRandomName() {

		List<String> n = getCarNames() ;
		return n.get( r.nextInt( n.size() ) ) + " " + n.get( r.nextInt( n.size() ) ) ;
	}
	
}
