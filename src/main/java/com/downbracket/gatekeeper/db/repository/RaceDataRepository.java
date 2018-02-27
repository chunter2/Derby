package com.downbracket.gatekeeper.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.downbracket.gatekeeper.db.entity.RaceData;

public interface RaceDataRepository extends JpaRepository<RaceData, Long> {

	List<RaceData> findByGateUniqueId( String uniqueId ) ;

}