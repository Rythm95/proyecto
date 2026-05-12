package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

@Controller
public class AdminController implements Initializable {

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;
	
	@Autowired
	private ProfesorService prService;

	@FXML
	private TableView<Profesor> tablaProfes;

	@FXML
	private VBox formProfe;

	@FXML
	private TextField txtNombreProfe;

	@FXML
	private TextField txtEmailProfe;

	@FXML
	private TextField txtUsernameProfe;

	@FXML
	private PasswordField txtPasswordProfe;

	@FXML
	private TableColumn<Profesor, String> colNombreProfe;

	@FXML
	private TableColumn<Profesor, String> colEmailProfe;

	@FXML
	private TextField buscadorProfe;

	@FXML
	private TableView<Grupo> tablaCursos;

	@FXML
	private TableColumn<Grupo, String> colCodigo;

	@FXML
	private TableColumn<Grupo, String> colCiclo;

	@FXML
	private TableColumn<Grupo, String> colCurso;

	@FXML
	private TextField buscadorCurso;

	@FXML
	private ComboBox<Profesor> comboProfes;

	@FXML
	private ComboBox<Grupo> comboCursos;

	@FXML
	private TableView<FormacionEmpresa> tablaAsignaciones;

	@FXML
	private TableColumn<FormacionEmpresa, String> colProfeAsignado;

	@FXML
	private TableColumn<FormacionEmpresa, String> colCursoAsignado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		configurarColumnaProfes();
		configurarColumnaCursos();
		cargarProfes();
	}

	private void configurarColumnaProfes() {
		colNombreProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmailProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
	}

	private void cargarProfes() {
		List<Profesor> tutores = prService.findAll();
		tablaProfes.setItems(FXCollections.observableArrayList(tutores));
		comboProfes.setItems(FXCollections.observableArrayList(tutores));
	}

	@FXML
	private void switchFormTutor() {
		boolean b = tablaProfes.isVisible();
		tablaProfes.setVisible(!b);
		tablaProfes.setManaged(!b);
		formProfe.setVisible(b);
		formProfe.setManaged(b);
	}

	@FXML
	private void buscarProfe() {
		String texto = buscadorProfe.getText().trim();
		if (texto.isEmpty()) {
			cargarProfes();
		} else {
			tablaProfes.setItems(FXCollections.observableArrayList(prService.findByNombre(texto)));
		}
	}

	@FXML
	private void guardarProfe() {

		Profesor profe = new Profesor();

		profe.setNombre(txtNombreProfe.getText());
		profe.setEmail(txtEmailProfe.getText());
		profe.setUser(txtUsernameProfe.getText());
		profe.setPassword(txtPasswordProfe.getText());
		profe.setPerfil(Perfil.PROFESORADO);

		peService.save(profe);

		switchFormTutor();
		cargarProfes();
	}

	private void configurarColumnaCursos() {
		colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
		colCiclo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCiclo()));
		colCurso.setCellValueFactory(
				data -> new SimpleStringProperty(String.valueOf(data.getValue().getCurso()) + "º"));
	}

	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
