package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.services.Hasher;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.Session;

import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * Clase GestionDatosPropiosController.java
 * 
 * Gestiona las interacciones con la interfaz de gestión de datos propios.
 */
@Controller
public class GestionDatosPropiosController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;

	@Autowired
	private Session session;

	@FXML
	private TextField txtEditNombre;

	@FXML
	private Label lblEditNombreError;

	@FXML
	private TextField txtEditEmail;

	@FXML
	private Label lblEditEmailError;

	@FXML
	private TextField txtEditUsername;

	@FXML
	private Label lblEditUsernameError;

	@FXML
	private PasswordField txtEditPassword;

	@FXML
	private Label lblEditPasswordError;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarDatos();
	}

	/**
	 * Carga los datos personales del usuario en sesión
	 */
	@FXML
	private void cargarDatos() {
		Persona p = peService.find(session.getUserId());

		txtEditNombre.setText(p.getNombre());
		txtEditEmail.setText(p.getEmail());
		txtEditUsername.setText(p.getUser());
	}

	/**
	 * Valida y edita los datos personales del usuario en sesión.
	 * 
	 * Si la validación es correcta, actualiza la persona en sesió en la base de
	 * datos.
	 */
	@FXML
	private void editar() {
		Persona p = peService.find(session.getUserId());
		if (p == null)
			return;

		if (validar(txtEditNombre, lblEditNombreError, txtEditEmail, lblEditEmailError, txtEditUsername,
				lblEditUsernameError, txtEditPassword, lblEditPasswordError, p))
			return;

		p.setNombre(txtEditNombre.getText());
		p.setEmail(txtEditEmail.getText());
		p.setUser(txtEditUsername.getText());
		p.setPassword(Hasher.md5(txtEditPassword.getText()));

		peService.update(p);
	}

	/**
	 * Valida los datos de la persona edición.
	 * 
	 * Comprueba la validez de los siguientes campos: - Nombre: no vacío y formato
	 * válido (solo letras y espacios) - Email: no vacío, formato válido y no
	 * duplicado - Nombre de usuario: no vacío, sin espacios, formato válido y no
	 * duplicado - Contraseña: no vacía y sin espacios
	 * 
	 * Durante la validación se actualiza la interfaz mostrando errores y aplicando
	 * estilos visuales a los campos afectados.
	 * 
	 * @param tfNombre
	 * @param lblNombre
	 * @param tfEmail
	 * @param lblEmail
	 * @param tfUser
	 * @param lblUser
	 * @param tfPassword
	 * @param lblPassword
	 * @param p           Persona a editar
	 * @return true si hay errores de validación, false si los datos son válidos
	 */
	private boolean validar(TextField tfNombre, Label lblNombre, TextField tfEmail, Label lblEmail, TextField tfUser,
			Label lblUser, PasswordField tfPassword, Label lblPassword, Persona p) {

		Persona pEmail = peService.findByEmail(tfEmail.getText());
		Persona pUser = peService.findByUser(tfUser.getText());
		Persona pEdit = p;

		String txtNombre = tfNombre.getText();
		boolean nombre = txtNombre.isEmpty();
		tfNombre.pseudoClassStateChanged(EMPTY, nombre);

		if (!nombre) {
			if (!Pattern.matches("^[\\p{L}]+( [\\p{L}]+)*$", txtNombre)) {
				nombre = true;
				lblNombre.setText("El nombre solo puede contener caracteres unicode y espacios.");
			}

			lblNombre.setManaged(nombre);
			lblNombre.setVisible(nombre);

		} else {
			lblNombre.setManaged(false);
			lblNombre.setVisible(false);
		}

		String txtEmail = tfEmail.getText();
		boolean email = txtEmail.isEmpty();
		tfEmail.pseudoClassStateChanged(EMPTY, email);

		if (!email) {
			if (!Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", txtEmail)) {
				email = true;
				lblEmail.setText("El email no es válido.");
			} else if (pEmail != null && (pEdit == null || !pEmail.getId().equals(pEdit.getId()))) {
				email = true;
				lblEmail.setText("Este correo ya está registrado en la base de datos.");
			}

			lblEmail.setManaged(email);
			lblEmail.setVisible(email);

		} else {
			lblEmail.setManaged(false);
			lblEmail.setVisible(false);
		}

		String txtUsername = tfUser.getText();
		boolean username = txtUsername.isEmpty();
		tfUser.pseudoClassStateChanged(EMPTY, username);

		if (!username) {
			if (txtUsername.length() <= 2) {
				username = true;
				lblUser.setText("El nombre de usuario debe contener más de 2 caracteres.");
			} else if (txtUsername.contains(" ")) {
				username = true;
				lblUser.setText("El nombre de usuario no debe contener espacios.");
			} else if (!Pattern.matches("^[a-z]+$", txtUsername)) {
				username = true;
				lblUser.setText(
						"El nombre de usuario no debe contener números ni letras mayúsculas o con tíldes o dieresis.");
			} else if (pUser != null && (pEdit == null || !pUser.getId().equals(pEdit.getId()))) {
				username = true;
				lblUser.setText("Este nombre de usuario ya está registrado en la base de datos.");
			}

			lblUser.setManaged(username);
			lblUser.setVisible(username);

		} else {
			lblUser.setManaged(false);
			lblUser.setVisible(false);
		}

		String txtPassword = tfPassword.getText();
		boolean password = txtPassword.isEmpty();
		tfPassword.pseudoClassStateChanged(EMPTY, password);
		if (!password) {
			if (txtPassword.length() <= 2) {
				password = true;
				lblPassword.setText("La contraseña debe contener más de 2 caracteres.");
			} else if (txtPassword.contains(" ")) {
				password = true;
				lblPassword.setText("La contraseña no debe contener espacios.");
			}

			lblPassword.setManaged(password);
			lblPassword.setVisible(password);

		} else {
			lblPassword.setManaged(false);
			lblPassword.setVisible(false);
		}

		return nombre || email || username || password;
	}
}