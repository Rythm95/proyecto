package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Curso;

/**
 * Clase NewCursoEvent.java
 * 
 * Maneja los eventos que indican que se ha actualizado la tabla de Cursos en la base de datos.
 */
public class NewCursoEvent {
	private final Curso curso;

	public NewCursoEvent(Curso curso) {
		this.curso = curso;
	}

	public Curso getCurso() {
		return curso;
	}
}
