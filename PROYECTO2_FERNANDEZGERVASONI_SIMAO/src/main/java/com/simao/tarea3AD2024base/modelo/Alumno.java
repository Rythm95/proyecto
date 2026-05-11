/**
* Clase Alumno.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumno")
public class Alumno extends Persona {

	@Column(nullable = false)
	private String ciclo;

	private boolean mayoriaEdad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idTutor")
	private Tutor tutorEmpresa;

	public Alumno() {
	}

	public Alumno(String email, String user, String password, String nombre, String ciclo, boolean mayoriaEdad) {
		super(email, user, password, nombre, Perfil.ALUMNADO);
		this.ciclo = ciclo;
		this.mayoriaEdad = mayoriaEdad;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public boolean isMayoriaEdad() {
		return mayoriaEdad;
	}

	public void setMayoriaEdad(boolean mayoriaEdad) {
		this.mayoriaEdad = mayoriaEdad;
	}

	public Tutor getTutorEmpresa() {
		return tutorEmpresa;
	}

	public void setTutorEmpresa(Tutor tutorEmpresa) {
		this.tutorEmpresa = tutorEmpresa;
	}
}