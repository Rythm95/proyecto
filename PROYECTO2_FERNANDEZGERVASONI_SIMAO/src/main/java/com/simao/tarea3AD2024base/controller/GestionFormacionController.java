package com.simao.tarea3AD2024base.controller;

import java.io.FileOutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.modelo.Periodo;
import com.simao.tarea3AD2024base.modelo.Profesor;
import com.simao.tarea3AD2024base.modelo.ResultadoAprendizaje;
import com.simao.tarea3AD2024base.modelo.Tutor;
import com.simao.tarea3AD2024base.services.AlumnoService;
import com.simao.tarea3AD2024base.services.FormacionEmpresaService;
import com.simao.tarea3AD2024base.services.ProfesorService;
import com.simao.tarea3AD2024base.services.ResultadoAprendizajeService;
import com.simao.tarea3AD2024base.services.Session;
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
			}
		}

		cbEditarFE.getSelectionModel().selectedItemProperty().addListener((_, _, fe) -> {
			cargarEditar(fe);
		});

		cbEvaluarFE.getSelectionModel().selectedItemProperty().addListener((_, _, fe) -> {
			cargarEvaluaciones(fe.getId());
		});

		cargarFormaciones();
		cargarAlumnos();
		cargarTutores();
		cargarProfes();
		cbPeriodo.getItems().addAll(Periodo.ORDINARIO, Periodo.EXTRAORIDINARIO);
		cbPeriodoEdit.getItems().addAll(Periodo.ORDINARIO, Periodo.EXTRAORIDINARIO);
	}

	private void cargarFormaciones() {
		List<FormacionEmpresa> fes = new ArrayList<>();

		if (session.getPerfil() == Perfil.TUTOR)
			fes = feService.findByTutor(tuService.find(session.getUserId()));
		else if (session.getPerfil() == Perfil.ALUMNADO)
			fes = feService.findByAlumno(alService.find(session.getUserId()));
		else
			fes = feService.findAll();

		ObservableList<FormacionEmpresa> datos = FXCollections.observableArrayList(fes);

		colAlumno.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getAlumno().getNombre()));
		colTutorCentro
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorCentro().getNombre()));
		colEmpresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmpresa().getNombre()));
		colTutorEmpresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorEmpresa().getNombre()));
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

	private void cargarAlumnos() {
		List<Alumno> alumnos = alService.findAll();
		ObservableList<Alumno> datos = FXCollections.observableArrayList(alumnos);

		if (!alumnos.isEmpty()) {
			cbAlumno.setItems(datos);
		}
	}

	private void cargarTutores() {
		List<Tutor> tutores = tuService.findAll();
		ObservableList<Tutor> datos = FXCollections.observableArrayList(tutores);

		if (!tutores.isEmpty()) {
			cbTutorEmpresa.setItems(datos);
			cbTutorEmpresaEdit.setItems(datos);
		}
	}

	private void cargarProfes() {
		List<Profesor> profes = prService.findAll();
		ObservableList<Profesor> datos = FXCollections.observableArrayList(profes);

		if (!profes.isEmpty()) {
			cbTutorCentro.setItems(datos);
			cbTutorCentroEdit.setItems(datos);
		}
	}

	@FXML
	private void switchNuevo() {
		if (!boxNuevo.isVisible()) {
			switchBox(boxNuevo, boxEvaluar, boxReasignar, boxFaltas);
			switchButton(btnNuevo, btnEvaluar, btnReasignar, btnFaltas);
		} else {
			guardar();
		}
	}

	@FXML
	private void switchReasignar() {
		if (!boxReasignar.isVisible()) {
			switchBox(boxReasignar, boxEvaluar, boxNuevo, boxFaltas);
			switchButton(btnReasignar, btnEvaluar, btnNuevo, btnFaltas);
		} else {
			editar();
		}
	}

	@FXML
	private void switchEvaluar() {
		if (!boxEvaluar.isVisible()) {
			switchBox(boxEvaluar, boxReasignar, boxNuevo, boxFaltas);
			switchButton(btnEvaluar, btnReasignar, btnNuevo, btnFaltas);
		} else {
			evaluar();
		}
	}

	@FXML
	private void switchFaltas() {
		if (!boxFaltas.isVisible()) {
			switchBox(boxFaltas, boxReasignar, boxNuevo, boxEvaluar);
			switchButton(btnFaltas, btnReasignar, btnNuevo, btnEvaluar);
		} else {
			registrarFalta();
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

		System.out.println(ras.size());
		System.out.println(ras.isEmpty());
		System.out.println("\\\\");
		for (ResultadoAprendizaje ra : ras) {

			EvaluacionRa ev = new EvaluacionRa();
			System.out.println("Llega");
			System.out.println(ra.getCodigo());

			ev.setResultadoAprendizaje(ra);
			fe.addEvaluacion(ev);
		}
		System.out.println("//");

		feService.save(fe);

		cargarFormaciones();
		limpiarForm(cbAlumno, cbTutorCentro, cbTutorEmpresa, cbPeriodo, dpIni, dpFin);
	}

	private void cargarEditar(FormacionEmpresa fe) {
		if (fe != null) {
			cbTutorEmpresaEdit.setValue(fe.getTutorEmpresa());
			cbTutorCentroEdit.setValue(fe.getTutorCentro());
			cbPeriodoEdit.setValue(fe.getPeriodo());
			dpIniEdit.setValue(fe.getFechaIni());
			dpFinEdit.setValue(fe.getFechaFin());

		}
	}

	private void editar() {
		FormacionEmpresa fe = cbEditarFE.getValue();
		if (fe == null)
			return;

		if (validar(null, lblAlumnoErrorEdit, cbTutorCentroEdit, cbTutorEmpresaEdit, cbPeriodoEdit, dpIniEdit,
				dpFinEdit, lblDateErrorEdit, true))
			return;

		fe.setTutorCentro(cbTutorCentroEdit.getValue());
		fe.setEmpresa(cbTutorEmpresaEdit.getValue().getEmpresa());
		fe.setTutorEmpresa(cbTutorEmpresaEdit.getValue());
		fe.setPeriodo(cbPeriodoEdit.getValue());
		fe.setFechaIni(dpIniEdit.getValue());
		fe.setFechaFin(dpFinEdit.getValue());
		if (dpIni.getValue().isAfter(LocalDate.now()))
			fe.setEstado(EstadoFE.ACTIVA);
		else if (dpFin.getValue().isAfter(LocalDate.now()))
			fe.setEstado(EstadoFE.FINALIZADA);

		feService.save(fe);

		cargarFormaciones();
		limpiarForm(cbAlumno, cbTutorCentro, cbTutorEmpresa, cbPeriodo, dpIni, dpFin);

	}

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

	private void evaluar() {
		FormacionEmpresa feSelected = cbEvaluarFE.getValue();

		if (feSelected == null)
			return;

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

	private void registrarFalta() {

	}

	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
