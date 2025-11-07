/**
* Clase Profesor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package entidades;

public class Profesor extends Persona {

	private String[] ciclos;

	public Profesor(int id, String email, String user, String password, String nomre, Perfil perfil, String[] ciclos) {
		super(id, email, user, password, nomre, perfil);
		this.ciclos = ciclos;
	}

	public String[] getCiclos() {
		return ciclos;
	}

	public void setCiclos(String[] ciclos) {
		this.ciclos = ciclos;
	}

}
