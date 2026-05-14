package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.repositorios.AlumnoRepository;

@Service
public class AlumnoService {

	@Autowired
	private AlumnoRepository repo;
	
	public Alumno save(Alumno alumno) {
		return repo.save(alumno);
	}
	
	public Alumno update(Alumno alumno) {
		return repo.save(alumno);
	}

	public Alumno find(Long id) {
		return repo.findById(id).get();
	}
	
	public List<Alumno> findAll() {
		return repo.findAll();
	}
	
	public List<Alumno> findByCurso(Curso curso) {
		return repo.findByCurso(curso); 
	}
	
	public List<Alumno> findByNombre(String nombre) {
		return repo.findByNombreContainingIgnoreCase(nombre); 
	}

}
