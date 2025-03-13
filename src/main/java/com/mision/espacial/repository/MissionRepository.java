package com.mision.espacial.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mision.espacial.model.Mission;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long> {
    List<Mission> findByStatus(String status);
    List<Mission> findByLaunchDate(LocalDateTime launchDate);
}
