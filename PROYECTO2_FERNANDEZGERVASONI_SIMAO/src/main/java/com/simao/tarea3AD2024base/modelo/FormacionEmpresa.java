package com.simao.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class FormacionEmpresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idAlumno")
	private Alumno alumno;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idEmpresa")
	private Empresa empresa;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "idTutor")
	private Tutor tutor;

	private LocalDate fechaIni;

	private LocalDate fechaFin;

	private Double calificacion;

	@OneToMany(mappedBy = "asignacion", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("fecha ASC")
	private List<Falta> faltas = new ArrayList<>();

	public FormacionEmpresa() {
	}

	public FormacionEmpresa(Alumno alumno, Empresa empresa, Tutor tutor, LocalDate fechaInicio) {
		this.alumno = alumno;
		this.empresa = empresa;
		this.tutor = tutor;
		this.fechaIni = fechaInicio;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Alumno getAlumno() {
		return alumno;
	}

	public void setAlumno(Alumno alumno) {
		this.alumno = alumno;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

	public Tutor getTutor() {
		return tutor;
	}

	public void setTutor(Tutor tutor) {
		this.tutor = tutor;
	}

	public LocalDate getFechaInicio() {
		return fechaIni;
	}

	public void setFechaInicio(LocalDate f) {
		this.fechaIni = f;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate f) {
		this.fechaFin = f;
	}

	public Double getNotaFinal() {
		return calificacion;
	}

	public void setNotaFinal(Double notaFinal) {
		this.calificacion = notaFinal;
	}

	public List<Falta> getFaltas() {
		return faltas;
	}

	public boolean isActiva() {
		return fechaFin == null;
	}
}