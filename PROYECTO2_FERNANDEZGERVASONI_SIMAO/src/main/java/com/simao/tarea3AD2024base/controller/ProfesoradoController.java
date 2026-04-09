package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.components.TiraAlumno;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

@Controller
public class ProfesoradoController implements Initializable {

	@FXML
	private VBox vBoxAlumnos;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		System.out.println("Controller creado");
		

		TiraAlumno tira = new TiraAlumno("Pedro", "2º DAM", "Medialab");
		vBoxAlumnos.getChildren().add(tira);

	}

	public void logout(ActionEvent event) {
		System.out.println("Logout");
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
