package com.mision.espacial.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mision.espacial.model.Astronaut;
import com.mision.espacial.service.AstronautService;

@RestController
@RequestMapping("/astronauts")
public class AstronautController {

    @Autowired
    private AstronautService astronautService;

    @PostMapping
    public ResponseEntity<Astronaut> createAstronaut(@RequestBody Astronaut astronaut, @RequestParam Long missionId) {
    	try {
	        Astronaut createdAstronaut = astronautService.createAstronaut(astronaut, missionId);
	        return ResponseEntity.status(HttpStatus.CREATED).body(createdAstronaut);
    	} catch (IllegalArgumentException ex) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    	}
    }

    @GetMapping("/mission/{missionId}")
    public ResponseEntity<List<Astronaut>> getAstronautsByMission(@PathVariable Long missionId) {
        List<Astronaut> astronauts = astronautService.getAstronautsByMission(missionId);
        return ResponseEntity.ok(astronauts);
    }
    
    @GetMapping("/{id}")
    public Astronaut getAstronaut(@PathVariable Long id) {
        return astronautService.getAstronaut(id);
    }
    
    @PutMapping("/{id}")
    public Astronaut updateAstronaut(@PathVariable Long id, @RequestBody Astronaut updatedAstronaut) {
        return astronautService.updateAstronaut(id, updatedAstronaut);
    }
    
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAstronaut(@PathVariable Long id) {
        astronautService.deleteAstronaut(id);
    }
}

