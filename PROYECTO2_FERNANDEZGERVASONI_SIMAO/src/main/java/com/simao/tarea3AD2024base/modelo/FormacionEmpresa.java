package com.simao.tarea3AD2024base.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

@Entity
public class FormacionEmpresa {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	private Alumno alumno;

	@ManyToOne
	private Empresa empresa;

	@ManyToOne
	private Profesor tutorCentro;

	@ManyToOne
	private Tutor tutorEmpresa;

	private LocalDate fechaIni;

	private LocalDate fechaFin;

	private EstadoFE estado = EstadoFE.PENDIENTE;

	private Double calificacion;

	@OneToMany(mappedBy = "asignacion", cascade = CascadeType.ALL, orphanRemoval = true)
	@OrderBy("fecha ASC")
	private List<Falta> faltas = new ArrayList<>();

	public FormacionEmpresa() {
	}

	public FormacionEmpresa(Alumno alumno, Empresa empresa, Profesor tutorCentro, Tutor tutorEmpresa,
			LocalDate fechaIni, LocalDate fechaFin) {
		this.alumno = alumno;
		this.empresa = empresa;
		this.tutorCentro = tutorCentro;
		this.tutorEmpresa = tutorEmpresa;
		this.fechaIni = fechaIni;
		this.fechaFin = fechaFin;
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

	public Profesor getTutorCentro() {
		return tutorCentro;
	}

	public void setTutorCentro(Profesor tutorCentro) {
		this.tutorCentro = tutorCentro;
	}

	public Tutor getTutorEmpresa() {
		return tutorEmpresa;
	}

	public void setTutorEmpresa(Tutor tutorEmpresa) {
		this.tutorEmpresa = tutorEmpresa;
	}

	public LocalDate getFechaIni() {
		return fechaIni;
	}

	public void setFechaIni(LocalDate fechaIni) {
		this.fechaIni = fechaIni;
	}

	public LocalDate getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDate fechaFin) {
		this.fechaFin = fechaFin;
	}

	public EstadoFE getEstado() {
		return estado;
	}

	public void setEstado(EstadoFE estado) {
		this.estado = estado;
	}

	public Double getCalificacion() {
		return calificacion;
	}

	public void setCalificacion(Double calificacion) {
		this.calificacion = calificacion;
	}

	public List<Falta> getFaltas() {
		return faltas;
	}

	public void setFaltas(List<Falta> faltas) {
		this.faltas = faltas;
	}
}