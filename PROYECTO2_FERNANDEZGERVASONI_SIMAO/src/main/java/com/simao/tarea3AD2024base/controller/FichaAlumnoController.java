package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.Falta;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.services.AlumnoService;
import com.simao.tarea3AD2024base.services.FormacionEmpresaService;
import com.simao.tarea3AD2024base.services.Session;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

/**
 * Clase GestionAlumnoController.java
 * 
 * Gestiona las interacciones con la interfaz de gestión de alumnos.
 */
@Controller
public class FichaAlumnoController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private AlumnoService alService;

	@Autowired
	private FormacionEmpresaService feService;

	@Autowired
	private Session session;

	@FXML
	private Label lblNombre;

	@FXML
	private Label lblEmail;

	@FXML
	private Label lblEdad;

	@FXML
	private Label lblCurso;

	@FXML
	private Label lblCoordinador;

	@FXML
	private VBox boxFormaciones;

	@FXML
	private TableView<Falta> tablaFaltas;
	
	@FXML
	private TableColumn<Falta, String> colFechaFalta;
	
	@FXML
	private TableColumn<Falta, String> colDescripcionFalta;
	
	@FXML
	private TableColumn<Falta, String> colJustificada;
	
	@FXML
	private TableColumn<Falta, String> colJustificante;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Alumno al = alService.find(session.getIdGest());
		cargarDatos(al);

		List<FormacionEmpresa> fes = feService.findByAlumno(al);

		if (fes.isEmpty()) {
			boxFormaciones.setManaged(false);
			boxFormaciones.setVisible(false);
		} else
			for (FormacionEmpresa fe : fes) {
				cargarFEs(fe);
			}

	}

	private void cargarDatos(Alumno al) {
		lblNombre.setText(al.getNombre());
		lblEmail.setText(al.getEmail());
		lblEdad.setText(al.isMayoriaEdad() ? "Mayor de edad" : "Menor de edad");
		lblCurso.setText(al.getCurso().getNombre());
		lblCoordinador.setText(al.getCurso().getCoordinador().getNombre());
	}

	private void cargarFEs(FormacionEmpresa fe) {
		VBox card = new VBox(5);

		card.getStyleClass().add("card");

		card.getChildren().addAll(new Label("Empresa: " + fe.getEmpresa().getNombre()),
				new Label("Periodo: " + fe.getPeriodo()), new Label("Fecha inicio: " + fe.getFechaIni()),
				new Label("Fecha fin: " + fe.getFechaFin()),
				new Label("Tutor empresa: " + fe.getTutorEmpresa().getNombre()),
				new Label("Tutor centro: " + fe.getTutorCentro().getNombre()), new Label("Estado: " + fe.getEstado()));

		card.setOnMouseClicked(_ -> {
			cargarFaltas(fe);
		});

		boxFormaciones.getChildren().add(card);
	}

	private void cargarFaltas(FormacionEmpresa fe) {
		if (fe == null || fe.getFaltas() == null) {
			tablaFaltas.getItems().clear();
			tablaFaltas.setPlaceholder(new Label("No hay faltas registradas para esta FE."));
			return;
		}
		
		ObservableList<Falta> datos = FXCollections.observableArrayList(fe.getFaltas());
		tablaFaltas.setItems(datos);
		colFechaFalta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
		colDescripcionFalta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
		colJustificada.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().isJustificada()? "Justificada":"Injustificada"));
		colJustificante.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJustificante()));
	}

	@FXML
	private void goBack() {
		Perfil p = session.getPerfil();

		if (p == null) {
			stageManager.switchScene(FxmlView.ADMINISTRADOR);
		} else {
			switch (p) {
			case ALUMNADO:
				stageManager.switchScene(FxmlView.ALUMNADO);
				break;

			case TUTOR:
				stageManager.switchScene(FxmlView.TUTOR);
				break;

			case PROFESORADO:
				stageManager.switchScene(FxmlView.PROFESORADO);
				break;

			default:
				stageManager.switchScene(FxmlView.ADMINISTRADOR);
				break;

			}
		}
	}
}
