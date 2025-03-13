package com.mision.espacial.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos para un astronauta")
@Entity
public class Astronaut {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único del astronauta", example = "1")
    private Long id;

    @Schema(description = "Nombre del astronauta", required = true, example = "John tech")
    private String name;

    @ManyToOne
    @JoinColumn(name = "mission_id")
    @Schema(description = "Misión asignada al astronauta")
    @JsonBackReference
    private Mission mission;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Mission getMission() {
		return mission;
	}

	public void setMission(Mission mission) {
		this.mission = mission;
	}

    
}

