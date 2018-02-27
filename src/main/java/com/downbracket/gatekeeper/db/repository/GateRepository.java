package com.downbracket.gatekeeper.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.downbracket.gatekeeper.db.entity.Gate;

public interface GateRepository extends JpaRepository<Gate, Long> {

	Gate findByUniqueId( String gateId ) ;
}