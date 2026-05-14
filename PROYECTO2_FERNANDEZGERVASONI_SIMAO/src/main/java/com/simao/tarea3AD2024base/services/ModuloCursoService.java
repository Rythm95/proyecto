package com.simao.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloCurso;
import com.simao.tarea3AD2024base.repositorios.ModuloGrupoRepository;

@Service
public class ModuloCursoService {
	
	@Autowired
	private ModuloGrupoRepository repo;

	public ModuloCurso save(ModuloCurso modulo) {
		return repo.save(modulo);
	}

	public ModuloCurso update(ModuloCurso modulo) {
		return repo.save(modulo);
	}

	public ModuloCurso find(Long id) {
		return repo.findById(id).get();
	}
	
	public ModuloCurso findByModulo(Modulo modulo) {
	    return repo.findByModulo(modulo);
	}
	
	public Optional<ModuloCurso> findByModuloAndCurso(Modulo modulo, Curso curso) {
	    return repo.findByModuloAndCurso(modulo, curso);
	}

	public List<ModuloCurso> findAll() {
		return repo.findAll();
	}
	

}
