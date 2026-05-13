package com.simao.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloGrupo;

@Repository
public interface ModuloGrupoRepository extends JpaRepository<ModuloGrupo, Long> {
	 
	ModuloGrupo findByModulo(Modulo modulo);
	
	Optional<ModuloGrupo> findByModuloAndGrupo(Modulo modulo, Grupo grupo);
	
}
