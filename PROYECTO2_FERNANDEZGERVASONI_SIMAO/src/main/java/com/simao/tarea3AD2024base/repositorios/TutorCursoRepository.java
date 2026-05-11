package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.modelo.TutorCurso;

@Repository
public interface TutorCursoRepository extends JpaRepository<TutorCurso, Long> {

	boolean existsByTutorAndCurso(Tutor tutor, Curso curso);

	void deleteByTutorAndCurso(Tutor tutor, Curso curso);

	@Query("SELECT tc FROM TutorCurso tc JOIN FETCH tc.tutor JOIN FETCH tc.curso")
	List<TutorCurso> findAllWithDetails();
}
