package com.downbracket.gatekeeper.db.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.downbracket.gatekeeper.db.entity.Gate;
import com.downbracket.gatekeeper.db.repository.GateRepository;

@Repository
@Transactional
public class GateService {

	private static final Logger log = LoggerFactory.getLogger(GateService.class);
	
	@Autowired
	GateRepository gateRepository ;

	public Gate getOrCreateGate( String id, String name ) 
	{
		log.info( "getOrCreateGate(id={},name={})", id, name );
		
		Gate gate = gateRepository.findByUniqueId( id ) ;

		if( gate == null )
		{
			log.info( "gate {} does not exist, creating it...");
			gate = new Gate( id, name ) ;
			gateRepository.save( gate ) ;
		}

		return gate;
	}

}
