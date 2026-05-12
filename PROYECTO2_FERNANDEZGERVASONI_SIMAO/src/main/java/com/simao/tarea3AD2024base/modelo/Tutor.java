/**
* Clase Tutor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Tutor extends Persona {

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	@OneToMany(mappedBy = "tutor", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<FormacionEmpresa> asignaciones = new ArrayList<>();

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

	public List<FormacionEmpresa> getAsignaciones() {
		return asignaciones;
	}

	public void setAsignaciones(List<FormacionEmpresa> asignaciones) {
		this.asignaciones = asignaciones;
	}

}
