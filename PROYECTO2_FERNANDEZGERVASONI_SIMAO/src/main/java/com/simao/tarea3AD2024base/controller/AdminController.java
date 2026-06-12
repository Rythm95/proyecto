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
import com.simao.tarea3AD2024base.modelo.Curso;
import com.simao.tarea3AD2024base.modelo.Modulo;
import com.simao.tarea3AD2024base.modelo.ModuloCurso;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Persona;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.modelo.ResultadoAprendizaje;
import com.simao.tarea3AD2024base.services.CursoService;
import com.simao.tarea3AD2024base.services.Hasher;
import com.simao.tarea3AD2024base.services.ModuloCursoService;
import com.simao.tarea3AD2024base.services.ModuloService;
import com.simao.tarea3AD2024base.services.PersonaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.services.ResultadoAprendizajeService;
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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Clase AdminController.java
 * 
 * Gestiona las interacciones con la interfaz de administración del sistema.
 */

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

	@Autowired
	private ResultadoAprendizajeService raService;

	@Autowired
	private ApplicationEventPublisher evPublisher;

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
	private ComboBox<Curso> cbCurso;

	@FXML
	private ComboBox<Profesor> cbProfesorModulo;

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
	private ComboBox<Curso> cbCursoEdit;

	@FXML
	private ComboBox<Profesor> cbProfesorModuloEdit;

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

	@FXML
	private HBox boxBuscarRA;

	@FXML
	private TextField txtBuscadorRA;

	@FXML
	private HBox boxCreacionRA;

	@FXML
	private ComboBox<Modulo> cbModuloRA;

	@FXML
	private TextField txtNombreRA;

	@FXML
	private Label lblNombreRAError;

	@FXML
	private TextArea txtDescRA;

	@FXML
	private HBox boxEdicionRA;

	@FXML
	private ComboBox<ResultadoAprendizaje> cbRAEdit;

	@FXML
	private ComboBox<Modulo> cbModuloRAEdit;

	@FXML
	private Label lblModuloRAEditError;

	@FXML
	private TextField txtNombreRAEdit;

	@FXML
	private Label lblNombreRAEditError;

	@FXML
	private TextArea txtDescRAEdit;

	@FXML
	private Label lblDescRAEditError;

	@FXML
	private Button btnBuscarRA;

	@FXML
	private Button btnNuevoRA;

	@FXML
	private Button btnEditarRA;

	@FXML
	private TableView<ResultadoAprendizaje> tablaRAs;

	@FXML
	private TableColumn<ResultadoAprendizaje, String> colCodigoRA;

	@FXML
	private TableColumn<ResultadoAprendizaje, String> colDescripcionRA;

	@FXML
	private TableColumn<ResultadoAprendizaje, String> colModuloRA;

	/**
	 * Inicializa el controlador y configura la vista.
	 * 
	 * Registra listeners en los ComboBox de edición para cargar automáticamente los
	 * datos seleccionados en los formularios correspondientes.
	 * 
	 * Además, carga los datos iniciales de cursos, profesores, módulos y resultados
	 * de aprendizaje en las distintas vistas.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbProfeEditar.getSelectionModel().selectedItemProperty().addListener((_, _, profe) -> {
			cargarProfeEditar(profe);
		});

		cbRAEdit.getSelectionModel().selectedItemProperty().addListener((_, _, ra) -> {
			cargarRAEditar(ra);
		});

		cargarCursos();
		cargarProfes();
		cargarModulos();
		cargarRAs();
	}

	/**
	 * Carga todos los Profesores desde la base de datos y los muestra en la
	 * interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes.
	 */
	private void cargarProfes() {
		List<Profesor> profes = prService.findAll();
		ObservableList<Profesor> datos = FXCollections.observableArrayList(profes);

		colNombreProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colEmailProfe.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmail()));

		if (!profes.isEmpty()) {
			tablaProfes.setItems(datos);
			cbCoordinador.setItems(datos);
			cbProfesorModuloEdit.setItems(datos);
			cbProfeEditar.setItems(datos);
			cbProfesorModulo.setItems(datos);
		}
	}

	/**
	 * Carga todos los Cursos desde la base de datos y los muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes, mostrando "Sin
	 * asignar" si no encuentra un valor para el profesor coordinador.
	 */
	private void cargarCursos() {
		List<Curso> cursos = cuService.findAll();
		ObservableList<Curso> datos = FXCollections.observableArrayList(cursos);

		cbCursoEdit.setItems(datos);
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

	/**
	 * Carga todos los Módulos desde la base de datos y los muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes, mostrando "Sin
	 * asignar" si no encuentra un valor para el curso o profesor.
	 */
	private void cargarModulos() {

		List<Modulo> modulos = moService.findAll();
		ObservableList<Modulo> datos = FXCollections.observableArrayList(modulos);

		tablaModulos.setItems(datos);
		cbModuloEditar.setItems(datos);
		cbModuloRA.setItems(datos);
		cbModuloRAEdit.setItems(datos);
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

	/**
	 * Carga todos los Resultados de Aprendizaje desde la base de datos y los
	 * muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, el ComboBox de edición, y configura las
	 * columnas de la tabla con los valores correspondientes.
	 */
	private void cargarRAs() {
		List<ResultadoAprendizaje> ras = raService.findAll();
		ObservableList<ResultadoAprendizaje> datos = FXCollections.observableArrayList(ras);

		cbRAEdit.setItems(datos);
		tablaRAs.setItems(datos);
		colCodigoRA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getCodigo()));
		colDescripcionRA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
		colModuloRA.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getModulo().getNombre()));
	}

	/**
	 * Alterna la visivilidad del menú de búsqueda de Profesores.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchBuscarProfe() {
		if (!boxBuscarProfe.isVisible()) {
			switchBox(boxBuscarProfe, boxEditarProfe, boxNuevoProfe);
			switchButton(btnBuscarProfe, btnEditarProfe, btnNuevoProfe);
		} else {
			buscarProfe();
		}
	}

	/**
	 * Alterna la visivilidad del menú de creación de Profesores.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchNuevoProfe() {
		if (!boxNuevoProfe.isVisible()) {
			switchBox(boxNuevoProfe, boxEditarProfe, boxBuscarProfe);
			switchButton(btnNuevoProfe, btnEditarProfe, btnBuscarProfe);
		} else {
			guardarProfe();
		}
	}

	/**
	 * Alterna la visivilidad del menú de edición de Profesores.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchEditarProfe() {
		if (!boxEditarProfe.isVisible()) {
			switchBox(boxEditarProfe, boxBuscarProfe, boxNuevoProfe);
			switchButton(btnEditarProfe, btnBuscarProfe, btnNuevoProfe);
		} else {
			editarProfe();
		}
	}

	/**
	 * Alterna la visivilidad del menú de asignación de Módulos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchAsignarModulo() {
		if (!boxAsignacionModulo.isVisible()) {
			switchBox(boxAsignacionModulo, boxEdicionModulo, boxCreacionModulo);
			switchButton(btnAsignarModulo, btnEditarModulo, btnCrearModulo);
		} else {
			asignarModulo();
		}
	}

	/**
	 * Alterna la visivilidad del menú de creación de Módulos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchNuevoModulo() {
		if (!boxCreacionModulo.isVisible()) {
			switchBox(boxCreacionModulo, boxEdicionModulo, boxAsignacionModulo);
			switchButton(btnCrearModulo, btnAsignarModulo, btnEditarModulo);
		} else {
			guardarModulo();
		}
	}

	/**
	 * Alterna la visivilidad del menú de edición de Módulos.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchEditarModulo() {
		if (!boxEdicionModulo.isVisible()) {
			switchBox(boxEdicionModulo, boxCreacionModulo, boxAsignacionModulo);
			switchButton(btnEditarModulo, btnAsignarModulo, btnCrearModulo);
		} else {
			editarModulo();
		}
	}

	/**
	 * Alterna la visivilidad del menú de búsqueda de Resultados de Apredizaje.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchBuscarRA() {
		if (!boxBuscarRA.isVisible()) {
			switchBox(boxBuscarRA, boxEdicionRA, boxCreacionRA);
			switchButton(btnBuscarRA, btnEditarRA, btnNuevoRA);
		} else {
			buscarRA();
		}
	}

	/**
	 * Alterna la visivilidad del menú de creación de Resultados de Apredizaje.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchNuevoRA() {
		if (!boxCreacionRA.isVisible()) {
			switchBox(boxCreacionRA, boxEdicionRA, boxBuscarRA);
			switchButton(btnNuevoRA, btnEditarRA, btnBuscarRA);
		} else {
			guardarRA();
		}
	}

	/**
	 * Alterna la visivilidad del menú de edición de Resultados de Apredizaje.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchEditarRA() {
		if (!boxEdicionRA.isVisible()) {
			switchBox(boxEdicionRA, boxBuscarRA, boxCreacionRA);
			switchButton(btnEditarRA, btnBuscarRA, btnNuevoRA);
		} else {
			editarRA();
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
	 * Filtra la lista de profesores según el nombre introducido en el buscador.
	 * 
	 * Si el campo de búsqueda está vacío, se recargan todos los profesores. Si no,
	 * se muestran solo los que coincidan con el nombre.
	 */
	@FXML
	private void buscarProfe() {
		String texto = txtBuscadorProfe.getText().trim();
		if (texto.isEmpty()) {
			cargarProfes();
		} else {
			tablaProfes.setItems(FXCollections.observableArrayList(prService.findByNombre(texto)));
		}
	}

	/**
	 * Filtra los Resultados de Aprendizaje según el código introducido en el
	 * buscador.
	 * 
	 * Si el campo de búsqueda está vacío, se cargan todos los RAs. Si no, se
	 * muestran solo los que coincidan con el código.
	 */
	@FXML
	private void buscarRA() {
		String texto = txtBuscadorRA.getText().trim();
		if (texto.isEmpty()) {
			cargarRAs();
		} else {
			tablaRAs.setItems(FXCollections.observableArrayList(raService.findByNombreParcial(texto)));
		}
	}

	/**
	 * Valida y guarda un nuevo profesor.
	 * 
	 * Si la validación es correcta, se crea una nueva entidad Profesor, se guarda
	 * en la base de datos, se actualiza la lista de profesores y se limpia el
	 * formulario, además de notificar la creación por medio de un evento.
	 */
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

		evPublisher.publishEvent(new NewProfesorEvent(profe));
	}

	/**
	 * Valida y guarda un nuevo resultado de aprendizaje.
	 * 
	 * Si la validación es correcta, se crea una nueva entidad ResultadoAprendizaje,
	 * se guarda en la base de datos, se actualiza la lista de resultados de
	 * aprendizaje y se limpia el formulario, además de notificar la creación por
	 * medio de un evento.
	 */
	@FXML
	private void guardarRA() {

		if (validarRA(txtNombreRA, lblNombreRAError, txtDescRA, cbModuloRA, false))
			return;

		ResultadoAprendizaje ra = new ResultadoAprendizaje();

		ra.setCodigo(txtNombreRA.getText());
		ra.setDescripcion(txtDescRA.getText());
		ra.setModulo(cbModuloRA.getValue());

		raService.save(ra);

		switchBuscarRA();
		cargarRAs();
		limpiarFormRA(txtNombreRA, txtDescRA, cbModuloRA);
	}

	/**
	 * Valida y edita un resultado de aprendizaje.
	 * 
	 * Si la validación es correcta, actualiza la entidad ResultadoAprendizaje
	 * seleccionada en la base de datos, se actualiza la lista de RAs y se limpia el
	 * formulario, además de notificar la actualización por medio de un evento.
	 */
	private void editarRA() {
		ResultadoAprendizaje ra = cbRAEdit.getValue();
		if (ra == null)
			return;

		if (validarRA(txtNombreRAEdit, lblNombreRAEditError, txtDescRAEdit, cbModuloRAEdit, true))
			return;

		ra.setCodigo(txtNombreRAEdit.getText());
		ra.setDescripcion(txtDescRAEdit.getText());
		ra.setModulo(cbModuloRAEdit.getValue());

		raService.update(ra);

		switchBuscarRA();
		cargarRAs();
		limpiarFormRA(txtNombreRAEdit, txtDescRAEdit, cbModuloRAEdit);
	}

	/**
	 * Carga los datos de un profesor en el formulario de edición.
	 *
	 * @param profe profesor cuyos datos se cargarán en el formulario
	 */
	private void cargarProfeEditar(Profesor profe) {
		if (profe != null) {
			txtEditNombreProfe.setText(profe.getNombre());
			txtEditEmailProfe.setText(profe.getEmail());
			txtEditUsernameProfe.setText(profe.getUser());
		}
	}

	/**
	 * Carga los datos de un resultado de aprendizaje en el formulario de edición.
	 * 
	 * @param ra resultado de aprendizaje cuyos datos se cargarán en el formulario
	 */
	private void cargarRAEditar(ResultadoAprendizaje ra) {
		if (ra != null) {
			txtNombreRAEdit.setText(ra.getCodigo());
			txtDescRAEdit.setText(ra.getDescripcion());
			cbModuloRAEdit.setValue(ra.getModulo());
		}
	}

	/**
	 * Valida y edita un profesor.
	 * 
	 * Si la validación es correcta, actualiza la entidad Profesor seleccionada en
	 * la base de datos, se actualiza la lista de profesores y se limpia el
	 * formulario, además de notificar la actualización por medio de un evento.
	 */
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
		if (!txtEditPasswordProfe.getText().isBlank())
			profe.setPassword(Hasher.md5(txtEditPasswordProfe.getText()));

		prService.update(profe);

		switchBuscarProfe();
		cargarProfes();
		limpiarForm(txtEditNombreProfe, txtEditEmailProfe, txtEditUsernameProfe, txtEditPasswordProfe);

		evPublisher.publishEvent(new NewProfesorEvent(profe));
	}

	/**
	 * Valida los datos de un profesor antes de su creación o edición.
	 * 
	 * Valida: - El nombre no está vacío - El nombre solo está compuesto de letras y
	 * espacios - El email no está vacío - El email tiene un formato válido y no
	 * duplicado - El nombre de usuario no está vacío - El nombre de usuario está
	 * compuesto de letras minúscuclas sin espacios y no está duplicado - La
	 * contraseña no está vacía y no contiene espacios.
	 * 
	 * Si algún campo da error, lo hará saber actualizando labels y estilos de la
	 * interfáz en los campos correspondientes.
	 * 
	 * @param tfNombre
	 * @param lblNombre
	 * @param tfEmail
	 * @param lblEmail
	 * @param tfUser
	 * @param lblUser
	 * @param tfPassword
	 * @param lblPassword
	 * @param edit        indica si la validación es para edición (true) o creación
	 *                    (false)
	 * @return true si hay errores de validación, false si los datos son válidos
	 */
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
		boolean password = false;
		if (!edit) {
			password = txtPassword.isEmpty();
		}
		tfPassword.pseudoClassStateChanged(EMPTY, password);
		if (!txtPassword.isEmpty()) {
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

	/**
	 * Valida los datos de un Resultado de Aprendizaje antes de su creación o
	 * edición.
	 * 
	 * Validaciones: - El código no está vacío - No existe otro RA con el mismo
	 * código - La descripción no está vacía - Se ha seleccionado el módulo al que
	 * pertenece el RA
	 * 
	 * Si algún campo da error, lo hará saber actualizando labels y estilos de la
	 * interfáz en los campos correspondientes.
	 * 
	 * @param tfCodigo
	 * @param lblCodigo
	 * @param tfDesc
	 * @param cbModulo
	 * @param edit      indica si la validación es para edición (true) o creación
	 *                  (false)
	 * @return true si hay errores de validación, false si los datos son válidos
	 */
	private boolean validarRA(TextField tfCodigo, Label lblCodigo, TextArea tfDesc, ComboBox<Modulo> cbModulo,
			boolean edit) {

		ResultadoAprendizaje raCodigo = raService.findByCodigo(tfCodigo.getText());
		ResultadoAprendizaje raEdit = edit ? cbRAEdit.getValue() : null;

		boolean codigo = tfCodigo.getText().isEmpty();
		tfCodigo.pseudoClassStateChanged(EMPTY, codigo);

		if (!codigo) {
			if (raCodigo != null && (raEdit == null || !raCodigo.getId().equals(raEdit.getId()))) {
				codigo = true;
				lblCodigo.setText("Este código ya pertenece a otro RA.");
			}

			lblCodigo.setManaged(codigo);
			lblCodigo.setVisible(codigo);

		} else {
			lblCodigo.setManaged(false);
			lblCodigo.setVisible(false);
		}

		boolean desc = tfDesc.getText().isEmpty();
		tfDesc.pseudoClassStateChanged(EMPTY, desc);

		boolean modulo = cbModulo.getValue() == null;
		cbModulo.pseudoClassStateChanged(EMPTY, modulo);

		return codigo || desc || modulo;
	}

	/**
	 * Asigna un profesor como coordinador del curso seleccionado.
	 * 
	 * Valida que exista un curso seleccionado en la tabla y un profesor
	 * seleccionado en el ComboBox. Si alguno es nulo, la operación no se ejecuta.
	 * 
	 * En caso válido, actualiza el coordinador del curso, guarda el cambio y
	 * recarga la lista de cursos.
	 */
	@FXML
	private void asignarCoordinador() {
		Curso cursoSeleccionado = tablaCursos.getSelectionModel().getSelectedItem();
		Profesor coordinador = cbCoordinador.getValue();

		if (cursoSeleccionado == null || coordinador == null)
			return;

		cursoSeleccionado.setCoordinador(coordinador);
		cuService.update(cursoSeleccionado);

		cargarCursos();
	}

	/**
	 * Valida y guarda un nuevo módulo en el sistema.
	 * 
	 * Validaciones: - El nombre no puede estar vacío - Debe tener más de 2
	 * caracteres - No puede existir otro módulo con el mismo nombre
	 * 
	 * Si la validación falla, se muestran mensajes de error en la interfaz. Si no,
	 * se guarda el módulo en la base de datos y se recarga la lista de módulos.
	 */
	private void guardarModulo() {

		String txtNombre = txtNombreModulo.getText();
		Curso curso = cbCurso.getValue();
		Profesor profe = cbProfesorModulo.getValue();

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

		cbCurso.pseudoClassStateChanged(EMPTY, curso == null);
		cbProfesorModulo.pseudoClassStateChanged(EMPTY, profe == null);

		if (nombre || curso == null || profe == null) {
			return;
		}

		Modulo modulo = new Modulo();
		modulo.setNombre(txtNombre);
		Modulo mod = moService.save(modulo);
		
		ModuloCurso mc = new ModuloCurso();
		
		mc.setModulo(mod);
		mc.setCurso(curso);
		mc.setProfesor(profe);
		
		mcService.save(mc);
		
		txtNombreModulo.clear();
		cargarModulos();
	}

	/**
	 * Asigna un módulo a un curso junto con su profesor.
	 * 
	 * Valida que se haya seleccionado un módulo, un curso y un profesor. Si la
	 * asignación ya existe, se actualiza; si no, se crea una nueva en la base de
	 * datos.
	 */
	private void asignarModulo() {

		Modulo modulo = tablaModulos.getSelectionModel().getSelectedItem();
		Curso curso = cbCursoEdit.getValue();
		Profesor profe = cbProfesorModuloEdit.getValue();

		cbCursoEdit.pseudoClassStateChanged(EMPTY, curso == null);
		cbProfesorModuloEdit.pseudoClassStateChanged(EMPTY, profe == null);

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

	/**
	 * Edita la información de un módulo existente.
	 * 
	 * Validaciones:- El nombre no puede estar vacío - Debe tener más de 2
	 * caracteres - No puede existir otro módulo con el mismo nombre
	 * 
	 * Si la validación es correcta, se actualiza el módulo en la base de datos y se
	 * recarga la lista de módulos.
	 */
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

	/**
	 * Limpia la información introducida en los campos del formulario de creación o
	 * edición de Profesores.
	 * 
	 * @param tfNombre
	 * @param tfEmail
	 * @param tfUser
	 * @param tfPassword
	 */
	private void limpiarForm(TextField tfNombre, TextField tfEmail, TextField tfUser, PasswordField tfPassword) {
		tfNombre.clear();
		tfEmail.clear();
		tfUser.clear();
		tfPassword.clear();
	}

	/**
	 * Limpia la información introducida en los campos del formulario de creación o
	 * edición de Resultados de Aprendizaje.
	 * 
	 * @param tfCodigo
	 * @param tfDesc
	 * @param cbModulo
	 */
	private void limpiarFormRA(TextField tfCodigo, TextArea tfDesc, ComboBox<Modulo> cbModulo) {
		tfCodigo.clear();
		tfDesc.clear();
		cbModulo.getSelectionModel().clearSelection();
		cbModulo.setValue(null);
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
