package com.simao.tarea3AD2024base.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Tutor;

@Repository
public interface FormacionEmpresaRepository extends JpaRepository<FormacionEmpresa, Long> {

	@Query("SELECT DISTINCT fe FROM FormacionEmpresa fe LEFT JOIN FETCH fe.evaluaciones ev LEFT JOIN FETCH ev.resultadoAprendizaje ra WHERE fe.id = :id")
	Optional<FormacionEmpresa> findByIdWithEvaluaciones(@Param("id") Long id);

	@Query("SELECT DISTINCT fe.alumno FROM FormacionEmpresa fe WHERE fe.tutorEmpresa.id = :tutorId")
	List<Alumno> findAlumnosByTutorEmpresa(@Param("tutorId") Long tutorId);
	
	List<FormacionEmpresa> findByTutorEmpresa(Tutor tutor);

	FormacionEmpresa findByAlumno(Alumno alumno);

}
