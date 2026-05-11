package com.simao.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.repositorios.TutorRepository;

import jakarta.transaction.Transactional;

@Service
public class TutorService {

	@Autowired
	private TutorRepository tutorRepository;

	@Transactional
	public Tutor save(Tutor tutor) {
		return tutorRepository.save(tutor);
	}

	@Transactional
	public Tutor update(Tutor tutor) {
		return tutorRepository.save(tutor);
	}

	@Transactional
	public void delete(Long id) {
		tutorRepository.deleteById(id);
	}

	public Optional<Tutor> find(Long id) {
		return tutorRepository.findById(id);
	}

	public List<Tutor> findAll() {
		return tutorRepository.findAllWithEmpresa();
	}

	public List<Tutor> findByName(String nombre) {
		return tutorRepository.findByNombre(nombre);
	}
}
