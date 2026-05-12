package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.repositorios.PersonaRepository;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository repo;

	public boolean authenticate(String username, String password) {
		
		Persona p = this.findByUser(username);
		if (p == null) {
			return false;
		} else {
			if (password.equals(p.getPassword()))
				return true;
			else
				return false;
		}
	}
	
	public Persona save(Persona persona) {
		return repo.save(persona);
	}
	
	public Persona update(Persona persona) {
		return repo.save(persona);
	}

	public Persona find(Long id) {
		return repo.findById(id).get();
	}
	
	public Persona findByUser(String user) {
		return repo.findByUser(user); 
	}
	
	public List<Persona> findByPerfil(Perfil perfil) {
		return repo.findByPerfil(perfil);
	}
	
	public Persona findByNombre(String nombre) {
		return repo.findByNombre(nombre); 
	}

}
