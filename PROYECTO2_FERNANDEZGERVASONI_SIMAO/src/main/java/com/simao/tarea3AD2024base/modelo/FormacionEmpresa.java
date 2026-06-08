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

/**
 * Clase FormacionEmpresa.java
 * 
 * Gestiona la relación entre el {@link Alumno}, la {@link Empresa} en la que
 * hará prácticas, además del {@link Profesor} tutor del centro y el
 * {@link Tutor} de empresa, en un {@link Periodo} de prácticas, durante unas
 * fechas.
 * 
 * También se almacenan el {@link EstadoFE} de la formación profesional y las
 * {@link EvaluacionRa} de los RAs de los módulos del grupo del alumno.
 * 
 * @author Simao Fernández Gervasoni
 * @version 1.0
 * @see Alumno
 * @see Empresa
 * @see Profesor
 * @see Tutor
 * @see Periodo
 * @see EstadoFE
 * @see EvaluacionRa
 */

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

	private Periodo periodo;

	private LocalDate fechaIni;

	private LocalDate fechaFin;

	private EstadoFE estado = EstadoFE.PENDIENTE;

	@OneToMany(mappedBy = "formacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<EvaluacionRa> evaluaciones = new ArrayList<>();

	@OneToMany(mappedBy = "formacion", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Falta> faltas = new ArrayList<>();

	public FormacionEmpresa() {
	}

	public FormacionEmpresa(Alumno alumno, Empresa empresa, Profesor tutorCentro, Tutor tutorEmpresa, Periodo periodo,
			LocalDate fechaIni, LocalDate fechaFin) {
		this.alumno = alumno;
		this.empresa = empresa;
		this.tutorCentro = tutorCentro;
		this.tutorEmpresa = tutorEmpresa;
		this.periodo = periodo;
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

	public Periodo getPeriodo() {
		return periodo;
	}

	public void setPeriodo(Periodo periodo) {
		this.periodo = periodo;
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

	public List<EvaluacionRa> getEvaluaciones() {
		return evaluaciones;
	}

	public void setEvaluaciones(List<EvaluacionRa> evaluaciones) {
		this.evaluaciones = evaluaciones;
	}

	public void addEvaluacion(EvaluacionRa evaluacion) {
		evaluaciones.add(evaluacion);
		evaluacion.setFormacion(this);
	}

	public List<Falta> getFaltas() {
		return faltas;
	}

	public void setFaltas(List<Falta> faltas) {
		this.faltas = faltas;
	}
	
	public void addFalta(Falta falta) {
		faltas.add(falta);
		falta.setFormacion(this);
	}

	@Override
	public String toString() {
		return alumno.getNombre();
	}

}