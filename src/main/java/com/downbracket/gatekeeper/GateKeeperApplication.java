package com.downbracket.gatekeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GateKeeperApplication {

	private static final Logger log = LoggerFactory.getLogger(GateKeeperApplication.class);

	public static void main(String[] args) {
		log.info( "Creating SpringApplication for GateKeeper");
		SpringApplication.run(GateKeeperApplication.class, args);
	}


/*	@Bean
	public CommandLineRunner loadData(GateRepository gateRepository, RaceDataRepository raceRepository) {
		log.info( "loadData into repo");
		return (args) -> {
			
			Gate gate = RaceCreatorUI.getGate() ;
			gateRepository.save( gate ) ; 
			
			// save a couple of races
			for( int index = 0 ; index < 2 ; index ++ )
			{
				RaceData race = RaceFactory.createRace( gate, 3 ) ;
				raceRepository.save( race ) ;
			}
			// fetch all races
			log.info("Races found with findAll():");
			log.info("-------------------------------");
			for (RaceData race : repository.findAll()) {
				log.info(race.toString());
			}
			log.info("");

			// fetch an individual race by ID
			RaceData race = repository.findOne(1L);
			log.info("Race found with findOne(1L):");
			log.info("--------------------------------");
			log.info(race.toString());
			log.info("");

};
	}
*/
	
}
