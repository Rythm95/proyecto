package com.simao.tarea3AD2024base.modelo;

import java.time.LocalDate;

import jakarta.persistence.Column;
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
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "formacion_id")
	private FormacionEmpresa formacion;

	private LocalDate fecha;

	private String descripcion;

	private boolean justificada;

	private String justificante;

	public Falta() {
	}

	public Falta(FormacionEmpresa formacion, LocalDate fecha, String descripcion, boolean justificada) {
		this.formacion = formacion;
		this.fecha = fecha;
		this.descripcion = descripcion;
		this.justificada = justificada;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FormacionEmpresa getFormacion() {
		return formacion;
	}

	public void setFormacion(FormacionEmpresa fe) {
		this.formacion = fe;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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