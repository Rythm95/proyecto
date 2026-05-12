package com.simao.tarea3AD2024base.services;

import org.springframework.stereotype.Component;

import com.simao.tarea3AD2024base.modelo.Perfil;

@Component
public class Session {
	private Perfil perfil = null;
	private Long userId = null;
	private String username = null;

	public void clear() {
		perfil = null;
		userId = null;
		username = null;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
