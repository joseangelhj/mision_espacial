package com.mision.espacial.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Modelo de datos para una misión espacial")
@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único de la misión", example = "1")
    private Long id;

    @Schema(description = "Nombre de la misión", required = true)
    private String name;
    @Schema(description = "Estado de la misión (Pendiente, En curso, Completada, Fallida)", example = "Pendiente")
    private String status; // Pendiente, En curso, Completada, Fallida
    @Schema(description = "Fecha de lanzamiento de la misión", example = "2025-03-12T12:00:00")
    private LocalDateTime launchDate;

    @OneToMany(mappedBy = "mission", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Astronaut> astronauts;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public LocalDateTime getLaunchDate() {
		return launchDate;
	}

	public void setLaunchDate(LocalDateTime launchDate) {
		this.launchDate = launchDate;
	}

	public List<Astronaut> getAstronauts() {
		return astronauts;
	}

	public void setAstronauts(List<Astronaut> astronauts) {
		this.astronauts = astronauts;
	}

}

