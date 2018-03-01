package com.downbracket.gatekeeper;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.downbracket.gatekeeper.db.entity.Car;
import com.downbracket.gatekeeper.db.entity.factory.CarFactory;
import com.downbracket.gatekeeper.db.repository.CarRepository;

@SpringBootApplication
public class GateKeeperApplication {

	private static final Logger log = LoggerFactory.getLogger(GateKeeperApplication.class);

	public static void main(String[] args) {
		log.info( "Creating SpringApplication for GateKeeper");
		SpringApplication.run(GateKeeperApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner loadCarData(CarRepository carRepository) {
		log.info( "loadCarData into repo");
		return args -> 	
		{		
			Collection<Car> cars = CarFactory.createCars( 16 ) ;
			cars.stream().forEach( carRepository::save ) ;
		};
	}

}
