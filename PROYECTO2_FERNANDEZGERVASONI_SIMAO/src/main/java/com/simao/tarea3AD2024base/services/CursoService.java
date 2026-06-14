package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.repositorios.CursoRepository;

@Service
public class CursoService {

	@Autowired
	private CursoRepository repo;

	public Curso save(Curso curso) {
		return repo.save(curso);
	}

	public Curso update(Curso curso) {
		return repo.save(curso);
	}

	public Curso find(Long id) {
		return repo.findById(id).get();
	}

	public List<Curso> findAll() {
		return repo.findAll();
	}
	
	public List<Curso> findByProfesor(Profesor profe){
		return repo.findByCoordinador(profe);
	}

}
