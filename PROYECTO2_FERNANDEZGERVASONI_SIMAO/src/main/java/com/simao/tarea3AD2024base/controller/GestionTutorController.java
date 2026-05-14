package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Empresa;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.services.EmpresaService;
import com.simao.tarea3AD2024base.services.Hasher;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.TutorService;
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
public class GestionTutorController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;

	@Autowired
	private TutorService tuService;

	@Autowired
	private EmpresaService emService;

	@EventListener
	public void onNewEmpresa(NewEmpresaEvent event) {
		cargarEmpresas();
	}

	@Autowired
	private ApplicationEventPublisher evPublisher;

	@FXML
	private HBox boxBuscar;

	@FXML
	private HBox boxNuevo;

	@FXML
	private HBox boxEditar;

	@FXML
	private TextField txtBuscador;

	@FXML
	private TextField txtNombre;

	@FXML
	private Label lblNombreError;

	@FXML
	private TextField txtEmail;

	@FXML
	private Label lblEmailError;

	@FXML
	private ComboBox<Empresa> cbEmpresas;

	@FXML
	private TextField txtUsername;

	@FXML
	private Label lblUsernameError;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblPasswordError;

	@FXML
	private ComboBox<Tutor> cbEditarTutor;

	@FXML
	private TextField txtEditNombre;

	@FXML
	private Label lblEditNombreError;

	@FXML
	private TextField txtEditEmail;

	@FXML
	private Label lblEditEmailError;

	@FXML
	private ComboBox<Empresa> cbEditEmpresas;

	@FXML
	private TextField txtEditUsername;

	@FXML
	private Label lblEditUsernameError;

	@FXML
	private PasswordField txtEditPassword;

	@FXML
	private Label lblEditPasswordError;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnNuevo;

	@FXML
	private Button btnEditar;

	@FXML
	private TableView<Tutor> tablaTutores;

	@FXML
	private TableColumn<Tutor, String> colNombre;

	@FXML
	private TableColumn<Tutor, String> colEmail;

	@FXML
	private TableColumn<Tutor, String> colEmpresa;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbEditarTutor.getSelectionModel().selectedItemProperty().addListener((_, _, tutor) -> {
			cargarEditar(tutor);
		});

		cargarTutores();
		cargarEmpresas();
	}

	private void cargarTutores() {
		List<Tutor> tutores = tuService.findAll();
		ObservableList<Tutor> datos = FXCollections.observableArrayList(tutores);

		colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
		colEmpresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmpresa().getNombre()));

		if (!tutores.isEmpty()) {
			tablaTutores.setItems(datos);
			cbEditarTutor.setItems(datos);
		}
	}

	private void cargarEmpresas() {
		List<Empresa> empresas = emService.findAll();
		cbEmpresas.setItems(emService.getObservableEmpresas());
		ObservableList<Empresa> datos = FXCollections.observableArrayList(empresas);
		cbEmpresas.setItems(datos);
		cbEditEmpresas.setItems(datos);
	}

	@FXML
	private void switchBuscar() {
		if (!boxBuscar.isVisible()) {
			switchBox(boxBuscar, boxEditar, boxNuevo);
			switchButton(btnBuscar, btnEditar, btnNuevo);
		} else {
			buscar();
		}
	}

	@FXML
	private void switchNuevo() {
		if (!boxNuevo.isVisible()) {
			switchBox(boxNuevo, boxEditar, boxBuscar);
			switchButton(btnNuevo, btnEditar, btnBuscar);
		} else {
			guardar();
		}
	}

	@FXML
	private void switchEditar() {
		if (!boxEditar.isVisible()) {
			switchBox(boxEditar, boxBuscar, boxNuevo);
			switchButton(btnEditar, btnBuscar, btnNuevo);
		} else {
			editar();
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
	private void buscar() {
		String txt = txtBuscador.getText().trim();
		if (txt.isEmpty()) {
			cargarTutores();
		} else {
			tablaTutores.setItems(FXCollections.observableArrayList(tuService.findByNombre(txt)));
		}
	}

	@FXML
	private void guardar() {
		if (validar(txtNombre, lblNombreError, txtEmail, lblEmailError, cbEmpresas, txtUsername, lblUsernameError,
				txtPassword, lblPasswordError, false))
			return;

		Tutor tutor = new Tutor();

		tutor.setNombre(txtNombre.getText());
		tutor.setEmail(txtEmail.getText());

		tutor.setEmpresa(cbEmpresas.getValue());

		tutor.setUser(txtUsername.getText());
		tutor.setPassword(Hasher.md5(txtPassword.getText()));
		tutor.setPerfil(Perfil.TUTOR);

		peService.save(tutor);

		switchBuscar();
		cargarTutores();
		limpiarForm(txtNombre, txtEmail, cbEmpresas, txtUsername, txtPassword);

		evPublisher.publishEvent(new NewTutorEvent(tutor));
	}

	private void cargarEditar(Tutor tutor) {
		if (tutor != null) {
			txtEditNombre.setText(tutor.getNombre());
			txtEditEmail.setText(tutor.getEmail());

			cbEditEmpresas.setValue(tutor.getEmpresa());

			txtEditUsername.setText(tutor.getUser());
		}
	}

	private void editar() {
		Tutor tutor = cbEditarTutor.getValue();
		if (tutor == null)
			return;

		if (validar(txtEditNombre, lblEditNombreError, txtEditEmail, lblEditEmailError, cbEditEmpresas, txtEditUsername,
				lblEditUsernameError, txtEditPassword, lblEditPasswordError, true))
			return;

		tutor.setNombre(txtEditNombre.getText());
		tutor.setEmail(txtEditEmail.getText());

		tutor.setEmpresa(cbEditEmpresas.getValue());

		tutor.setUser(txtEditUsername.getText());
		tutor.setPassword(Hasher.md5(txtEditPassword.getText()));

		tuService.update(tutor);

		switchBuscar();
		cargarTutores();
		limpiarForm(txtEditNombre, txtEditEmail, cbEditEmpresas, txtEditUsername, txtEditPassword);

		evPublisher.publishEvent(new NewTutorEvent(tutor));
	}

	private boolean validar(TextField tfNombre, Label lblNombre, TextField tfEmail, Label lblEmail,
			ComboBox<Empresa> cbEmp, TextField tfUser, Label lblUser, PasswordField tfPassword, Label lblPassword,
			boolean edit) {

		Persona tuEmail = peService.findByEmail(tfEmail.getText());
		Persona tuUser = peService.findByUser(tfUser.getText());
		Persona tuEdit = edit ? cbEditarTutor.getValue() : null;

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
			} else if (tuEmail != null && (tuEdit == null || !tuEmail.getId().equals(tuEdit.getId()))) {
				email = true;
				lblEmail.setText("Este correo ya está registrado en la base de datos.");
			}

			lblEmail.setManaged(email);
			lblEmail.setVisible(email);

		} else {
			lblEmail.setManaged(false);
			lblEmail.setVisible(false);
		}

		boolean empresa = cbEmp.getValue() == null;
		cbEmp.pseudoClassStateChanged(EMPTY, empresa);

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
			} else if (tuUser != null && (tuEdit == null || !tuUser.getId().equals(tuEdit.getId()))) {
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

		return nombre || email || empresa || username || password;
	}

	private void limpiarForm(TextField tfNombre, TextField tfEmail, ComboBox<Empresa> cbEmp, TextField tfUser,
			PasswordField tfPassword) {
		tfNombre.clear();
		tfEmail.clear();
		cbEmp.getSelectionModel().clearSelection();
		cbEmp.setValue(null);
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
