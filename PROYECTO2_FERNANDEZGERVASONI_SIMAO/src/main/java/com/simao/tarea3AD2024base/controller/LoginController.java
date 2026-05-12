package com.simao.tarea3AD2024base.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.Session;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

@Controller
public class LoginController implements Initializable {

	private static final Logger logger = Logger.getLogger(LoginController.class.getName());

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;

	@Autowired
	private Session session;

	@FXML
	private PasswordField passwordField;

	@FXML
	private TextField usernameField;

	@FXML
	private Label lblError;

	public String getPassword() {
		return passwordField.getText();
	}

	public String getUsername() {
		return usernameField.getText();
	}

	@FXML
	private void login(ActionEvent event) throws IOException {
		session.clear();

		Properties properties = new Properties();
		try (FileInputStream fis = new FileInputStream("src/main/resources/application.properties")) {
			properties.load(fis);
		} catch (IOException e) {
			logger.warning("Error al leer el fichero de propiedades: " + e.getMessage());
		}
		String user = properties.getProperty("usuarioAdmin");
		String pass = properties.getProperty("passwordAdmin");

		usernameField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), getUsername().isEmpty());
		passwordField.pseudoClassStateChanged(PseudoClass.getPseudoClass("error"), getPassword().isEmpty());

		if (getPassword().isEmpty() || getUsername().isEmpty()) {
			return;
		} else if (user.equals(getUsername()) && pass.equals(getPassword())) {
			stageManager.switchScene(FxmlView.ADMINISTRADOR);
		} else if (peService.authenticate(getUsername(), getPassword())) {

			Persona pers = peService.findByUser(getUsername());
			if (pers != null && pers.getPassword().equals(getPassword())) {

				session.setUserId(pers.getId());
				session.setPerfil(pers.getPerfil());
				session.setUsername(getUsername());
			}

			switch (pers.getPerfil()) {
			case ALUMNADO:
				stageManager.switchScene(FxmlView.ALUMNADO);
				break;

			case PROFESORADO:
				stageManager.switchScene(FxmlView.PROFESORADO);
				break;
			
			case TUTOR:
				stageManager.switchScene(FxmlView.TUTOR);
				break;
				
			default:
				break;
			}

		} else {
			lblError.setVisible(true);
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

}
