/**
* Clase Tutor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

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

}
