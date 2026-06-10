package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Profesor;

/**
 * Clase NewProfesorEvent.java
 * 
 * Maneja los eventos que indican que se ha actualizado la tabla de Profesores en la base de datos.
 */
public class NewProfesorEvent {
	private final Profesor profesor;
	
	public NewProfesorEvent(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Profesor getProfesor() {
		return profesor;
	}
}
