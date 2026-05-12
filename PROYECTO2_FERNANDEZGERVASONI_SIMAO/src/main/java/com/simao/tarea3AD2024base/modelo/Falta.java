package com.simao.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Falta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idFE")
	private FormacionEmpresa asignacion;

	private LocalDate fecha;

	private boolean justificada;

	private String justificante;

	public Falta() {
	}

	public Falta(FormacionEmpresa asignacion, LocalDate fecha, boolean justificada) {
		this.asignacion = asignacion;
		this.fecha = fecha;
		this.justificada = justificada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormacionEmpresa getAsignacion() {
		return asignacion;
	}

	public void setAsignacion(FormacionEmpresa a) {
		this.asignacion = a;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public boolean isJustificada() {
		return justificada;
	}

	public void setJustificada(boolean justificada) {
		this.justificada = justificada;
	}

	public String getJustificante() {
		return justificante;
	}

	public void setJustificante(String justificante) {
		this.justificante = justificante;
	}
}
