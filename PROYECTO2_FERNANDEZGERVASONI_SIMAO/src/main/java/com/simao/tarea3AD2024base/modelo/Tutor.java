/**
* Clase Tutor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

public class Tutor extends Persona {

	private Empresa empresa;

	public Tutor(int id, String email, String user, String password, String nombre, Perfil perfil, Empresa empresa) {
		super(id, email, user, password, nombre, perfil);
		this.empresa = empresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
