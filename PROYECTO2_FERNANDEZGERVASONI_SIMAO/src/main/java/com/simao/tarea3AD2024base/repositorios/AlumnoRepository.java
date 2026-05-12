package com.simao.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.Grupo;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Long> {

	List<Alumno> findByNombre(String nombre);

	List<Alumno> findByGrupo(Grupo grupo);

}
