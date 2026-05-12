package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.repositorios.GrupoRepository;

@Service
public class GrupoService {

	@Autowired
	private GrupoRepository repo;

	public Grupo save(Grupo curso) {
		return repo.save(curso);
	}

	public Grupo update(Grupo curso) {
		return repo.save(curso);
	}

	public Grupo find(Long id) {
		return repo.findById(id).get();
	}

	public List<Grupo> findAll() {
		return repo.findAll();
	}

}
