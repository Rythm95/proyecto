package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.ResultadoAprendizaje;
import com.simao.tarea3AD2024base.repositorios.ResultadoAprendizajeRepository;

@Service
public class ResultadoAprendizajeService {

	@Autowired
	private ResultadoAprendizajeRepository repo;

	public ResultadoAprendizaje save(ResultadoAprendizaje ra) {
		return repo.save(ra);
	}

	public ResultadoAprendizaje update(ResultadoAprendizaje ra) {
		return repo.save(ra);
	}

	public ResultadoAprendizaje find(Long id) {
		return repo.findById(id).get();
	}
	
	public List<ResultadoAprendizaje> findByNombreParcial(String codigo) {
		return repo.findByCodigoContainingIgnoreCase(codigo);
	}
	
	public ResultadoAprendizaje findByCodigo(String codigo) {
		return repo.findByCodigo(codigo);
	}
	
	public List<ResultadoAprendizaje> findByCurso(Curso curso){
		return repo.findByCurso(curso);
	}

	public List<ResultadoAprendizaje> findAll() {
		return repo.findAll();
	}
	
}
