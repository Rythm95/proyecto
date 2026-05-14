package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Profesor;

public class NewProfesorEvent {
	private final Profesor profesor;
	
	public NewProfesorEvent(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public Profesor getProfesor() {
		return profesor;
	}
}
