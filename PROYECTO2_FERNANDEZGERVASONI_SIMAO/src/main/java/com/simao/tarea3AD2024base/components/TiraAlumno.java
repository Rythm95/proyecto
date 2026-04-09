package com.simao.tarea3AD2024base.components;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class TiraAlumno extends GridPane {

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblCurso;

	@FXML
	private Label lblEmpresa;

	public TiraAlumno() {

		FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/simao/tarea3AD2024base/components/TiraAlumno.fxml"));

		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public TiraAlumno(String nombre, String curso, String empresa) {
		this();
		lblNombre.setText(nombre);
		lblCurso.setText(curso);
		lblEmpresa.setText(empresa);
	}

}
