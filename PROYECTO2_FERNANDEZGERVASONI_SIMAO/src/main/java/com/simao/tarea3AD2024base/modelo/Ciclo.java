package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ciclo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	private TipoCiclo tipo;

	public Ciclo() {
	}

	public Ciclo(TipoCiclo tipo) {
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoCiclo getTipo() {
		return tipo;
	}

	public void setTipo(TipoCiclo tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return tipo.toString();
	}
}