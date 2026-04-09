/**
* Clase Profesor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

// Meter un bool Activo/NoActivo para dar de baja 
public class Profesor extends Persona {

	private String[] ciclos;

	public Profesor(int id, String email, String user, String password, String nombre, Perfil perfil, String[] ciclos) {
		super(id, email, user, password, nombre, perfil);
		this.ciclos = ciclos;
	}

	public String[] getCiclos() {
		return ciclos;
	}

	public void setCiclos(String[] ciclos) {
		this.ciclos = ciclos;
	}

}
