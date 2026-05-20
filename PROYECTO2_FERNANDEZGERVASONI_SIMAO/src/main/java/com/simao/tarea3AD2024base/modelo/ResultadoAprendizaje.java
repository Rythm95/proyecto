package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Clase ResultadoAprendizaje.java
 * 
 * Representa un Resultado de Aprendizaje de un {@link Modulo}.
 * 
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Modulo
 */

@Entity
public class ResultadoAprendizaje {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	private String codigo;

	private String descripcion;

	@ManyToOne
	private Modulo modulo;

	public ResultadoAprendizaje() {
	}

	public ResultadoAprendizaje(String codigo, String descripcion, Modulo modulo) {
		this.codigo = codigo;
		this.descripcion = descripcion;
		this.modulo = modulo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}
	
	@Override
	public String toString() {
		return codigo;
	}
}
