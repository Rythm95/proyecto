package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "tutor_curso", uniqueConstraints = @UniqueConstraint(columnNames = { "idTutor", "idCurso" }))
public class TutorCurso {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idTutor", nullable = false)
	private Tutor tutor;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idCurso", nullable = false)
	private Curso curso;

	public TutorCurso() {
	}

	public TutorCurso(Tutor tutor, Curso curso) {
		this.tutor = tutor;
		this.curso = curso;
	}

	public Long getId() {
		return id;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}
}
