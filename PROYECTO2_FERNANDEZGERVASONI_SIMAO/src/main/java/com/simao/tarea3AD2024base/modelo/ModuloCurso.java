package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

/**
 * Clase ModuloCurso.java
 * 
 * Esta clase actúa como entidad intermedia entre {@link Modulo} y
 * {@link Curso}, incluyendo además la relación con el {@link Profesor} que lo
 * imparte.
 * 
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Modulo
 * @see Curso
 * @see Profesor
 */

@Entity
public class ModuloCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	private Modulo modulo;

	@ManyToOne
	private Curso curso;

	@ManyToOne
	private Profesor profesor;

	public ModuloCurso() {

	}

	public ModuloCurso(Modulo modulo, Curso curso, Profesor profesor) {
		this.modulo = modulo;
		this.curso = curso;
		this.profesor = profesor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Modulo getModulo() {
		return modulo;
	}

	public void setModulo(Modulo modulo) {
		this.modulo = modulo;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
}
