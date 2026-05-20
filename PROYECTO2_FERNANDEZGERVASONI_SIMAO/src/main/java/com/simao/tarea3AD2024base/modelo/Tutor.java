package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

/**
 * Clase Tutor.java
 *
 * Un tutor hereda de {@link Persona} y está asociado/a a una {@link Empresa}.
 *
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Persona
 * @see Empresa
 */

@Entity
public class Tutor extends Persona {

	@ManyToOne
	private Empresa empresa;

	public Tutor() {
	}

	public Tutor(String email, String user, String password, String nombre, Empresa empresa) {
		super(email, user, password, nombre, Perfil.TUTOR);
		this.empresa = empresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
