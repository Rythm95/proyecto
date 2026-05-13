package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Grupo {

	@Id
	private String codigo;

	private int curso;

	@ManyToOne
	private Ciclo ciclo;

	@ManyToOne
	private Profesor coordinador;

	private String yearAcademico;

	@OneToMany(mappedBy = "grupo")
	private List<Alumno> alumnos = new ArrayList<>();

	@OneToMany(mappedBy = "grupo")
	private List<ModuloGrupo> modulos;

	public Grupo() {
	}

	public Grupo(String codigo, int curso, Ciclo ciclo) {
		this.codigo = codigo;
		this.curso = curso;
		this.ciclo = ciclo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public Ciclo getCiclo() {
		return ciclo;
	}

	public void setCiclo(Ciclo ciclo) {
		this.ciclo = ciclo;
	}

	public Profesor getCoordinador() {
		return coordinador;
	}

	public void setCoordinador(Profesor coordinador) {
		this.coordinador = coordinador;
	}

	public String getYearAcademico() {
		return yearAcademico;
	}

	public void setYearAcademico(String yearAcademico) {
		this.yearAcademico = yearAcademico;
	}

	public List<Alumno> getAlumnos() {
		return alumnos;
	}

	public void setAlumnos(List<Alumno> alumnos) {
		this.alumnos = alumnos;
	}

	public List<ModuloGrupo> getModulos() {
		return modulos;
	}

	public void setModulos(List<ModuloGrupo> modulos) {
		this.modulos = modulos;
	}
	
	@Override
	public String toString() {
		return codigo;
	}
}
