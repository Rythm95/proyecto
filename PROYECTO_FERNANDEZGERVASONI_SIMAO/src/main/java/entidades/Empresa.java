/**
* Clase Empresa.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package entidades;

public class Empresa {
	private String direccion;
	private String nombre;

	public Empresa(String direccion, String nombre) {
		super();
		this.direccion = direccion;
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
