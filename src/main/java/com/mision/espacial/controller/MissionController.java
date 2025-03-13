package com.mision.espacial.controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
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
import org.springframework.web.bind.annotation.RestController;

import com.mision.espacial.model.Mission;
import com.mision.espacial.repository.MissionRepository;
import com.mision.espacial.service.MissionService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/missions")
public class MissionController {

    @Autowired
    private MissionService missionService;
    
    @Autowired
    private MissionRepository missionRepository;

    @Operation(summary = "Crear una nueva misión", description = "Crea una nueva misión con estado inicial 'Pendiente'.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Misión creada con éxito"),
            @ApiResponse(responseCode = "400", description = "Solicitud incorrecta"),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor")
        })
    @PostMapping
    public ResponseEntity<Mission> createMission(@RequestBody Mission mission) {
        Mission createdMission = missionService.createMission(mission);
        // Iniciar simulación en segundo plano
        missionService.simulateMissionLaunch(createdMission.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMission);
    }
    
    @Operation(summary = "Obtener una misión por ID", description = "Recupera los detalles de una misión específica utilizando su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Misión encontrada"),
        @ApiResponse(responseCode = "404", description = "Misión no encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Mission> getMission(@PathVariable Long id) {
        return missionRepository.findById(id)
                .map(mission -> ResponseEntity.ok(mission))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Mission> updateMissionStatus(@PathVariable Long id, @RequestParam String status) {
        Mission updatedMission = missionService.updateMissionStatus(id, status);
        return ResponseEntity.ok(updatedMission);
    }

    @GetMapping("/status")
    public ResponseEntity<List<Mission>> getMissionsByStatus(@RequestParam String status) {
        List<Mission> missions = missionService.getMissionsByStatus(status);
        return ResponseEntity.ok(missions);
    }

    @GetMapping("/date")
    public ResponseEntity<List<Mission>> getMissionsByDate(@RequestParam String date) {
    	try {
    		LocalDateTime dateTime = LocalDateTime.parse(date);
    		List<Mission> missions = missionService.getMissionsByDate(dateTime);
            return ResponseEntity.ok(missions);
    	} catch (DateTimeParseException e) {
    		return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    	}    
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
    	if (missionRepository.existsById(id)) {
            missionRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
