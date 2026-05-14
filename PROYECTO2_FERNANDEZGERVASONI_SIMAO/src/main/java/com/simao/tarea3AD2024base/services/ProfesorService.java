package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.repositorios.ProfesorRepository;

@Service
public class ProfesorService {

	@Autowired
	private ProfesorRepository repo;
	
	public Profesor save(Profesor profe) {
		return repo.save(profe);
	}
	
	public Profesor update(Profesor persona) {
		return repo.save(persona);
	}

	public Profesor find(Long id) {
		return repo.findById(id).get();
	}
	
	public List<Profesor> findAll() {
		return repo.findAll();
	}
	
	public Profesor findByUser(String user) {
		return repo.findByUser(user); 
	}
	
	public List<Profesor> findByNombre(String nombre) {
		return repo.findByNombreContainingIgnoreCase(nombre); 
	}

}
