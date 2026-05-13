package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Profesor;

@Repository
public interface ProfesorRepository extends JpaRepository<Profesor, Long> {

	List<Profesor> findByNombre(String nombre);

	Profesor findByUser(String user);

}