package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Alumno;

/**
 * Clase NewAlumnoEvent.java
 * 
 * Maneja los eventos que indican que se ha actualizado la tabla de Alumnos en la base de datos.
 */
public class NewAlumnoEvent {
	private final Alumno alumno;

	public NewAlumnoEvent(Alumno alumno) {
		this.alumno = alumno;
	}

	public Alumno getAlumno() {
		return alumno;
	}
}
