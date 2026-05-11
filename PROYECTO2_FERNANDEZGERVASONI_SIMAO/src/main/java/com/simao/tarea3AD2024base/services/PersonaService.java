package com.simao.tarea3AD2024base.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.repositorios.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	public Optional<Persona> authenticate(String user, String password) {
		return personaRepository.findByUser(user).filter(p -> p.getPassword().equals(password));
	}

	public List<Persona> findByPerfil(Perfil perfil) {
		return personaRepository.findByPerfil(perfil);
	}

	public Optional<Persona> find(Long id) {
		return personaRepository.findById(id);
	}
}
