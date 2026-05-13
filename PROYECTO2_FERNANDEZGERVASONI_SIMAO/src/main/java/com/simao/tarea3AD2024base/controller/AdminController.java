package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Grupo;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloGrupo;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.services.GrupoService;
import com.simao.tarea3AD2024base.services.ModuloGrupoService;
import com.simao.tarea3AD2024base.services.ModuloService;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.layout.VBox;

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
	private GrupoService grService;

	@Autowired
	private ModuloService moService;

	@Autowired
	private ModuloGrupoService mgService;

	@FXML
	private TableView<Profesor> tablaProfes;

	@FXML
	private VBox formProfe;

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
	private TableColumn<Profesor, String> colNombreProfe;

	@FXML
	private TableColumn<Profesor, String> colEmailProfe;

	@FXML
	private TextField buscadorProfe;

	@FXML
	private TableView<Grupo> tablaGrupos;

	@FXML
	private ComboBox<Profesor> cbCoordinador;

	@FXML
	private TableColumn<Grupo, String> colCodigo;

	@FXML
	private TableColumn<Grupo, String> colCiclo;

	@FXML
	private TableColumn<Grupo, Integer> colCurso;

	@FXML
	private TableColumn<Grupo, String> colCoordinador;

	@FXML
	private ComboBox<Grupo> cbGrupo;

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
	private TableColumn<Modulo, String> colGrupoModulo;

	@FXML
	private TableColumn<Modulo, String> colProfesorModulo;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cargarTablaProfes();
		cargarGrupos();
		cargarProfes();
		cargarModulos();
	}

	private void cargarTablaProfes() {
		colNombreProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmailProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
	}

	private void cargarProfes() {
		List<Profesor> profes = prService.findAll();
		ObservableList<Profesor> datos = FXCollections.observableArrayList(profes);

		if (!profes.isEmpty()) {
			tablaProfes.setItems(datos);
			cbCoordinador.setItems(datos);
			cbProfesorModulo.setItems(datos);
		}
	}

	private void cargarGrupos() {
		List<Grupo> grupos = grService.findAll();
		ObservableList<Grupo> datos = FXCollections.observableArrayList(grupos);

		cbGrupo.setItems(datos);

		if (!grupos.isEmpty()) {
			ObservableList<Grupo> lista = datos;
			tablaGrupos.setItems(lista);
		}

		colCodigo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
		colCiclo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCiclo().toString()));
		colCurso.setCellValueFactory(data -> new SimpleObjectProperty<>(data.getValue().getCurso()));
		colCoordinador.setCellValueFactory(data -> {

			Profesor coordinador = data.getValue().getCoordinador();

			String nombre = coordinador != null ? coordinador.getNombre() : "Sin asignar";

			return new SimpleStringProperty(nombre);
		});
	}

	public void cargarModulos() {

		List<Modulo> modulos = moService.findAll();

		ObservableList<Modulo> datos = FXCollections.observableArrayList(modulos);

		tablaModulos.setItems(datos);
		cbModuloEditar.setItems(datos);
		colModulo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colGrupoModulo.setCellValueFactory(data -> {

			ModuloGrupo mg = mgService.findByModulo(data.getValue());

			String grupo = (mg != null && mg.getGrupo() != null) ? mg.getGrupo().getCodigo() : "Sin asignar";

			return new SimpleStringProperty(grupo);
		});

		colProfesorModulo.setCellValueFactory(data -> {

			ModuloGrupo mg = mgService.findByModulo(data.getValue());

			String prof = (mg != null && mg.getProfesor() != null) ? mg.getProfesor().getNombre() : "Sin asignar";

			return new SimpleStringProperty(prof);
		});
	}

	@FXML
	private void switchFormProfe() {
		boolean b = tablaProfes.isVisible();
		tablaProfes.setVisible(!b);
		tablaProfes.setManaged(!b);
		formProfe.setVisible(b);
		formProfe.setManaged(b);
	}

	@FXML
	private void btnAsignarModulo() {
		if (!boxAsignacionModulo.isVisible()) {
			boxAsignacionModulo.setManaged(true);
			boxAsignacionModulo.setVisible(true);
			boxCreacionModulo.setManaged(false);
			boxCreacionModulo.setVisible(false);
			boxEdicionModulo.setVisible(false);
			boxEdicionModulo.setManaged(false);

			switchButton(btnAsignarModulo, btnEditarModulo, btnCrearModulo);
		} else {
			asignarModulo();
		}
	}

	@FXML
	private void btnNuevoModulo() {
		if (!boxCreacionModulo.isVisible()) {
			boxAsignacionModulo.setManaged(false);
			boxAsignacionModulo.setVisible(false);
			boxCreacionModulo.setManaged(true);
			boxCreacionModulo.setVisible(true);
			boxEdicionModulo.setVisible(false);
			boxEdicionModulo.setManaged(false);

			switchButton(btnCrearModulo, btnAsignarModulo, btnEditarModulo);
		} else {
			guardarModulo();
		}
	}

	@FXML
	private void btnEditarModulo() {
		if (!boxEdicionModulo.isVisible()) {
			boxAsignacionModulo.setManaged(false);
			boxAsignacionModulo.setVisible(false);
			boxCreacionModulo.setManaged(false);
			boxCreacionModulo.setVisible(false);
			boxEdicionModulo.setVisible(true);
			boxEdicionModulo.setManaged(true);

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

		if (validarProfe())
			return;

		Profesor profe = new Profesor();

		profe.setNombre(txtNombreProfe.getText());
		profe.setEmail(txtEmailProfe.getText());
		profe.setUser(txtUsernameProfe.getText());
		profe.setPassword(txtPasswordProfe.getText());
		profe.setPerfil(Perfil.PROFESORADO);

		peService.save(profe);

		switchFormProfe();
		cargarProfes();
	}

	private boolean validarProfe() {

		String txtNombre = txtNombreProfe.getText();
		boolean nombre = txtNombre.isEmpty();
		txtNombreProfe.pseudoClassStateChanged(EMPTY, nombre);

		if (!nombre) {
			if (!Pattern.matches("^[\\p{L}]+( [\\p{L}]+)*$", txtNombre)) {
				nombre = true;
				lblNombreProfeError.setText("El nombre solo puede contener caracteres unicode y espacios.");
			}

			lblNombreProfeError.setManaged(nombre);
			lblNombreProfeError.setVisible(nombre);

		} else {
			lblNombreProfeError.setManaged(false);
			lblNombreProfeError.setVisible(false);
		}

		String txtEmail = txtEmailProfe.getText();
		boolean email = txtEmail.isEmpty();
		txtEmailProfe.pseudoClassStateChanged(EMPTY, email);

		if (!email) {
			if (!Pattern.matches("^[\\w._%+-]+@[\\w.-]+\\.[a-zA-Z]{2,}$", txtEmail)) {
				email = true;
				lblEmailProfeError.setText("El email no es válido.");
			} else if (peService.findByEmail(txtEmail) != null) {
				email = true;
				lblEmailProfeError.setText("Este correo ya está registrado en la base de datos.");
			}

			lblEmailProfeError.setManaged(email);
			lblEmailProfeError.setVisible(email);

		} else {
			lblEmailProfeError.setManaged(false);
			lblEmailProfeError.setVisible(false);
		}

		String txtUsername = txtUsernameProfe.getText();
		boolean username = txtUsername.isEmpty();
		txtUsernameProfe.pseudoClassStateChanged(EMPTY, username);

		if (!username) {
			if (txtUsername.length() <= 2) {
				username = true;
				lblUsernameProfeError.setText("El nombre de usuario debe contener más de 2 caracteres.");
			} else if (txtUsername.contains(" ")) {
				username = true;
				lblUsernameProfeError.setText("El nombre de usuario no debe contener espacios.");
			} else if (!Pattern.matches("^[a-z]+$", txtUsername)) {
				username = true;
				lblUsernameProfeError.setText(
						"El nombre de usuario no debe contener números ni letras mayúsculas o con tíldes o dieresis.");
			} else if (peService.findByUser(txtUsername) != null) {
				username = true;
				lblUsernameProfeError.setText("Este nombre de usuario ya está registrado en la base de datos.");
			}

			lblUsernameProfeError.setManaged(username);
			lblUsernameProfeError.setVisible(username);

		} else {
			lblUsernameProfeError.setManaged(false);
			lblUsernameProfeError.setVisible(false);
		}

		String txtPassword = txtPasswordProfe.getText();
		boolean password = txtPassword.isEmpty();
		txtPasswordProfe.pseudoClassStateChanged(EMPTY, password);
		if (!password) {
			if (txtPassword.length() <= 2) {
				password = true;
				lblPasswordProfeError.setText("La contraseña debe contener más de 2 caracteres.");
			} else if (txtPassword.contains(" ")) {
				password = true;
				lblPasswordProfeError.setText("La contraseña no debe contener espacios.");
			}

			lblPasswordProfeError.setManaged(password);
			lblPasswordProfeError.setVisible(password);

		} else {
			lblPasswordProfeError.setManaged(false);
			lblPasswordProfeError.setVisible(false);
		}

		return nombre || email || username || password;
	}

	@FXML
	public void asignarCoordinador() {

		Grupo grupoSeleccionado = tablaGrupos.getSelectionModel().getSelectedItem();

		Profesor coordinador = cbCoordinador.getValue();

		if (grupoSeleccionado == null || coordinador == null) {
			return;
		}

		grupoSeleccionado.setCoordinador(coordinador);

		grService.update(grupoSeleccionado);

		cargarGrupos();
	}

	@FXML
	public void guardarModulo() {

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

	@FXML
	public void asignarModulo() {

		Modulo modulo = tablaModulos.getSelectionModel().getSelectedItem();
		Grupo grupo = cbGrupo.getValue();
		Profesor profe = cbProfesorModulo.getValue();

		cbGrupo.pseudoClassStateChanged(EMPTY, grupo == null);
		cbProfesorModulo.pseudoClassStateChanged(EMPTY, profe == null);

		if (modulo == null || grupo == null || profe == null) {
			return;
		}

		ModuloGrupo mg = mgService.findByModulo(modulo);

		if (mg == null)
			mg = new ModuloGrupo();

		mg.setModulo(modulo);
		mg.setGrupo(grupo);
		mg.setProfesor(profe);

		mgService.save(mg);

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

	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
