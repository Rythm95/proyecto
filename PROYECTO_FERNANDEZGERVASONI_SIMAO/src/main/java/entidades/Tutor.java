/**
* Clase Tutor.java
*
* @author Simao Fernandez Gervasoni
* @version 1.0
*/
package entidades;

public class Tutor extends Persona {

	private Empresa empresa;

	public Tutor(int id, String email, String user, String password, String nomre, Perfil perfil, Empresa empresa) {
		super(id, email, user, password, nomre, perfil);
		this.empresa = empresa;
	}

	public Empresa getEmpresa() {
		return empresa;
	}

	public void setEmpresa(Empresa empresa) {
		this.empresa = empresa;
	}

}
