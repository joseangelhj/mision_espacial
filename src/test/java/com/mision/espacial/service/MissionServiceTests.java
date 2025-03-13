package com.mision.espacial.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.mision.espacial.model.Astronaut;
import com.mision.espacial.model.Mission;

@SpringBootTest
public class MissionServiceTests {

    @Autowired
    private MissionService missionService;
    
    @Autowired
    private AstronautService astronautService;

    @Test
    public void testCreateMission() {
        Mission mission = new Mission();
        mission.setName("Luna 1");

        Mission createdMission = missionService.createMission(mission);

        assertNotNull(createdMission);
        assertEquals("Pendiente", createdMission.getStatus());
    }
    
    @Test
    public void testCreateAstronaut() {
        Astronaut astronaut = new Astronaut();
        astronaut.setName("John tech");

        Astronaut createdAstronaut = astronautService.createAstronaut(astronaut, 1L);
        assertNotNull(createdAstronaut);
    }
}
