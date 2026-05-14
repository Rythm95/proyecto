package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Ciclo;
import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.TipoCiclo;
import com.simao.tarea3AD2024base.repositorios.CicloRepository;

@Service
public class CicloService {

	@Autowired
	private CicloRepository repo;
	
	@Autowired
	private CursoService cuService;
	
	public void loadAll() {
		Ciclo dawDiu = new Ciclo(TipoCiclo.DAW_DIURNO);
        Ciclo dawVes = new Ciclo(TipoCiclo.DAW_VESPERTINO);
        Ciclo dawVir = new Ciclo(TipoCiclo.DAW_VIRTUAL);
        Ciclo dam = new Ciclo(TipoCiclo.DAM);

        repo.save(dawDiu);
        repo.save(dawVes);
        repo.save(dawVir);
        repo.save(dam);
		
        cuService.save(new Curso("1IFC303", "1º DAW Diurno", dawDiu));
		cuService.save(new Curso("2IFC303", "2º DAW Diurno", dawDiu));

		cuService.save(new Curso("1VIFC303", "1º DAW Vespertino", dawVes));
		cuService.save(new Curso("2VIFC303", "2º DAW Vespertino", dawVes)); 

		cuService.save(new Curso("1@IFC303", "1º DAW Virtual", dawVir));
		cuService.save(new Curso("2@IFC303", "2º DAW Virtual", dawVir));

		cuService.save(new Curso("1VIFC302", "1º DAM", dam));
		cuService.save(new Curso("2VIFC302", "2º DAM", dam));
	}
	
	public List<Ciclo> findAll(){
		return repo.findAll();
	}
}
