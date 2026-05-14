package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Curso {

	@Id
	private String codigo;

	private String nombre;

	@ManyToOne
	private Ciclo ciclo;

	@ManyToOne
	private Profesor coordinador;

	private String yearAcademico;

	@OneToMany(mappedBy = "curso")
	private List<Alumno> alumnos = new ArrayList<>();

	@OneToMany(mappedBy = "curso")
	private List<ModuloCurso> modulos;

	public Curso() {
	}

	public Curso(String codigo, String nombre, Ciclo ciclo) {
		this.codigo = codigo;
		this.nombre = nombre;
		this.ciclo = ciclo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public List<ModuloCurso> getModulos() {
		return modulos;
	}

	public void setModulos(List<ModuloCurso> modulos) {
		this.modulos = modulos;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
