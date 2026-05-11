/**
* Clase Profesor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "profesor")
public class Profesor extends Persona {

	@Column(name = "ciclos")
	private String ciclos;

	@OneToMany(mappedBy = "profesor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Curso> cursos = new ArrayList<>();

	public Profesor() {
	}

	public Profesor(String email, String user, String password, String nombre, String ciclos) {
		super(email, user, password, nombre, Perfil.PROFESORADO);
		this.ciclos = ciclos;
	}

	public String getCiclos() {
		return ciclos;
	}

	public void setCiclos(String ciclos) {
		this.ciclos = ciclos;
	}

	public List<Curso> getCursos() {
		return cursos;
	}
}
