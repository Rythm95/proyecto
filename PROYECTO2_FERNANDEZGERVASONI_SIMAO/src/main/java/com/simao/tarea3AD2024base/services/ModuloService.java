package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.repositorios.ModuloRepository;

@Service
public class ModuloService {
	
	@Autowired
	private ModuloRepository repo;

	public Modulo save(Modulo modulo) {
		return repo.save(modulo);
	}

	public Modulo update(Modulo modulo) {
		return repo.save(modulo);
	}

	public Modulo find(Long id) {
		return repo.findById(id).get();
	}
	
	public Modulo findByNombre(String nombre) {
		return repo.findByNombre(nombre);
	}

	public List<Modulo> findAll() {
		return repo.findAll();
	}
	

}
