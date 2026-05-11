package com.simao.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long> {
 
    Optional<Persona> findByUser(String user);
 
    Optional<Persona> findByEmail(String email);
 
    List<Persona> findByPerfil(Perfil perfil);
}
