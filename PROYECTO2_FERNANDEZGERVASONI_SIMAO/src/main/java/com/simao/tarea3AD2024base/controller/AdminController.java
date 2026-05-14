package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloCurso;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.services.CursoService;
import com.simao.tarea3AD2024base.services.Hasher;
import com.simao.tarea3AD2024base.services.ModuloCursoService;
import com.simao.tarea3AD2024base.services.ModuloService;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@Controller
public class AdminController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;

	@Autowired
	private ProfesorService prService;

	@Autowired
	private CursoService cuService;

	@Autowired
	private ModuloService moService;

	@Autowired
	private ModuloCursoService mcService;

	@FXML
	private TableView<Profesor> tablaProfes;

	@FXML
	private HBox boxNuevoProfe;

	@FXML
	private HBox boxBuscarProfe;

	@FXML
	private HBox boxEditarProfe;

	@FXML
	private HBox boxAsignacionModulo;

	@FXML
	private HBox boxCreacionModulo;

	@FXML
	private TextField txtNombreModulo;

	@FXML
	private Button btnCrearModulo;

	@FXML
	private Button btnAsignarModulo;

	@FXML
	private Button btnEditarModulo;

	@FXML
	private TextField txtNombreProfe;

	@FXML
	private Label lblNombreProfeError;

	@FXML
	private TextField txtEmailProfe;

	@FXML
	private Label lblEmailProfeError;

	@FXML
	private TextField txtUsernameProfe;

	@FXML
	private Label lblUsernameProfeError;

	@FXML
	private PasswordField txtPasswordProfe;

	@FXML
	private Label lblPasswordProfeError;

	@FXML
	private Label lblNombreModuloError;

	@FXML
	private TextField txtEditNombreProfe;

	@FXML
	private Label lblEditNombreProfeError;

	@FXML
	private TextField txtEditEmailProfe;

	@FXML
	private Label lblEditEmailProfeError;

	@FXML
	private TextField txtEditUsernameProfe;

	@FXML
	private Label lblEditUsernameProfeError;

	@FXML
	private PasswordField txtEditPasswordProfe;

	@FXML
	private Label lblEditPasswordProfeError;

	@FXML
	private Button btnBuscarProfe;

	@FXML
	private Button btnNuevoProfe;

	@FXML
	private Button btnEditarProfe;

	@FXML
	private ComboBox<Profesor> cbProfeEditar;

	@FXML
	private TableColumn<Profesor, String> colNombreProfe;

	@FXML
	private TableColumn<Profesor, String> colEmailProfe;

	@FXML
	private TextField txtBuscadorProfe;

	@FXML
	private TableView<Curso> tablaCursos;

	@FXML
	private ComboBox<Profesor> cbCoordinador;

	@FXML
	private TableColumn<Curso, String> colCodigo;

	@FXML
	private TableColumn<Curso, String> colCiclo;

	@FXML
	private TableColumn<Curso, String> colCurso;

	@FXML
	private TableColumn<Curso, String> colCoordinador;

	@FXML
	private ComboBox<Curso> cbCurso;

	@FXML
	private ComboBox<Profesor> cbProfesorModulo;

	@FXML
	private HBox boxEdicionModulo;

	@FXML
	private ComboBox<Modulo> cbModuloEditar;

	@FXML
	private TextField txtEditNombreModulo;

	@FXML
	private Label lblEditNombreModuloError;

	@FXML
	private TableView<Modulo> tablaModulos;

	@FXML
	private TableColumn<Modulo, String> colModulo;

	@FXML
	private TableColumn<Modulo, String> colCursoModulo;

	@FXML
	private TableColumn<Modulo, String> colProfesorModulo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbProfeEditar.getSelectionModel().selectedItemProperty().addListener((_, _, profe) -> {
			cargarProfeEditar(profe);
		});

		cargarCursos();
		cargarProfes();
		cargarModulos();
	}

	private void cargarProfes() {
		List<Profesor> profes = prService.findAll();
		ObservableList<Profesor> datos = FXCollections.observableArrayList(profes);

		colNombreProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmailProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

		if (!profes.isEmpty()) {
			tablaProfes.setItems(datos);
			cbCoordinador.setItems(datos);
			cbProfesorModulo.setItems(datos);
			cbProfeEditar.setItems(datos);
		}
	}

	private void cargarCursos() {
		List<Curso> cursos = cuService.findAll();
		ObservableList<Curso> datos = FXCollections.observableArrayList(cursos);

		cbCurso.setItems(datos);

		if (!cursos.isEmpty()) {
			ObservableList<Curso> lista = datos;
			tablaCursos.setItems(lista);
		}

		colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
		colCiclo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCiclo().toString()));
		colCurso.setCellValueFactory(
				data -> new SimpleStringProperty(String.valueOf(data.getValue().getNombre().charAt(0))));
		colCoordinador.setCellValueFactory(data -> {

			Profesor coordinador = data.getValue().getCoordinador();

			String nombre = coordinador != null ? coordinador.getNombre() : "Sin asignar";

			return new SimpleStringProperty(nombre);
		});
	}

	private void cargarModulos() {

		List<Modulo> modulos = moService.findAll();

		ObservableList<Modulo> datos = FXCollections.observableArrayList(modulos);

		tablaModulos.setItems(datos);
		cbModuloEditar.setItems(datos);
		colModulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colCursoModulo.setCellValueFactory(data -> {

			ModuloCurso mc = mcService.findByModulo(data.getValue());

			String curso = (mc != null && mc.getCurso() != null) ? mc.getCurso().getCodigo() : "Sin asignar";

			return new SimpleStringProperty(curso);
		});

		colProfesorModulo.setCellValueFactory(data -> {

			ModuloCurso mc = mcService.findByModulo(data.getValue());

			String prof = (mc != null && mc.getProfesor() != null) ? mc.getProfesor().getNombre() : "Sin asignar";

			return new SimpleStringProperty(prof);
		});
	}

	@FXML
	private void switchBuscarProfe() {
		if (!boxBuscarProfe.isVisible()) {
			switchBox(boxBuscarProfe, boxEditarProfe, boxNuevoProfe);
			switchButton(btnBuscarProfe, btnEditarProfe, btnNuevoProfe);
		} else {
			buscarProfe();
		}
	}

	@FXML
	private void switchNuevoProfe() {
		if (!boxNuevoProfe.isVisible()) {
			switchBox(boxNuevoProfe, boxEditarProfe, boxBuscarProfe);
			switchButton(btnNuevoProfe, btnEditarProfe, btnBuscarProfe);
		} else {
			guardarProfe();
		}
	}

	@FXML
	private void switchEditarProfe() {
		if (!boxEditarProfe.isVisible()) {
			switchBox(boxEditarProfe, boxBuscarProfe, boxNuevoProfe);
			switchButton(btnEditarProfe, btnBuscarProfe, btnNuevoProfe);
		} else {
			editarProfe();
		}
	}

	@FXML
	private void switchAsignarModulo() {
		if (!boxAsignacionModulo.isVisible()) {
			switchBox(boxAsignacionModulo, boxEdicionModulo, boxCreacionModulo);
			switchButton(btnAsignarModulo, btnEditarModulo, btnCrearModulo);
		} else {
			asignarModulo();
		}
	}

	@FXML
	private void switchNuevoModulo() {
		if (!boxCreacionModulo.isVisible()) {
			switchBox(boxCreacionModulo, boxEdicionModulo, boxAsignacionModulo);
			switchButton(btnCrearModulo, btnAsignarModulo, btnEditarModulo);
		} else {
			guardarModulo();
		}
	}

	@FXML
	private void switchEditarModulo() {
		if (!boxEdicionModulo.isVisible()) {
			switchBox(boxEdicionModulo, boxCreacionModulo, boxAsignacionModulo);
			switchButton(btnEditarModulo, btnAsignarModulo, btnCrearModulo);
		} else {
			editarModulo();
		}
	}

	private void switchButton(Button activo, Button... inactivos) {

		activo.getStyleClass().removeAll("btn-secondary", "btn-primary");
		activo.getStyleClass().add("btn-primary");

		for (Button b : inactivos) {
			b.getStyleClass().removeAll("btn-secondary", "btn-primary");
			b.getStyleClass().add("btn-secondary");
		}
	}

	private void switchBox(HBox activo, HBox... inactivos) {

		activo.setManaged(true);
		activo.setVisible(true);

		for (HBox h : inactivos) {
			h.setManaged(false);
			h.setVisible(false);
		}
	}

	@FXML
	private void buscarProfe() {
		String texto = txtBuscadorProfe.getText().trim();
		if (texto.isEmpty()) {
			cargarProfes();
		} else {
			tablaProfes.setItems(FXCollections.observableArrayList(prService.findByNombre(texto)));
		}
	}

	@FXML
	private void guardarProfe() {

		if (validarProfe(txtNombreProfe, lblNombreProfeError, txtEmailProfe, lblEmailProfeError, txtUsernameProfe,
				lblUsernameProfeError, txtPasswordProfe, lblPasswordProfeError, false))
			return;

		Profesor profe = new Profesor();

		profe.setNombre(txtNombreProfe.getText());
		profe.setEmail(txtEmailProfe.getText());
		profe.setUser(txtUsernameProfe.getText());
		profe.setPassword(Hasher.md5(txtPasswordProfe.getText()));
		profe.setPerfil(Perfil.PROFESORADO);

		peService.save(profe);

		switchBuscarProfe();
		cargarProfes();
		limpiarForm(txtNombreProfe, txtEmailProfe, txtUsernameProfe, txtPasswordProfe);
	}

	private void cargarProfeEditar(Profesor profe) {
		if (profe != null) {
			txtEditNombreProfe.setText(profe.getNombre());
			txtEditEmailProfe.setText(profe.getEmail());
			txtEditUsernameProfe.setText(profe.getUser());
			txtEditPasswordProfe.setText(profe.getPassword());
		}
	}

	private void editarProfe() {
		Profesor profe = cbProfeEditar.getValue();
		if (profe == null)
			return;

		if (validarProfe(txtEditNombreProfe, lblEditNombreProfeError, txtEditEmailProfe, lblEditEmailProfeError,
				txtEditUsernameProfe, lblEditUsernameProfeError, txtEditPasswordProfe, lblEditPasswordProfeError, true))
			return;

		profe.setNombre(txtEditNombreProfe.getText());
		profe.setEmail(txtEditEmailProfe.getText());
		profe.setUser(txtEditUsernameProfe.getText());
		profe.setPassword(Hasher.md5(txtEditPasswordProfe.getText()));

		prService.update(profe);

		switchBuscarProfe();
		cargarProfes();
		limpiarForm(txtEditNombreProfe, txtEditEmailProfe, txtEditUsernameProfe, txtEditPasswordProfe);
	}

	private boolean validarProfe(TextField tfNombre, Label lblNombre, TextField tfEmail, Label lblEmail,
			TextField tfUser, Label lblUser, PasswordField tfPassword, Label lblPassword, boolean edit) {

		Persona profEmail = peService.findByEmail(tfEmail.getText());
		Persona profUser = peService.findByUser(tfUser.getText());
		Persona profEdit = edit ? cbProfeEditar.getValue() : null;

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
			} else if (profEmail != null && (profEdit == null || !profEmail.getId().equals(profEdit.getId()))) {
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
			} else if (profUser != null && (profEdit == null || !profUser.getId().equals(profEdit.getId()))) {
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

	@FXML
	private void asignarCoordinador() {

		Curso cursoSeleccionado = tablaCursos.getSelectionModel().getSelectedItem();

		Profesor coordinador = cbCoordinador.getValue();

		if (cursoSeleccionado == null || coordinador == null) {
			return;
		}

		cursoSeleccionado.setCoordinador(coordinador);

		cuService.update(cursoSeleccionado);

		cargarCursos();
	}

	private void guardarModulo() {

		String txtNombre = txtNombreModulo.getText();
		boolean nombre = txtNombre.isEmpty();
		txtNombreModulo.pseudoClassStateChanged(EMPTY, nombre);

		if (!nombre) {
			if (txtNombre.length() <= 2) {
				nombre = true;
				lblNombreModuloError.setText("El nombre del modulo debe contener más de 2 caracteres.");
			} else if (moService.findByNombre(txtNombre) != null) {
				nombre = true;
				lblNombreModuloError.setText("Ya existe un módulo con este nombre en la base de datos.");
			}

			lblNombreModuloError.setManaged(nombre);
			lblNombreModuloError.setVisible(nombre);
		} else {
			lblNombreModuloError.setManaged(false);
			lblNombreModuloError.setVisible(false);
		}

		if (nombre)
			return;

		Modulo modulo = new Modulo();
		modulo.setNombre(txtNombre);
		moService.save(modulo);

		txtNombreModulo.clear();

		cargarModulos();
	}

	private void asignarModulo() {

		Modulo modulo = tablaModulos.getSelectionModel().getSelectedItem();
		Curso curso = cbCurso.getValue();
		Profesor profe = cbProfesorModulo.getValue();

		cbCurso.pseudoClassStateChanged(EMPTY, curso == null);
		cbProfesorModulo.pseudoClassStateChanged(EMPTY, profe == null);

		if (modulo == null || curso == null || profe == null) {
			return;
		}

		ModuloCurso mc = mcService.findByModulo(modulo);

		if (mc == null)
			mc = new ModuloCurso();

		mc.setModulo(modulo);
		mc.setCurso(curso);
		mc.setProfesor(profe);

		mcService.save(mc);

		cargarModulos();

	}

	private void editarModulo() {

		Modulo modulo = cbModuloEditar.getValue();

		String txtNombre = txtEditNombreModulo.getText();
		boolean nombre = txtNombre.isEmpty();
		txtEditNombreModulo.pseudoClassStateChanged(EMPTY, nombre);

		if (!nombre) {
			if (txtNombre.length() <= 2) {
				nombre = true;
				lblEditNombreModuloError.setText("El nombre del modulo debe contener más de 2 caracteres.");
			} else if (moService.findByNombre(txtNombre) != null) {
				nombre = true;
				lblEditNombreModuloError.setText("Ya existe un módulo con este nombre en la base de datos.");
			}

			lblEditNombreModuloError.setManaged(nombre);
			lblEditNombreModuloError.setVisible(nombre);
		} else {
			lblEditNombreModuloError.setManaged(false);
			lblEditNombreModuloError.setVisible(false);
		}

		if (nombre || modulo == null)
			return;

		modulo.setNombre(txtNombre);

		moService.update(modulo);

		cargarModulos();
	}

	private void limpiarForm(TextField tfNombre, TextField tfEmail, TextField tfUser, PasswordField tfPassword) {
		tfNombre.clear();
		tfEmail.clear();
		tfUser.clear();
		tfPassword.clear();
	}

	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
