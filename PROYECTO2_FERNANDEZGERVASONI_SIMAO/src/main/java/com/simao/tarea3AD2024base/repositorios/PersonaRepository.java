package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
 
    Persona findByUser(String user);
 
    Persona findByEmail(String email);
 
    List<Persona> findByPerfil(Perfil perfil);
    
    Persona findByNombre(String nombre);
}
