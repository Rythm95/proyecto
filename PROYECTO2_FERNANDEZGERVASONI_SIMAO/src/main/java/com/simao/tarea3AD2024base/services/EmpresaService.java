package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.simao.tarea3AD2024base.modelo.Empresa;
import com.simao.tarea3AD2024base.repositorios.EmpresaRepository;

public class EmpresaService {

	@Autowired
	private EmpresaRepository repo;

	public Empresa save(Empresa empresa) {
		return repo.save(empresa);
	}

	public Empresa update(Empresa empresa) {
		return repo.save(empresa);
	}

	public Empresa find(Long id) {
		return repo.findById(id).get();
	}

	public List<Empresa> findAll() {
		return repo.findAll();
	}
	
}
