package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.repositorios.FormacionEmpresaRepository;

import jakarta.transaction.Transactional;

@Service
public class FormacionEmpresaService {

	@Autowired
	private FormacionEmpresaRepository repo;

	@Transactional
	public FormacionEmpresa save(FormacionEmpresa fe) {
		return repo.save(fe);
	}

	public FormacionEmpresa update(FormacionEmpresa fe) {
		return repo.save(fe);
	}

	public FormacionEmpresa find(Long id) {
		return repo.findById(id).get();
	}

	public List<FormacionEmpresa> findByAlumno(Alumno alumno) {
		return repo.findByAlumnoWithFaltas(alumno);
	}
	
	public List<FormacionEmpresa> findByTutor(Tutor tutor) {
		return repo.findByTutorEmpresa(tutor);
	}

	@Transactional
	public FormacionEmpresa findCompleta(Long id) {
		return repo.findByIdWithEvaluaciones(id).orElse(null);
	}

	public List<Alumno> getAlumnosByTutor(Long idTutor) {
		return repo.findAlumnosByTutorEmpresa(idTutor);
	}
	
	public List<Alumno> getAlumnosByProfesor(Long idProfe) {
		return repo.findAlumnosByTutorCentro(idProfe);
	}

	public List<FormacionEmpresa> findAll() {
		return repo.findAll();
	}

}
