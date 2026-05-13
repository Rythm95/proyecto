package com.simao.tarea3AD2024base.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.simao.tarea3AD2024base.modelo.Ciclo;

@Repository
public interface CicloRepository extends JpaRepository<Ciclo, Long> {

}
