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
}
