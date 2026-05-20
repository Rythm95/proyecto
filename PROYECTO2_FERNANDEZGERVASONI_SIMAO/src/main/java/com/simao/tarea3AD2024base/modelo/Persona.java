package com.simao.tarea3AD2024base.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;

/**
 * Clase Persona.java
 *
 * Superclase para los usuarios del sistema ({@link Alumno}, {@link Profesor},
 * {@link Tutor}, {@link Profesor}) con un identificador, email, nombre de
 * usuario, contraseña, nombre de la persona y su {@link Perfil}.
 *
 * @author Simao Fernandez Gervasoni
 * @version 1.0
 * @see Perfil
 */

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false, nullable = false)
	protected Long id;

	@Column(unique = true, nullable = false)
	protected String email;

	@Column(unique = true, nullable = false)
	protected String user;

	@Column(nullable = false)
	protected String password;

	@Column(nullable = false)
	protected String nombre;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	protected Perfil perfil;

	public Persona() {
	}

	public Persona(String email, String user, String password, String nombre, Perfil perfil) {
		super();
		this.email = email;
		this.user = user;
		this.password = password;
		this.nombre = nombre;
		this.perfil = perfil;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

}
