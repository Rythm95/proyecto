package com.simao.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.modelo.TutorCurso;
import com.simao.tarea3AD2024base.repositorios.CursoRepository;
import com.simao.tarea3AD2024base.repositorios.TutorCursoRepository;

import jakarta.transaction.Transactional;

@Service
public class CursoService {

	@Autowired
	private CursoRepository cursoRepository;

	@Autowired
	private TutorCursoRepository tutorCursoRepository;

	@Transactional
	public Curso save(Curso curso) {
		return cursoRepository.save(curso);
	}

	@Transactional
	public Curso update(Curso curso) {
		return cursoRepository.save(curso);
	}

	@Transactional
	public void delete(Long id) {
		cursoRepository.deleteById(id);
	}

	public Optional<Curso> find(Long id) {
		return cursoRepository.findById(id);
	}

	public List<Curso> findAll() {
		return cursoRepository.findAll();
	}

	@Transactional
	public boolean asignarTutor(Tutor tutor, Curso curso) {
		if (tutorCursoRepository.existsByTutorAndCurso(tutor, curso)) {
			return false;
		}
		tutorCursoRepository.save(new TutorCurso(tutor, curso));
		return true;
	}

	public List<TutorCurso> listarAsignaciones() {
		return tutorCursoRepository.findAllWithDetails();
	}
}
