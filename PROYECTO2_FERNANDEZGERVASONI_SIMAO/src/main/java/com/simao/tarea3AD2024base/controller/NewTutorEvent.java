package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Tutor;

/**
 * Clase NewTutorEvent.java
 * 
 * Maneja los eventos que indican que se ha actualizado la tabla de Tutores en la base de datos.
 */
public class NewTutorEvent {
	private final Tutor tutor;
	
	public NewTutorEvent(Tutor tutor) {
		this.tutor = tutor;
	}

	public Tutor getTutor() {
		return tutor;
	}
}
