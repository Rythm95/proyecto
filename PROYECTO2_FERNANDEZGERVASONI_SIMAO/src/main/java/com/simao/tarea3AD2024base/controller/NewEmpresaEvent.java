package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Empresa;

public class NewEmpresaEvent {
	private final Empresa empresa;
	
	public NewEmpresaEvent(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
}
