package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Grupo;

@Repository
public interface GrupoRepository extends JpaRepository<Grupo, Long> {

	Grupo findByCodigo(String codigo);

	List<Grupo> findByYearAcademico(String yearAcademico);

	@Query("SELECT g FROM Grupo g LEFT JOIN FETCH g.modulos LEFT JOIN FETCH g.alumnos")
	List<Grupo> findAllWithDetails();
}
