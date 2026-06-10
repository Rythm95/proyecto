package com.simao.tarea3AD2024base.controller;

import com.simao.tarea3AD2024base.modelo.Empresa;

/**
 * Clase NewEmpresaEvent.java
 * 
 * Maneja los eventos que indican que se ha actualizado la tabla de Empresas en la base de datos.
 */
public class NewEmpresaEvent {
	private final Empresa empresa;
	
	public NewEmpresaEvent(Empresa empresa) {
		this.empresa = empresa;
	}
	
	public Empresa getEmpresa() {
		return empresa;
	}
}
