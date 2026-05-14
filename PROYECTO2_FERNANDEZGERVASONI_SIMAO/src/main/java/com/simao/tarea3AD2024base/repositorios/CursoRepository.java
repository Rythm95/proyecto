package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	Curso findByCodigo(String codigo);

	List<Curso> findByYearAcademico(String yearAcademico);

	@Query("SELECT g FROM Curso g LEFT JOIN FETCH g.modulos LEFT JOIN FETCH g.alumnos")
	List<Curso> findAllWithDetails();
}
