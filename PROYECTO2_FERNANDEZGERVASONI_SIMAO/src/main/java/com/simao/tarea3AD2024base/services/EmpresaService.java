package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Empresa;
import com.simao.tarea3AD2024base.repositorios.EmpresaRepository;

@Service
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
	
	public List<Empresa> findByNombreParcial(String empresa) {
		return repo.findByNombreContainingIgnoreCase(empresa);
	}
	
	public Empresa findByNombre(String empresa) {
		return repo.findByNombre(empresa);
	}

	public List<Empresa> findAll() {
		return repo.findAll();
	}
	
}
