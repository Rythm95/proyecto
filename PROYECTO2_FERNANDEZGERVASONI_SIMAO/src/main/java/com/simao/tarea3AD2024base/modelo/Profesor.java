package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

/**
 * Clase Profesor.java
 *
 * Un profesor hereda de {@link Persona} y puede impartir varios
 * módulos dentro de distintos cursos.
 *
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Persona
 * @see ModuloCurso
 */

@Entity
public class Profesor extends Persona {

	@OneToMany(mappedBy = "profesor")
	private List<ModuloCurso> modulosCurso = new ArrayList<>();

	public Profesor() {
	}

	public Profesor(String email, String user, String password, String nombre) {
		super(email, user, password, nombre, Perfil.PROFESORADO);
	}

	public List<ModuloCurso> getModulos() {
		return modulosCurso;
	}

	public void setModulos(List<ModuloCurso> modulosCurso) {
		this.modulosCurso = modulosCurso;
	}

	@Override
	public String toString() {
		return nombre;
	}

}
