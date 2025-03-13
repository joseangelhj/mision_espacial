package com.mision.espacial.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mision.espacial.model.Astronaut;
import com.mision.espacial.model.Mission;

@Repository
public interface AstronautRepository extends JpaRepository<Astronaut, Long> {
    List<Astronaut> findByMission(Mission mission);
}

