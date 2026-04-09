/**
* Clase Persona.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package com.simao.tarea3AD2024base.modelo;

public abstract class Persona {

	protected int id;
	protected String email;
	protected String user;
	protected String password;
	protected String nombre;
	protected Perfil perfil;

	public Persona(int id, String email, String user, String password, String nombre, Perfil perfil) {
		super();
		this.id = id;
		this.email = email;
		this.user = user;
		this.password = password;
		this.nombre = nombre;
		this.perfil = perfil;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
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
