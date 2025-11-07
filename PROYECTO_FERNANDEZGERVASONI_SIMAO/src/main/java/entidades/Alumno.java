/**
* Clase Alumno.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package entidades;

public class Alumno extends Persona {

	private String ciclo;
	private boolean mayoriaEdad;
	private Empresa empresa;

	public Alumno(int id, String email, String user, String password, String nomre, Perfil perfil, String ciclo,
			boolean mayoriaEdad, Empresa empresa) {
		super(id, email, user, password, nomre, perfil);
		this.ciclo = ciclo;
		this.mayoriaEdad = mayoriaEdad;
		this.empresa = empresa;
	}

	public String getCiclo() {
		return ciclo;
	}

	public void setCiclo(String ciclo) {
		this.ciclo = ciclo;
	}

	public boolean isMayoriaEdad() {
		return mayoriaEdad;
	}

	public void setMayoriaEdad(boolean mayoriaEdad) {
		this.mayoriaEdad = mayoriaEdad;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
