package com.simao.tarea3AD2024base.controller;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.EstadoFE;
import com.simao.tarea3AD2024base.modelo.EstadoRA;
import com.simao.tarea3AD2024base.modelo.EvaluacionRa;
import com.simao.tarea3AD2024base.modelo.Falta;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Periodo;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.modelo.ResultadoAprendizaje;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.services.AlumnoService;
import com.simao.tarea3AD2024base.services.FaltaService;
import com.simao.tarea3AD2024base.services.FormacionEmpresaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.services.ResultadoAprendizajeService;
import com.simao.tarea3AD2024base.services.Session;
import com.simao.tarea3AD2024base.services.TutorService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Clase GestionFormacionController.java
 * 
 * Gestiona las interacciones con la interfaz de gestión de empresas.
 */
@Controller
public class GestionFormacionController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private FormacionEmpresaService feService;

	@Autowired
	private TutorService tuService;

	@Autowired
	private AlumnoService alService;

	@Autowired
	private ProfesorService prService;

	@Autowired
	private ResultadoAprendizajeService raService;

	@Autowired
	private FaltaService faService;

	@Autowired
	private Session session;

	@EventListener
	public void onNewAlumno(NewAlumnoEvent event) {
		cargarAlumnos();
	}

	@EventListener
	public void onNewProfesor(NewProfesorEvent event) {
		cargarProfes();
	}

	@EventListener
	public void onNewTutor(NewTutorEvent event) {
		cargarTutores();
	}

	@FXML
	private HBox boxNuevo;

	@FXML
	private HBox boxReasignar;

	@FXML
	private HBox boxEvaluar;

	@FXML
	private ComboBox<FormacionEmpresa> cbEditarFE;

	@FXML
	private Label lblAlumnoErrorEdit;

	@FXML
	private ComboBox<Tutor> cbTutorEmpresaEdit;

	@FXML
	private ComboBox<Profesor> cbTutorCentroEdit;

	@FXML
	private ComboBox<Periodo> cbPeriodoEdit;

	@FXML
	private DatePicker dpIniEdit;

	@FXML
	private DatePicker dpFinEdit;

	@FXML
	private Label lblDateErrorEdit;

	@FXML
	private CheckBox checkNewFE;

	@FXML
	private ComboBox<Alumno> cbAlumno;

	@FXML
	private Label lblAlumnoError;

	@FXML
	private ComboBox<Tutor> cbTutorEmpresa;

	@FXML
	private ComboBox<Profesor> cbTutorCentro;

	@FXML
	private ComboBox<Periodo> cbPeriodo;

	@FXML
	private DatePicker dpIni;

	@FXML
	private DatePicker dpFin;

	@FXML
	private Label lblDateError;

	@FXML
	private ComboBox<FormacionEmpresa> cbEvaluarFE;

	private List<EvaluacionRow> evaluacionRows = new ArrayList<>();

	@FXML
	private VBox boxEvaluaciones;

	@FXML
	private HBox boxFaltas;

	@FXML
	private DatePicker dpFechaFalta;

	@FXML
	private CheckBox checkJustificada;

	@FXML
	private ComboBox<FormacionEmpresa> cbFEFalta;

	@FXML
	private Button btnNuevo;

	@FXML
	private Button btnReasignar;

	@FXML
	private Button btnEvaluar;

	@FXML
	private Button btnFaltas;

	@FXML
	private TextArea txtDescripcionFalta;

	@FXML
	private TableView<FormacionEmpresa> tablaFormaciones;

	@FXML
	private TableColumn<FormacionEmpresa, String> colAlumno;

	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorCentro;

	@FXML
	private TableColumn<FormacionEmpresa, String> colEmpresa;

	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorEmpresa;

	@FXML
	private TableColumn<FormacionEmpresa, String> colFechaIni;

	@FXML
	private TableColumn<FormacionEmpresa, String> colFechaFin;

	@FXML
	private TableColumn<FormacionEmpresa, String> colPeriodo;

	@FXML
	private TableColumn<FormacionEmpresa, String> colEstado;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (session.getPerfil() == Perfil.TUTOR || session.getPerfil() == Perfil.ALUMNADO) {
			btnNuevo.setVisible(false);
			btnNuevo.setManaged(false);
			btnReasignar.setVisible(false);
			btnReasignar.setManaged(false);
			if (session.getPerfil() == Perfil.TUTOR)
				switchEvaluar();
			else {
				btnFaltas.setVisible(false);
				btnFaltas.setManaged(false);
				btnEvaluar.setVisible(false);
				btnEvaluar.setManaged(false);
				boxNuevo.setVisible(false);
				boxNuevo.setManaged(false);
			}
		}

		cbEditarFE.getSelectionModel().selectedItemProperty().addListener((_, _, fe) -> {
			cargarEditar(fe);
		});

		cbEvaluarFE.getSelectionModel().selectedItemProperty().addListener((_, _, fe) -> {

			if (fe == null) {
				boxEvaluaciones.getChildren().clear();
				return;
			}

			cargarEvaluaciones(fe.getId());
		});

		cargarFormaciones();
		cargarAlumnos();
		cargarTutores();
		cargarProfes();
		cbPeriodo.getItems().addAll(Periodo.ORDINARIO, Periodo.EXTRAORIDINARIO);
		cbPeriodoEdit.getItems().addAll(Periodo.ORDINARIO, Periodo.EXTRAORIDINARIO);
	}

	/**
	 * Carga todas las formaciones en empresa desde la base de datos y las muestra
	 * en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes.
	 */
	private void cargarFormaciones() {
		List<FormacionEmpresa> fes = new ArrayList<>();

		if (session.getPerfil() == Perfil.TUTOR)
			fes = feService.findByTutor(tuService.find(session.getUserId()));
		else if (session.getPerfil() == Perfil.ALUMNADO)
			fes = feService.findByAlumno(alService.find(session.getUserId()));
		else
			fes = feService.findAll();

		fes.removeIf(fe -> fe.getEstado() == EstadoFE.CANCELADA);

		ObservableList<FormacionEmpresa> datos = FXCollections.observableArrayList(fes);

		colAlumno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlumno().getNombre()));
		colTutorCentro
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorCentro().getNombre()));
		colEmpresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmpresa().getNombre()));
		colTutorEmpresa
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorEmpresa().getNombre()));
		colFechaIni.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaIni().toString()));
		colFechaFin.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFechaFin().toString()));
		colPeriodo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeriodo().toString()));
		colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));

		if (!fes.isEmpty()) {
			tablaFormaciones.setItems(datos);
			cbEditarFE.setItems(datos);
			cbEvaluarFE.setItems(datos);
			cbFEFalta.setItems(datos);
		}
	}

	/**
	 * Obtiene todos los alumnos desde la base de datos y los carga en el ComboBox
	 * de Alumnos.
	 * 
	 */
	private void cargarAlumnos() {
		List<Alumno> alumnos = alService.findAll();
		ObservableList<Alumno> datos = FXCollections.observableArrayList(alumnos);

		if (!alumnos.isEmpty()) {
			cbAlumno.setItems(datos);
		}
	}

	/**
	 * Obtiene todos los tutores desde la base de datos y los carga en los ComboBox
	 * relacionados.
	 * 
	 */
	private void cargarTutores() {
		List<Tutor> tutores = tuService.findAll();
		ObservableList<Tutor> datos = FXCollections.observableArrayList(tutores);

		if (!tutores.isEmpty()) {
			cbTutorEmpresa.setItems(datos);
			cbTutorEmpresaEdit.setItems(datos);
		}
	}

	/**
	 * Obtiene todos los profesores desde la base de datos y los carga en los
	 * ComboBox relacionados.
	 * 
	 */
	private void cargarProfes() {
		List<Profesor> profes = prService.findAll();
		ObservableList<Profesor> datos = FXCollections.observableArrayList(profes);

		if (!profes.isEmpty()) {
			cbTutorCentro.setItems(datos);
			cbTutorCentroEdit.setItems(datos);
		}
	}

	/**
	 * Alterna la visivilidad del menú de creación de FEs.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchNuevo() {
		if (!boxNuevo.isVisible()) {
			switchBox(boxNuevo, boxEvaluar, boxReasignar, boxFaltas);
			switchButton(btnNuevo, btnEvaluar, btnReasignar, btnFaltas);
		} else {
			guardar();
		}
	}

	/**
	 * Alterna la visivilidad del menú de reasignación de FEs.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchReasignar() {
		if (!boxReasignar.isVisible()) {
			switchBox(boxReasignar, boxEvaluar, boxNuevo, boxFaltas);
			switchButton(btnReasignar, btnEvaluar, btnNuevo, btnFaltas);
		} else {
			editar();
		}
	}

	/**
	 * Alterna la visivilidad del menú de evaluacion de FEs.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchEvaluar() {
		if (!boxEvaluar.isVisible()) {
			switchBox(boxEvaluar, boxReasignar, boxNuevo, boxFaltas);
			switchButton(btnEvaluar, btnReasignar, btnNuevo, btnFaltas);
		} else {
			evaluar();
		}
	}

	/**
	 * Alterna la visivilidad del menú de registro de faltas.
	 * 
	 * Si el menú no está visible, lo muestra. Si ya está visible, ejecuta la
	 * operación.
	 */
	@FXML
	private void switchFaltas() {
		if (!boxFaltas.isVisible()) {
			switchBox(boxFaltas, boxReasignar, boxNuevo, boxEvaluar);
			switchButton(btnFaltas, btnReasignar, btnNuevo, btnEvaluar);
		} else {
			registrarFalta();
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
	 * Valida y guarda una nueva FE.
	 * 
	 * Si la validación es correcta, se crea una nueva FormacionEmpresa, se guarda
	 * en la base de datos, se actualiza la lista de FEs y se cambia al menú de
	 * búsqueda.
	 */
	@FXML
	private void guardar() {
		if (validar(cbAlumno, lblAlumnoError, cbTutorCentro, cbTutorEmpresa, cbPeriodo, dpIni, dpFin, lblDateError,
				false))
			return;

		FormacionEmpresa fe = new FormacionEmpresa();

		fe.setAlumno(cbAlumno.getValue());
		fe.setTutorCentro(cbTutorCentro.getValue());
		fe.setEmpresa(cbTutorEmpresa.getValue().getEmpresa());
		fe.setTutorEmpresa(cbTutorEmpresa.getValue());
		fe.setPeriodo(cbPeriodo.getValue());
		fe.setFechaIni(dpIni.getValue());
		fe.setFechaFin(dpFin.getValue());
		if (dpIni.getValue().isAfter(LocalDate.now()))
			fe.setEstado(EstadoFE.ACTIVA);
		else if (dpFin.getValue().isAfter(LocalDate.now()))
			fe.setEstado(EstadoFE.FINALIZADA);

		List<ResultadoAprendizaje> ras = raService.findByCurso(cbAlumno.getValue().getCurso());

		for (ResultadoAprendizaje ra : ras) {

			EvaluacionRa ev = new EvaluacionRa();
			System.out.println("Llega");
			System.out.println(ra.getCodigo());

			ev.setResultadoAprendizaje(ra);
			fe.addEvaluacion(ev);
		}

		feService.save(fe);

		cargarFormaciones();
		limpiarForm(cbAlumno, cbTutorCentro, cbTutorEmpresa, cbPeriodo, dpIni, dpFin);
	}

	/**
	 * Carga los datos de una FE en el formulario de edición.
	 *
	 * @param fe FormacionEmpresa cuyos datos se cargarán en el formulario.
	 */
	private void cargarEditar(FormacionEmpresa fe) {
		if (fe != null) {
			cbTutorEmpresaEdit.setValue(fe.getTutorEmpresa());
			cbTutorCentroEdit.setValue(fe.getTutorCentro());
			cbPeriodoEdit.setValue(fe.getPeriodo());
			dpIniEdit.setValue(fe.getFechaIni());
			dpFinEdit.setValue(fe.getFechaFin());

		}
	}

	/**
	 * Valida y edita una FE.
	 * 
	 * Si la validación es correcta, actualiza la entidad FormacionEmpresa
	 * seleccionada en la base de datos, se actualiza la lista de empresas y se
	 * cambia al menú de búsqueda.
	 */
	private void editar() {
		FormacionEmpresa fe = cbEditarFE.getValue();
		if (fe == null)
			return;

		if (validar(null, lblAlumnoErrorEdit, cbTutorCentroEdit, cbTutorEmpresaEdit, cbPeriodoEdit, dpIniEdit,
				dpFinEdit, lblDateErrorEdit, true))
			return;

		if (checkNewFE.isSelected()) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Crear nueva FE");
			alert.setHeaderText("Se creará una nueva Formación en Empresa");
			alert.setContentText(
					"Si selecciona \"Nueva FE\", la FE actual será marcada como CANCELADA y se creará una nueva FE para el alumno seleccionado con los datos introducidos.\n¿Desea continuar?");
			alert.setResizable(true);

			Optional<ButtonType> result = alert.showAndWait();

			if (result.isEmpty() || result.get() != ButtonType.OK) {
				return;
			}

			fe.setEstado(EstadoFE.CANCELADA);
			feService.save(fe);

			FormacionEmpresa nfe = new FormacionEmpresa();

			nfe.setAlumno(fe.getAlumno());
			nfe.setTutorCentro(cbTutorCentroEdit.getValue());
			nfe.setEmpresa(cbTutorEmpresaEdit.getValue().getEmpresa());
			nfe.setTutorEmpresa(cbTutorEmpresaEdit.getValue());
			nfe.setPeriodo(cbPeriodoEdit.getValue());
			nfe.setFechaIni(dpIniEdit.getValue());
			nfe.setFechaFin(dpFinEdit.getValue());
			if (dpIniEdit.getValue().isAfter(LocalDate.now())) {
				nfe.setEstado(EstadoFE.PENDIENTE);
			} else if (dpFinEdit.getValue().isBefore(LocalDate.now())) {
				nfe.setEstado(EstadoFE.FINALIZADA);
			} else {
				nfe.setEstado(EstadoFE.ACTIVA);
			}

			checkNewFE.setSelected(false);
			feService.save(nfe);

		} else {

			fe.setTutorCentro(cbTutorCentroEdit.getValue());
			fe.setEmpresa(cbTutorEmpresaEdit.getValue().getEmpresa());
			fe.setTutorEmpresa(cbTutorEmpresaEdit.getValue());
			fe.setPeriodo(cbPeriodoEdit.getValue());
			fe.setFechaIni(dpIniEdit.getValue());
			fe.setFechaFin(dpFinEdit.getValue());
			if (dpIniEdit.getValue().isAfter(LocalDate.now())) {
				fe.setEstado(EstadoFE.PENDIENTE);
			} else if (dpFinEdit.getValue().isBefore(LocalDate.now())) {
				fe.setEstado(EstadoFE.FINALIZADA);
			} else {
				fe.setEstado(EstadoFE.ACTIVA);
			}

			feService.save(fe);
		}

		cargarFormaciones();
		limpiarForm(cbAlumno, cbTutorCentroEdit, cbTutorEmpresaEdit, cbPeriodoEdit, dpIniEdit, dpFinEdit);

	}

	/**
	 * Valida los datos de una FE antes de su creación o edición.
	 * 
	 * Validaciones: - El alumno no puede estar asociado a una FE previamente -
	 * Ningún campo puede estar vacío - La fecha final no puede ser anterior a la
	 * fecha inicial
	 * 
	 * @param cbAlumno
	 * @param lblAlumnoError
	 * @param cbProfe
	 * @param cbTutor
	 * @param cbPeriodo
	 * @param dpIni
	 * @param dpFin
	 * @param lblDateError
	 * @param edit
	 * @return
	 */
	private boolean validar(ComboBox<Alumno> cbAlumno, Label lblAlumnoError, ComboBox<Profesor> cbProfe,
			ComboBox<Tutor> cbTutor, ComboBox<Periodo> cbPeriodo, DatePicker dpIni, DatePicker dpFin,
			Label lblDateError, boolean edit) {

		boolean alumno;
		if (!edit) {
			alumno = cbAlumno.getValue() == null;
			cbAlumno.pseudoClassStateChanged(EMPTY, alumno);

			if (!alumno) {
				if (!feService.findByAlumno(cbAlumno.getValue()).isEmpty()) {
					alumno = true;
					lblAlumnoError.setText("Este alumno ya está asociado a una FE.");
				}

				lblAlumnoError.setManaged(alumno);
				lblAlumnoError.setVisible(alumno);

			} else {
				lblAlumnoError.setManaged(false);
				lblAlumnoError.setVisible(false);
			}

		} else
			alumno = false;

		boolean profe = cbProfe.getValue() == null;
		cbProfe.pseudoClassStateChanged(EMPTY, profe);

		boolean tutor = cbTutor.getValue() == null;
		cbTutor.pseudoClassStateChanged(EMPTY, tutor);

		boolean periodo = cbPeriodo.getValue() == null;
		cbPeriodo.pseudoClassStateChanged(EMPTY, periodo);

		boolean fechaIni = dpIni.getValue() == null;
		dpIni.pseudoClassStateChanged(EMPTY, fechaIni);

		boolean fechaFin = dpFin.getValue() == null;
		dpFin.pseudoClassStateChanged(EMPTY, fechaFin);

		boolean fechas = fechaIni || fechaFin;
		if (!fechas) {
			if (dpFin.getValue().isBefore(dpIni.getValue())) {
				fechas = true;
				lblDateError.setText("La fecha final no puede ser anterior a la fecha inicial.");
			} else if (dpIni.getValue().plusMonths(4).isBefore(dpFin.getValue())) {
				fechas = true;
				lblDateError.setText("La duración de la FE no debe ser superior a 4 meses.");
			}

			lblDateError.setManaged(fechas);
			lblDateError.setVisible(fechas);
		} else {
			lblDateError.setManaged(false);
			lblDateError.setVisible(false);
		}

		return alumno || profe || tutor || periodo || fechas;
	}

	/**
	 * Limpia la información introducida en los campos del formulario de creación o
	 * edición de la FE.
	 * 
	 * @param cbAlumno
	 * @param cbProfe
	 * @param cbTutor
	 * @param cbPeriodo
	 * @param dpIni
	 * @param dpFin
	 */
	private void limpiarForm(ComboBox<Alumno> cbAlumno, ComboBox<Profesor> cbProfe, ComboBox<Tutor> cbTutor,
			ComboBox<Periodo> cbPeriodo, DatePicker dpIni, DatePicker dpFin) {
		cbAlumno.getSelectionModel().clearSelection();
		cbAlumno.setValue(null);
		cbProfe.getSelectionModel().clearSelection();
		cbProfe.setValue(null);
		cbTutor.getSelectionModel().clearSelection();
		cbTutor.setValue(null);
		cbPeriodo.getSelectionModel().clearSelection();
		cbPeriodo.setValue(null);
		dpIni.setValue(null);
		dpFin.setValue(null);
	}

	class EvaluacionRow {

		Long idEvaluacion;
		ToggleGroup group;

		EvaluacionRow(Long idEvaluacion, ToggleGroup group) {
			this.idEvaluacion = idEvaluacion;
			this.group = group;
		}
	}

	/**
	 * Carga las evaluaciones de la FE desde la base de datos.
	 * 
	 * @param feId
	 */
	private void cargarEvaluaciones(Long feId) {
		FormacionEmpresa fe = feService.findCompleta(feId);

		if (fe == null)
			return;

		boxEvaluaciones.getChildren().clear();
		evaluacionRows.clear();

		for (EvaluacionRa ev : fe.getEvaluaciones()) {

			HBox fila = new HBox(15);

			Label lblRA = new Label(ev.getResultadoAprendizaje().getCodigo());

			ToggleGroup tg = new ToggleGroup();

			RadioButton rbSuperado = new RadioButton("Superado");
			rbSuperado.setToggleGroup(tg);
			RadioButton rbNoSuperado = new RadioButton("No superado");
			rbNoSuperado.setToggleGroup(tg);
			RadioButton rbNoAplica = new RadioButton("No aplica");
			rbNoAplica.setToggleGroup(tg);

			switch (ev.getEstado()) {
			case SUPERADO:
				rbSuperado.setSelected(true);
				break;

			case NO_SUPERADO:
				rbNoSuperado.setSelected(true);
				break;

			case NO_APLICA:
				rbNoAplica.setSelected(true);
				break;

			default:
				break;
			}

			fila.getChildren().addAll(lblRA, rbSuperado, rbNoSuperado, rbNoAplica);

			boxEvaluaciones.getChildren().addAll(fila);
			evaluacionRows.add(new EvaluacionRow(ev.getId(), tg));
		}
	}

	/**
	 * Guarda las evaluaciones de los RAs de la FE en la base de datos.
	 */
	private void evaluar() {
		FormacionEmpresa feSelected = feService.findCompleta(cbEvaluarFE.getValue().getId());

		if (feSelected == null) {
			return;
		}

		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Evaluar FE");
		alert.setContentText(
				"Al evaluar una FE, será marcada como FINALIZADA y no se podrán modificar sus datos. ¿Desea continuar?");
		alert.setResizable(true);

		Optional<ButtonType> result = alert.showAndWait();

		if (result.isEmpty() || result.get() != ButtonType.OK) {
			return;
		}

		FormacionEmpresa fe = feService.findCompleta(feSelected.getId());

		for (EvaluacionRow row : evaluacionRows) {

			EvaluacionRa ev = fe.getEvaluaciones().stream().filter(e -> e.getId().equals(row.idEvaluacion)).findFirst()
					.orElse(null);

			if (ev == null)
				continue;

			RadioButton selected = (RadioButton) row.group.getSelectedToggle();

			if (selected == null)
				continue;

			switch (selected.getText()) {

			case "Superado" -> ev.setEstado(EstadoRA.SUPERADO);

			case "No superado" -> ev.setEstado(EstadoRA.NO_SUPERADO);

			case "No aplica" -> ev.setEstado(EstadoRA.NO_APLICA);
			}
		}

		fe.setEstado(EstadoFE.FINALIZADA);

		feService.save(fe);

		cargarFormaciones();
	}

	/*
	 * Exporta todos los datos de una FE seleccionada a un PDF.
	 */
	@FXML
	private void exportar() throws Exception {

		FormacionEmpresa feSelect = tablaFormaciones.getSelectionModel().getSelectedItem();

		if (feSelect == null)
			return;

		FormacionEmpresa fe = feService.findCompleta(feSelect.getId());

		Path carpeta = Paths.get(System.getProperty("user.dir"), "ficheros");
		Files.createDirectories(carpeta);

		Path archivo = carpeta.resolve("formacion_" + fe.getId() + ".pdf");

		Document doc = new Document();
		PdfWriter.getInstance(doc, new FileOutputStream(archivo.toFile()));

		doc.open();

		Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD);

		doc.add(new Paragraph("FORMACIÓN EN EMPRESA", titleFont));
		doc.add(new Paragraph(" "));

		doc.add(new Paragraph("Alumno: " + fe.getAlumno().getNombre()));
		doc.add(new Paragraph("Empresa: " + fe.getEmpresa().getNombre()));
		doc.add(new Paragraph("Tutor Centro: " + fe.getTutorCentro().getNombre()));
		doc.add(new Paragraph("Tutor Empresa: " + fe.getTutorEmpresa().getNombre()));

		doc.add(new Paragraph("Fecha Inicio: " + fe.getFechaIni()));
		doc.add(new Paragraph("Fecha Fin: " + fe.getFechaFin()));
		doc.add(new Paragraph("Estado: " + fe.getEstado()));

		doc.add(new Paragraph(" "));
		doc.add(new Paragraph("EVALUACIONES RA", titleFont));
		doc.add(new Paragraph(" "));

		for (EvaluacionRa ev : fe.getEvaluaciones()) {

			String estado = ev.getEstado() != null ? ev.getEstado().toString() : "SIN EVALUAR";

			doc.add(new Paragraph(ev.getResultadoAprendizaje().getCodigo() + " - "
					+ ev.getResultadoAprendizaje().getDescripcion() + " : " + estado));
		}

		doc.close();
	}

	/**
	 * Valida y registra una falta.
	 * 
	 * Si la validación es correcta, se crea una nueva Falta y se guarda en la base
	 * de datos.
	 */
	private void registrarFalta() {
		if (validarFalta()) {
			return;
		}

		Falta falta = new Falta();
		falta.setFormacion(cbFEFalta.getValue());
		falta.setFecha(dpFechaFalta.getValue());
		falta.setDescripcion(txtDescripcionFalta.getText());
		falta.setJustificada(checkJustificada.isSelected());

		faService.save(falta);
		clearFaltaForm();
	}

	/**
	 * Valida los datos de una Falta antes de su creación.
	 * 
	 * Validaciones: Ningún campo puede estar vacío
	 */
	private boolean validarFalta() {

		boolean fe = cbFEFalta.getValue() == null;
		cbFEFalta.pseudoClassStateChanged(EMPTY, fe);

		boolean fecha = dpFechaFalta.getValue() == null;
		dpFechaFalta.pseudoClassStateChanged(EMPTY, fecha);

		boolean desc = txtDescripcionFalta.getText() == null;
		txtDescripcionFalta.pseudoClassStateChanged(EMPTY, desc);

		return fe || fecha || desc;
	}

	/**
	 * Limpia la información introducida en los campos del formulario de registro de
	 * Faltas.
	 */
	private void clearFaltaForm() {
		cbFEFalta.setValue(null);
		dpFechaFalta.setValue(null);
		txtDescripcionFalta.clear();
		checkJustificada.setSelected(false);
	}
}
