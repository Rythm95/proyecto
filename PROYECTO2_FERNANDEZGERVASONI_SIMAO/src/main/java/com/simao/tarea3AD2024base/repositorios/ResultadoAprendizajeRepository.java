package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.ResultadoAprendizaje;

@Repository
public interface ResultadoAprendizajeRepository extends JpaRepository<ResultadoAprendizaje, Long> {

	List<ResultadoAprendizaje> findByCodigoContainingIgnoreCase(String codigo);

	ResultadoAprendizaje findByCodigo(String codigo);

	@Query("SELECT ra FROM ResultadoAprendizaje ra WHERE ra.modulo.id IN (SELECT mc.modulo.id FROM ModuloCurso mc WHERE mc.curso = :curso)")
	List<ResultadoAprendizaje> findByCurso(Curso curso);

}