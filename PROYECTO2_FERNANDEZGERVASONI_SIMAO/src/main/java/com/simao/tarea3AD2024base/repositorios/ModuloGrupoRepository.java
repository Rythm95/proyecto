package com.simao.tarea3AD2024base.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloCurso;

@Repository
public interface ModuloGrupoRepository extends JpaRepository<ModuloCurso, Long> {
	 
	ModuloCurso findByModulo(Modulo modulo);
	
	Optional<ModuloCurso> findByModuloAndCurso(Modulo modulo, Curso curso);
	
}
