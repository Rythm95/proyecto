package com.simao.tarea3AD2024base.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Falta;
import com.simao.tarea3AD2024base.repositorios.FaltaRepository;

@Service
public class FaltaService {
	
	@Autowired
	private FaltaRepository repo;

	public Falta save(Falta falta) {
		return repo.save(falta);
	}

	public Falta update(Falta falta) {
		return repo.save(falta);
	}

	public Falta find(Long id) {
		return repo.findById(id).get();
	}
}
