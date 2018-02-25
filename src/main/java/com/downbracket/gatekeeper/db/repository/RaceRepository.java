package com.downbracket.gatekeeper.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.downbracket.gatekeeper.db.entity.RaceData;

public interface RaceRepository extends JpaRepository<RaceData, Long> {

//	List<Race> findByLastNameStartsWithIgnoreCase(String lastName);
}