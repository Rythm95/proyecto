package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Clase EvaluacionRa.java
 * 
 * Permite evaluar cada {@link ResultadoAprendizaje} asociado a una
 * {@link FormacionEmpresa}.
 * 
 * @author Simao Fernández Gervasoni
 * @version 1.0
 * @see FormacionEmpresa
 * @see ResultadoAprendizaje
 */

@Entity
public class EvaluacionRa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "formacion_id")
	private FormacionEmpresa formacion;

	@ManyToOne
	private ResultadoAprendizaje resultadoAprendizaje;

	@Enumerated(EnumType.STRING)
	private EstadoRA estado = EstadoRA.PENDIENTE;

	public EvaluacionRa() {
	}

	public EvaluacionRa(FormacionEmpresa formacion, ResultadoAprendizaje resultadoAprendizaje, EstadoRA estado) {
		this.formacion = formacion;
		this.resultadoAprendizaje = resultadoAprendizaje;
		this.estado = estado;
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

	public void setFormacion(FormacionEmpresa formacion) {
		this.formacion = formacion;
	}

	public ResultadoAprendizaje getResultadoAprendizaje() {
		return resultadoAprendizaje;
	}

	public void setResultadoAprendizaje(ResultadoAprendizaje resultadoAprendizaje) {
		this.resultadoAprendizaje = resultadoAprendizaje;
	}

	public EstadoRA getEstado() {
		return estado;
	}

	public void setEstado(EstadoRA estado) {
		this.estado = estado;
	}
}
