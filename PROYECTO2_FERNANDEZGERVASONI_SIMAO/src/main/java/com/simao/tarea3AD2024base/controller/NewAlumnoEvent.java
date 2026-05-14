package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Alumno;

public class NewAlumnoEvent {
	private final Alumno alumno;

	public NewAlumnoEvent(Alumno alumno) {
		this.alumno = alumno;
	}

	public Alumno getAlumno() {
		return alumno;
	}
}
