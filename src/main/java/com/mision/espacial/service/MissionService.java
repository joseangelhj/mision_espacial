package com.mision.espacial.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mision.espacial.exception.ResourceNotFoundException;
import com.mision.espacial.model.Mission;
import com.mision.espacial.repository.MissionRepository;

@Service
public class MissionService {

    @Autowired
    private MissionRepository missionRepository;


    @Transactional
    public Mission createMission(Mission mission) {
        mission.setStatus("Pendiente");
        mission.setLaunchDate(LocalDateTime.now());
        return missionRepository.save(mission);
    }

    @Transactional
    public Mission updateMissionStatus(Long missionId, String status) {
        Optional<Mission> missionOpt = missionRepository.findById(missionId);
        if (missionOpt.isPresent()) {
            Mission mission = missionOpt.get();
            mission.setStatus(status);
            return missionRepository.save(mission);
        }
        throw new ResourceNotFoundException("Mission not found");
    }

    @Async
    @Transactional
    public void simulateMissionLaunch(Long missionId) {
        try {
            // Cambiar el estado de "Pendiente" a "En curso" después de 5 segundos
            Thread.sleep(5000);
            updateMissionStatus(missionId, "En curso");

            // Cambiar el estado a "Completada" o "Fallida" después de 10 segundos más
            Thread.sleep(10000);
            Random rand = new Random();
            String status = rand.nextInt(100) < 80 ? "Completada" : "Fallida";
            updateMissionStatus(missionId, status);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public List<Mission> getMissionsByStatus(String status) {
        return missionRepository.findByStatus(status);
    }

    public List<Mission> getMissionsByDate(LocalDateTime date) {
        return missionRepository.findByLaunchDate(date);
    }
    
    @Transactional
    public void deleteMission(Long id) {
        Mission mission = missionRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Mission not found"));
        missionRepository.delete(mission);
    }
}

