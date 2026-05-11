package com.simao.tarea3AD2024base.modelo;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "curso")
public class Curso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(unique = true, nullable = false)
	private String codigo;

	@Column(nullable = false)
	private String ciclo;

	@Column(nullable = false)
	private int curso;

	@Column(name = "yearAcademico", nullable = false)
	private String yearAcademico;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idProfesor")
	private Profesor profesor;

	@OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TutorCurso> asignaciones = new ArrayList<>();

	public Curso() {
	}

	public Curso(String codigo, String ciclo, int curso, String anioAcademico) {
		this.codigo = codigo;
		this.ciclo = ciclo;
		this.curso = curso;
		this.yearAcademico = anioAcademico;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public int getCurso() {
		return curso;
	}

	public void setCurso(int curso) {
		this.curso = curso;
	}

	public String getAnioAcademico() {
		return yearAcademico;
	}

	public void setAnioAcademico(String anioAcademico) {
		this.yearAcademico = anioAcademico;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}

	public List<TutorCurso> getAsignaciones() {
		return asignaciones;
	}
}
