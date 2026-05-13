/**
* Clase Profesor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

@Entity
public class Profesor extends Persona {

	@OneToMany(mappedBy = "profesor")
	private List<ModuloGrupo> modulosGrupo = new ArrayList<>();

	public Profesor() {
	}

	public Profesor(String email, String user, String password, String nombre) {
		super(email, user, password, nombre, Perfil.PROFESORADO);
	}

	public List<ModuloGrupo> getModulos() {
		return modulosGrupo;
	}

	public void setModulos(List<ModuloGrupo> modulosGrupo) {
		this.modulosGrupo = modulosGrupo;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
