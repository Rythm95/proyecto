package com.simao.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloGrupo;
import com.simao.tarea3AD2024base.repositorios.ModuloGrupoRepository;

@Service
public class ModuloGrupoService {
	
	@Autowired
	private ModuloGrupoRepository repo;

	public ModuloGrupo save(ModuloGrupo modulo) {
		return repo.save(modulo);
	}

	public ModuloGrupo update(ModuloGrupo modulo) {
		return repo.save(modulo);
	}

	public ModuloGrupo find(Long id) {
		return repo.findById(id).get();
	}
	
	public ModuloGrupo findByModulo(Modulo modulo) {
	    return repo.findByModulo(modulo);
	}
	
	public Optional<ModuloGrupo> findByModuloAndGrupo(Modulo modulo, Grupo grupo) {
	    return repo.findByModuloAndGrupo(modulo, grupo);
	}

	public List<ModuloGrupo> findAll() {
		return repo.findAll();
	}
	

}
