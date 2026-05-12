package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.repositorios.TutorRepository;

@Service
public class TutorService {

	@Autowired
	private TutorRepository repo;

	public Tutor save(Tutor tutor) {
		return repo.save(tutor);
	}

	public Tutor update(Tutor tutor) {
		return repo.save(tutor);
	}

	public void delete(Long id) {
		repo.deleteById(id);
	}

	public Tutor find(Long id) {
		return repo.findById(id).get();
	}

	public List<Tutor> findAll() {
		return repo.findAllWithEmpresa();
	}

	public List<Tutor> findByName(String nombre) {
		return repo.findByNombre(nombre);
	}
}
