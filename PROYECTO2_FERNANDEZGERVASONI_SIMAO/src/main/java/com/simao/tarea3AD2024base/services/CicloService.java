package com.simao.tarea3AD2024base.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simao.tarea3AD2024base.modelo.Ciclo;
import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.modelo.TipoCiclo;
import com.simao.tarea3AD2024base.repositorios.CicloRepository;

@Service
public class CicloService {

	@Autowired
	private CicloRepository repo;
	
	@Autowired
	private GrupoService grService;
	
	public void loadAll() {
		Ciclo dawDiu = new Ciclo(TipoCiclo.DAW_DIURNO);
        Ciclo dawVes = new Ciclo(TipoCiclo.DAW_VESPERTINO);
        Ciclo dawVir = new Ciclo(TipoCiclo.DAW_VIRTUAL);
        Ciclo dam = new Ciclo(TipoCiclo.DAM);

        repo.save(dawDiu);
        repo.save(dawVes);
        repo.save(dawVir);
        repo.save(dam);
		
        grService.save(new Grupo("1IFC303", 1, dawDiu));
		grService.save(new Grupo("2IFC303", 2, dawDiu));

		grService.save(new Grupo("1VIFC303", 1, dawVes));
		grService.save(new Grupo("2VIFC303", 2, dawVes));

		grService.save(new Grupo("1@IFC303", 1, dawVir));
		grService.save(new Grupo("2@IFC303", 2, dawVir));

		grService.save(new Grupo("1VIFC302", 1, dam));
		grService.save(new Grupo("2VIFC302", 2, dam));
	}
	
	public List<Ciclo> findAll(){
		return repo.findAll();
	}
}
