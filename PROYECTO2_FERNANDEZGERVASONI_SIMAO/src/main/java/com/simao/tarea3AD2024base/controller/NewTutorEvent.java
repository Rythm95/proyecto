package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Tutor;

public class NewTutorEvent {
	private final Tutor tutor;
	
	public NewTutorEvent(Tutor tutor) {
		this.tutor = tutor;
	}

	public Tutor getTutor() {
		return tutor;
	}
}
