package com.downbracket.gatekeeper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.downbracket.gatekeeper.db.entity.RaceData;
import com.downbracket.gatekeeper.db.entity.factory.RaceFactory;
import com.downbracket.gatekeeper.db.repository.RaceRepository;

@SpringBootApplication
public class GateKeeperApplication {

	private static final Logger log = LoggerFactory.getLogger(GateKeeperApplication.class);

	public static void main(String[] args) {
		log.info( "Creating SpringApplication for GateKeeper");
		SpringApplication.run(GateKeeperApplication.class, args);
	}


	@Bean
	public CommandLineRunner loadData(RaceRepository repository) {
		log.info( "loadData into repo");
		return (args) -> {
			// save a couple of races
			repository.save( RaceFactory.createRace( 3 ) ) ;
			repository.save( RaceFactory.createRace( 3 ) ) ;
			repository.save( RaceFactory.createRace( 3 ) ) ;
			repository.save( RaceFactory.createRace( 3 ) ) ;
			repository.save( RaceFactory.createRace( 3 ) ) ;

/*			// fetch all races
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
*/
};
	}
}
