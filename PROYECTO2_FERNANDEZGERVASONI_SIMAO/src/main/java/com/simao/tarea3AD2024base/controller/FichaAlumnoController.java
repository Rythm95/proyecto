package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Alumno;
import com.simao.tarea3AD2024base.modelo.EvaluacionRa;
import com.simao.tarea3AD2024base.modelo.Falta;
import com.simao.tarea3AD2024base.modelo.FormacionEmpresa;
import com.simao.tarea3AD2024base.modelo.Perfil;
import com.simao.tarea3AD2024base.services.AlumnoService;
import com.simao.tarea3AD2024base.services.FaltaService;
import com.simao.tarea3AD2024base.services.FormacionEmpresaService;
import com.simao.tarea3AD2024base.services.Session;
import com.simao.tarea3AD2024base.view.FxmlView;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

/**
 * Clase FichaAlumnoController.java
 * 
 * Muestra toda la información del alumno en sesión, junto con la de sus FEs.
 */
@Controller
public class FichaAlumnoController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private AlumnoService alService;

	@Autowired
	private FormacionEmpresaService feService;

	@Autowired
	private FaltaService faService;

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
	private Button btnFaltas;

	@FXML
	private Button btnEval;

	@FXML
	private Button btnExp;

	@FXML
	private TableView<FormacionEmpresa> tvFormaciones;

	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorCentro;

	@FXML
	private TableColumn<FormacionEmpresa, String> colEmpresa;

	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorEmpresa;

	@FXML
	private TableColumn<FormacionEmpresa, LocalDate> colFechaIni;

	@FXML
	private TableColumn<FormacionEmpresa, LocalDate> colFechaFin;

	@FXML
	private TableColumn<FormacionEmpresa, String> colPeriodo;

	@FXML
	private TableColumn<FormacionEmpresa, String> colEstado;

	@FXML
	private TitledPane tpFaltas;

	@FXML
	private VBox formJustificar;

	@FXML
	private Label lblJustificacion;

	@FXML
	private TextField txtJustificante;

	@FXML
	private TableView<Falta> tvFaltas;

	@FXML
	private TableColumn<Falta, String> colFechaFalta;

	@FXML
	private TableColumn<Falta, String> colDescripcionFalta;

	@FXML
	private TableColumn<Falta, String> colJustificada;

	@FXML
	private TableColumn<Falta, String> colJustificante;

	@FXML
	private TitledPane tpEval;

	@FXML
	private TableView<EvaluacionRa> tvEval;

	@FXML
	private TableColumn<EvaluacionRa, String> colModuloRA;

	@FXML
	private TableColumn<EvaluacionRa, String> colNombreRA;

	@FXML
	private TableColumn<EvaluacionRa, String> colDescripcionRA;

	@FXML
	private TableColumn<EvaluacionRa, String> colResultadoEval;

	@FXML
	private TitledPane tpExper;

	@FXML
	private TableView<FormacionEmpresa> tvExper;

	@FXML
	private TableColumn<FormacionEmpresa, String> colEmpresaVal;

	@FXML
	private TableColumn<FormacionEmpresa, String> colTutorVal;

	@FXML
	private TableColumn<FormacionEmpresa, String> colVal;

	@FXML
	private VBox formValorar;

	@FXML
	private TextArea txtValorar;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		if (session.getPerfil() == Perfil.TUTOR) {
			btnExp.setVisible(false);
			btnExp.setManaged(false);
		}

		tpEval.setVisible(false);
		tpEval.setManaged(false);
		tpExper.setVisible(false);
		tpExper.setManaged(false);

		Alumno al = alService.find(session.getIdGest());
		cargarDatos(al);

		List<FormacionEmpresa> fes = feService.findByAlumno(al);

		if (fes.isEmpty()) {
			tpFaltas.setManaged(false);
			tpFaltas.setVisible(false);
			btnEval.setVisible(false);
			btnExp.setVisible(false);
			btnFaltas.setVisible(false);
		} else
			cargarFEs(fes);

		tvFormaciones.getSelectionModel().selectedItemProperty().addListener((_, _, fe) -> {
			if (fe != null) {
				cargarFaltas(fe);
				cargarEvaluaciones(fe);
				cargarValoraciones(fe);
			}
		});
	}

	/**
	 * Carga los datos de un alumno y los muestra en la interfáz.
	 * 
	 * @param al
	 */
	private void cargarDatos(Alumno al) {
		lblNombre.setText(al.getNombre());
		lblEmail.setText(al.getEmail());
		lblEdad.setText(al.isMayoriaEdad() ? "Sí" : "No");
		lblCurso.setText(al.getCurso().getNombre());
		lblCoordinador.setText(al.getCurso().getCoordinador().getNombre());
	}

	/**
	 * Alterna la visivilidad del menú de vista de faltas.
	 * 
	 * Si el menú no está visible, lo muestra.
	 */
	@FXML
	private void switchFaltas() {
		if (!tpFaltas.isVisible()) {
			switchBox(tpFaltas, tpEval, tpExper);
			switchButton(btnFaltas, btnEval, btnExp);
		}
	}

	/**
	 * Alterna la visivilidad del menú de vista de evaluaciones.
	 * 
	 * Si el menú no está visible, lo muestra.
	 */
	@FXML
	private void switchEval() {
		if (!tpEval.isVisible()) {
			switchBox(tpEval, tpFaltas, tpExper);
			switchButton(btnEval, btnFaltas, btnExp);
		}
	}

	/**
	 * Alterna la visivilidad del menú de valoración de experiencia.
	 * 
	 * Si el menú no está visible, lo muestra.
	 */
	@FXML
	private void switchExp() {
		if (!tpExper.isVisible()) {
			switchBox(tpExper, tpEval, tpFaltas);
			switchButton(btnExp, btnEval, btnFaltas);
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
	 * Muestra un TitledPane y oculta el resto.
	 * 
	 * El TitledPane activo pasa a ser visible y los inactivos se ocultan y dejan de
	 * ocupar espacio en la interfaz.
	 *
	 * @param activo    TitledPane que se mostrará
	 * @param inactivos TitledPane que se ocultarán
	 */
	private void switchBox(TitledPane activo, TitledPane... inactivos) {

		activo.setManaged(true);
		activo.setVisible(true);

		for (TitledPane tp : inactivos) {
			tp.setManaged(false);
			tp.setVisible(false);
		}
	}

	/**
	 * Carga la información de una FE del alumno en una card.
	 * 
	 * @param fe
	 */
	private void cargarFEs(List<FormacionEmpresa> fes) {
		ObservableList<FormacionEmpresa> datos = FXCollections.observableArrayList(fes);

		colEmpresa.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmpresa().getNombre()));
		colPeriodo.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getPeriodo().toString()));
		colFechaIni.setCellValueFactory(new PropertyValueFactory<>("fechaIni"));
		colFechaFin.setCellValueFactory(new PropertyValueFactory<>("fechaFin"));
		colTutorEmpresa
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorEmpresa().getNombre()));
		colTutorCentro
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorCentro().getNombre()));
		colEstado.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));

		tvFormaciones.setItems(datos);
	}

	/**
	 * Carga todas las Faltas de una FE seleccionada y los muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización y configura las columnas de la tabla con
	 * los valores correspondientes.
	 * 
	 * @param fe
	 */
	private void cargarFaltas(FormacionEmpresa fe) {
		if (fe.getFaltas().isEmpty()) {
			tvFaltas.getItems().clear();
			tvFaltas.setPlaceholder(new Label("No hay faltas registradas para esta FE."));
			return;
		}

		ObservableList<Falta> datos = FXCollections.observableArrayList(fe.getFaltas());
		tvFaltas.setItems(datos);
		colFechaFalta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getFecha().toString()));
		colDescripcionFalta.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDescripcion()));
		colJustificada.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().isJustificada() ? "Justificada" : "Injustificada"));
		colJustificante.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getJustificante()));
	}

	@FXML
	private void justificar() {
		lblJustificacion.setText("");

		Falta i = tvFaltas.getSelectionModel().getSelectedItem();

		if (i == null) {
			lblJustificacion.setText("Selecciona una falta.");
			return;
		}

		if (i.isJustificada()) {
			lblJustificacion.setText("La falta ya está justificada.");
			return;
		}

		boolean just = txtJustificante.getText().isBlank();
		txtJustificante.pseudoClassStateChanged(EMPTY, just);

		if (just)
			return;

		i.setJustificada(true);
		i.setJustificante(txtJustificante.getText());

		faService.update(i);

		txtJustificante.clear();
		tvFaltas.refresh();
		lblJustificacion.setText("Falta justificada.");
	}

	private void cargarEvaluaciones(FormacionEmpresa fe) {
		FormacionEmpresa feComp = feService.findCompleta(fe.getId());

		if (feComp.getEvaluaciones().isEmpty()) {
			tvEval.getItems().clear();
			tvEval.setPlaceholder(new Label("Esta FE no se ha evaluado aún."));
			return;
		}

		ObservableList<EvaluacionRa> datos = FXCollections.observableArrayList(feComp.getEvaluaciones());
		tvEval.setItems(datos);
		colModuloRA.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().getResultadoAprendizaje().getModulo().getNombre()));
		colNombreRA.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().getResultadoAprendizaje().getCodigo()));
		colDescripcionRA.setCellValueFactory(
				data -> new SimpleStringProperty(data.getValue().getResultadoAprendizaje().getDescripcion()));
		colResultadoEval.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEstado().toString()));
	}

	private void cargarValoraciones(FormacionEmpresa fe) {

		ObservableList<FormacionEmpresa> datos = FXCollections.observableArrayList(fe);
		tvExper.setItems(datos);
		colEmpresaVal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getEmpresa().getNombre()));
		colTutorVal
				.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getTutorEmpresa().getNombre()));
		colVal.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getValoracion()));
	}

	@FXML
	private void valorar() {

		FormacionEmpresa fe = tvExper.getSelectionModel().getSelectedItem();
		if (fe == null) {
			fe = tvFormaciones.getSelectionModel().getSelectedItem();
			if (fe == null)
				return;
		}

		boolean val = txtValorar.getText().isBlank();
		txtValorar.pseudoClassStateChanged(EMPTY, val);

		if (val)
			return;

		fe.setValoracion(txtValorar.getText());
		feService.update(fe);
		tvExper.refresh();
	}

	/**
	 * Vuelve a la pantalla de gestión según el perfil de la sesión.
	 */
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
