package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.services.AlumnoService;
import com.simao.tarea3AD2024base.services.CursoService;
import com.simao.tarea3AD2024base.services.FormacionEmpresaService;
import com.simao.tarea3AD2024base.services.Hasher;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.Session;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Clase GestionAlumnoController.java
 * 
 * Gestiona las interacciones con la interfaz de gestión de alumnos.
 */
@Controller
public class GestionAlumnoController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private PersonaService peService;

	@Autowired
	private AlumnoService alService;

	@Autowired
	private CursoService cuService;

	@Autowired
	private FormacionEmpresaService feService;

	@Autowired
	private Session session;

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
	private ComboBox<Curso> cbCursos;

	@FXML
	private CheckBox checkEdad;

	@FXML
	private TextField txtUsername;

	@FXML
	private Label lblUsernameError;

	@FXML
	private PasswordField txtPassword;

	@FXML
	private Label lblPasswordError;

	@FXML
	private ComboBox<Alumno> cbEditarAlumno;

	@FXML
	private TextField txtEditNombre;

	@FXML
	private Label lblEditNombreError;

	@FXML
	private TextField txtEditEmail;

	@FXML
	private Label lblEditEmailError;

	@FXML
	private ComboBox<Curso> cbEditCursos;

	@FXML
	private CheckBox checkEditEdad;

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
	private TableView<Alumno> tablaAlumnos;

	@FXML
	private TableColumn<Alumno, String> colNombre;

	@FXML
	private TableColumn<Alumno, String> colEmail;

	@FXML
	private TableColumn<Alumno, String> colCurso;

	@FXML
	private TableColumn<Alumno, String> colEdad;

	/**
	 * Inicializa el controlador, carga los alumnos de la base de datos y configura
	 * la vista diferenciando si el perfil de usuario es Tutor.
	 * 
	 * Si el usuario no es tutor, registra el listener del ComboBox de edición para
	 * cargar automáticamente los datos seleccionados en los formularios
	 * correspondientes y carga los cursos.
	 * 
	 * Si es tutor, esconde los botones de cambio de menú.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		if (session.getPerfil() == Perfil.TUTOR) {
			btnNuevo.setVisible(false);
			btnNuevo.setManaged(false);
			btnBuscar.setVisible(false);
			btnBuscar.setManaged(false);
			btnEditar.setVisible(false);
			btnEditar.setManaged(false);
		} else {
			cbEditarAlumno.getSelectionModel().selectedItemProperty().addListener((_, _, alumno) -> {
				cargarEditar(alumno);
			});

			cargarCursos();
		}

		cargarAlumnos();

	}

	/**
	 * Carga todos los alumnos desde la base de datos y los muestra en la interfaz.
	 * Si el usuario es un tutor, solo carga los que tenga asociados.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes.
	 */
	private void cargarAlumnos() {
		List<Alumno> alumnos;

		if (session.getPerfil() == Perfil.TUTOR)
			alumnos = feService.getAlumnosByTutor(session.getUserId());
		else
			alumnos = alService.findAll();

		ObservableList<Alumno> datos = FXCollections.observableArrayList(alumnos);

		colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmail.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));
		colCurso.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCurso().getNombre()));
		colEdad.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().isMayoriaEdad() ? "Mayor de edad" : "Menor de edad"));

		if (!alumnos.isEmpty()) {
			tablaAlumnos.setItems(datos);
			cbEditarAlumno.setItems(datos);
		}
	}

	/**
	 * Carga todos los Cursos desde la base de datos y los muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes.
	 */
	private void cargarCursos() {
		List<Curso> cursos = cuService.findAll();
		ObservableList<Curso> datos = FXCollections.observableArrayList(cursos);
		cbCursos.setItems(datos);
		cbEditCursos.setItems(datos);
	}

	/**
	 * Alterna la visivilidad del menú de búsqueda de Alumnos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchBuscar() {
		if (!boxBuscar.isVisible()) {
			switchBox(boxBuscar, boxEditar, boxNuevo);
			switchButton(btnBuscar, btnEditar, btnNuevo);
		} else {
			buscar();
		}
	}

	/**
	 * Alterna la visivilidad del menú de creación de Alumnos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchNuevo() {
		if (!boxNuevo.isVisible()) {
			switchBox(boxNuevo, boxEditar, boxBuscar);
			switchButton(btnNuevo, btnEditar, btnBuscar);
		} else {
			guardar();
		}
	}

	/**
	 * Alterna la visivilidad del menú de edición de Alumnos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchEditar() {
		if (!boxEditar.isVisible()) {
			switchBox(boxEditar, boxBuscar, boxNuevo);
			switchButton(btnEditar, btnBuscar, btnNuevo);
		} else {
			editar();
		}
	}

	/**
	 * Da a un botón un estilo primario y le da uno secundario al resto.
	 * 
	 * El botón activo recibe el estilo "btn-primary", mientras que los botones
	 * inactivos reciben el estilo "btn-secondary".
	 *
	 * @param activo    Botón que se marcará como primario
	 * @param inactivos Botones que se marcarán como secundarios
	 */
	private void switchButton(Button activo, Button... inactivos) {

		activo.getStyleClass().removeAll("btn-secondary", "btn-primary");
		activo.getStyleClass().add("btn-primary");

		for (Button b : inactivos) {
			b.getStyleClass().removeAll("btn-secondary", "btn-primary");
			b.getStyleClass().add("btn-secondary");
		}
	}

	/**
	 * Muestra un HBox y oculta el resto.
	 * 
	 * El HBox activo pasa a ser visible y los inactivos se ocultan y dejan de
	 * ocupar espacio en la interfaz.
	 *
	 * @param activo    HBox que se mostrará
	 * @param inactivos HBox que se ocultarán
	 */
	private void switchBox(HBox activo, HBox... inactivos) {

		activo.setManaged(true);
		activo.setVisible(true);

		for (HBox h : inactivos) {
			h.setManaged(false);
			h.setVisible(false);
		}
	}

	/**
	 * Filtra la lista de alumnos según el nombre introducido en el buscador.
	 * 
	 * Si el campo de búsqueda está vacío, se recargan todos los alumnos. Si no, se
	 * muestran solo los que coincidan con el nombre.
	 */
	@FXML
	private void buscar() {
		String txt = txtBuscador.getText().trim();
		if (txt.isEmpty()) {
			cargarAlumnos();
		} else {
			tablaAlumnos.setItems(FXCollections.observableArrayList(alService.findByNombre(txt)));
		}
	}

	/**
	 * Valida y guarda un nuevo alumno.
	 * 
	 * Si la validación es correcta, se crea una nueva entidad Alumno, se guarda en
	 * la base de datos, se actualiza la lista de alumnos y se limpia el formulario,
	 * además de notificar la creación por medio de un evento.
	 */
	@FXML
	private void guardar() {
		if (validar(txtNombre, lblNombreError, txtEmail, lblEmailError, cbCursos, txtUsername, lblUsernameError,
				txtPassword, lblPasswordError, false))
			return;

		Alumno alumno = new Alumno();

		alumno.setNombre(txtNombre.getText());
		alumno.setEmail(txtEmail.getText());

		alumno.setCurso(cbCursos.getValue());
		alumno.setMayoriaEdad(checkEdad.isSelected());

		alumno.setUser(txtUsername.getText());
		alumno.setPassword(Hasher.md5(txtPassword.getText()));
		alumno.setPerfil(Perfil.ALUMNADO);

		peService.save(alumno);

		switchBuscar();
		cargarAlumnos();
		limpiarForm(txtNombre, txtEmail, cbCursos, txtUsername, txtPassword);

		evPublisher.publishEvent(new NewAlumnoEvent(alumno));
	}

	/**
	 * Carga los datos de un alumno en el formulario de edición.
	 *
	 * @param alumno Alumno cuyos datos se cargarán en el formulario.
	 */
	private void cargarEditar(Alumno alumno) {
		if (alumno != null) {
			txtEditNombre.setText(alumno.getNombre());
			txtEditEmail.setText(alumno.getEmail());

			cbEditCursos.setValue(alumno.getCurso());
			checkEditEdad.setSelected(alumno.isMayoriaEdad());

			txtEditUsername.setText(alumno.getUser());
		}
	}

	/**
	 * Valida y edita un alumno.
	 * 
	 * Si la validación es correcta, actualiza la entidad Alumno seleccionada en la
	 * base de datos, se actualiza la lista de alumnos y se limpia el formulario,
	 * además de notificar la actualización por medio de un evento.
	 */
	private void editar() {
		Alumno alumno = cbEditarAlumno.getValue();
		if (alumno == null)
			return;

		if (validar(txtEditNombre, lblEditNombreError, txtEditEmail, lblEditEmailError, cbEditCursos, txtEditUsername,
				lblEditUsernameError, txtEditPassword, lblEditPasswordError, true))
			return;

		alumno.setNombre(txtEditNombre.getText());
		alumno.setEmail(txtEditEmail.getText());

		alumno.setCurso(cbEditCursos.getValue());
		alumno.setMayoriaEdad(checkEditEdad.isSelected());

		alumno.setUser(txtEditUsername.getText());
		alumno.setPassword(Hasher.md5(txtEditPassword.getText()));

		alService.update(alumno);

		switchBuscar();
		cargarAlumnos();
		limpiarForm(txtEditNombre, txtEditEmail, cbEditCursos, txtEditUsername, txtEditPassword);

		evPublisher.publishEvent(new NewAlumnoEvent(alumno));
	}

	/**
	 * Valida los datos de un alumno antes de su creación o edición.
	 * 
	 * Comprueba la validez de los siguientes campos: - Nombre: no vacío y formato
	 * válido (solo letras y espacios) - Email: no vacío, formato válido y no
	 * duplicado - Curso: no vacío - Nombre de usuario: no vacío, sin espacios,
	 * formato válido y no duplicado - Contraseña: no vacía y sin espacios
	 * 
	 * En modo edición se ignoran duplicidades respecto al propio alumno.
	 * 
	 * Durante la validación se actualiza la interfaz mostrando errores y aplicando
	 * estilos visuales a los campos afectados.
	 * 
	 * @param tfNombre
	 * @param lblNombre
	 * @param tfEmail
	 * @param lblEmail
	 * @param cbCur
	 * @param tfUser
	 * @param lblUser
	 * @param tfPassword
	 * @param lblPassword
	 * @param edit        indica si se trata de edición (true) o creación (false)
	 * @return true si hay errores de validación, false si los datos son válidos
	 */
	private boolean validar(TextField tfNombre, Label lblNombre, TextField tfEmail, Label lblEmail,
			ComboBox<Curso> cbCur, TextField tfUser, Label lblUser, PasswordField tfPassword, Label lblPassword,
			boolean edit) {

		Persona alEmail = peService.findByEmail(tfEmail.getText());
		Persona alUser = peService.findByUser(tfUser.getText());
		Persona alEdit = edit ? cbEditarAlumno.getValue() : null;

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
			} else if (alEmail != null && (alEdit == null || !alEmail.getId().equals(alEdit.getId()))) {
				email = true;
				lblEmail.setText("Este correo ya está registrado en la base de datos.");
			}

			lblEmail.setManaged(email);
			lblEmail.setVisible(email);

		} else {
			lblEmail.setManaged(false);
			lblEmail.setVisible(false);
		}

		boolean curso = cbCur.getValue() == null;
		cbCur.pseudoClassStateChanged(EMPTY, curso);

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
			} else if (alUser != null && (alEdit == null || !alUser.getId().equals(alEdit.getId()))) {
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

		return nombre || email || curso || username || password;
	}

	/**
	 * Limpia la información introducida en los campos del formulario de creación o
	 * edición de Alumnos.
	 * 
	 * @param tfNombre
	 * @param tfEmail
	 * @param cbCur
	 * @param tfUser
	 * @param tfPassword
	 */
	private void limpiarForm(TextField tfNombre, TextField tfEmail, ComboBox<Curso> cbCur, TextField tfUser,
			PasswordField tfPassword) {
		tfNombre.clear();
		tfEmail.clear();
		cbCur.getSelectionModel().clearSelection();
		cbCur.setValue(null);
		tfUser.clear();
		tfPassword.clear();
	}
}
