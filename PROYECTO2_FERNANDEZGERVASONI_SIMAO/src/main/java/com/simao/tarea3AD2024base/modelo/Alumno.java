package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Clase Alumno.java
 *
 * Un alumno hereda de {@link Persona} y se asocia a un único curso. Además
 * almacena si es mayor de edad o no.
 *
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Persona
 */

@Entity
public class Alumno extends Persona {

	private boolean mayoriaEdad;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idCurso")
	private Curso curso;

	public Alumno() {
	}

	public Alumno(String email, String user, String password, String nombre, boolean mayoriaEdad) {
		super(email, user, password, nombre, Perfil.ALUMNADO);
		this.mayoriaEdad = mayoriaEdad;
	}

	public boolean isMayoriaEdad() {
		return mayoriaEdad;
	}

	public void setMayoriaEdad(boolean mayoriaEdad) {
		this.mayoriaEdad = mayoriaEdad;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return nombre;
	}
}