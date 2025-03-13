package com.mision.espacial.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mision.espacial.exception.ResourceNotFoundException;
import com.mision.espacial.model.Astronaut;
import com.mision.espacial.model.Mission;
import com.mision.espacial.repository.AstronautRepository;
import com.mision.espacial.repository.MissionRepository;

@Service
public class AstronautService {

    @Autowired
    private AstronautRepository astronautRepository;

    @Autowired
    private MissionRepository missionRepository;

    @Transactional
    public Astronaut createAstronaut(Astronaut astronaut, Long missionId) {
        if (!astronaut.getName().contains("tech")) {
            throw new IllegalArgumentException("Astronaut name must contain 'tech'");
        }

        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));

        astronaut.setMission(mission);
        return astronautRepository.save(astronaut);
    }

    public List<Astronaut> getAstronautsByMission(Long missionId) {
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new ResourceNotFoundException("Mission not found"));
        return astronautRepository.findByMission(mission);
    }
    
    public Astronaut getAstronaut(Long astronautId) {
        return astronautRepository.findById(astronautId)
                .orElseThrow(() -> new ResourceNotFoundException("Astronaut not found"));
    }
    
    public Astronaut updateAstronaut(Long astronautId, Astronaut updatedAstronaut) {
        Astronaut astronaut = astronautRepository.findById(astronautId)
                .orElseThrow(() -> new ResourceNotFoundException("Astronaut not found"));
        astronaut.setName(updatedAstronaut.getName());
        astronaut.setMission(updatedAstronaut.getMission());  // Reasignar misión si es necesario
        return astronautRepository.save(astronaut);
    }
    
    public void deleteAstronaut(Long astronautId) {
        Astronaut astronaut = astronautRepository.findById(astronautId)
                .orElseThrow(() -> new ResourceNotFoundException("Astronaut not found"));
        astronautRepository.delete(astronaut);
    }
}

