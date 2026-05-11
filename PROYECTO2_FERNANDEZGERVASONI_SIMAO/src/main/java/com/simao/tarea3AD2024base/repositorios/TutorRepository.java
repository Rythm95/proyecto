package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Tutor;

@Repository
public interface TutorRepository extends JpaRepository<Tutor, Long> {

	List<Tutor> findByNombre(String nombre);

	@Query("SELECT t FROM Tutor t LEFT JOIN FETCH t.empresa")
	List<Tutor> findAllWithEmpresa();
}
