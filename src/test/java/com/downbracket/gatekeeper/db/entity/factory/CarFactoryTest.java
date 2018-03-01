package com.downbracket.gatekeeper.db.entity.factory;

import java.util.Collection;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.downbracket.gatekeeper.db.entity.Car;

public class CarFactoryTest {

	private static final Logger log = LoggerFactory.getLogger(CarFactoryTest.class);

	@Test
	public void testSanityOfRandomizer() {
		
		log.info( "testSanityOfRandomizer()" ) ;
		
		String rname = CarFactory.getRandomName() ;
		
		log.info( "got random name: {}", rname );

	}


	@Test
	public void testSanityOfListRandomizer() {
		
		log.info( "testSanityOfListRandomizer()" ) ;
		
		Collection<Car> rname = CarFactory.createCars( 16 ) ;
		
		log.info( "got random name: {}", rname );

	}

}
