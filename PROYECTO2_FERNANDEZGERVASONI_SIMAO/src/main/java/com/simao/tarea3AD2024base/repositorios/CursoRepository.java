package com.simao.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Curso;

@Repository
public interface CursoRepository extends JpaRepository<Curso, Long> {

	Optional<Curso> findByCodigo(String codigo);

	List<Curso> findByYearAcademico(String yearAcademico);
}
