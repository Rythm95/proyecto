package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

/**
 * Clase AlumnoController.java
 * 
 * Gestiona las interacciones con la interfaz de los usuarios alumnos.
 */
@Controller
public class AlumnoController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	/**
	 * Cierra la sesión actual y vuelve a la pantalla de inicio de sesión.
	 *
	 * @param event Evento lanzado desde la interfaz
	 */
	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	/**
	 * Finaliza la aplicación.
	 *
	 * @param event Evento lanzado desde la interfaz
	 */
	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
