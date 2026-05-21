package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Empresa;
import com.simao.tarea3AD2024base.services.EmpresaService;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

/**
 * Clase GestionEmpresaController.java
 * 
 * Gestiona las interacciones con la interfaz de gestión de empresas.
 */
@Controller
public class GestionEmpresaController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private EmpresaService emService;

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
	private TextField txtDireccion;

	@FXML
	private ComboBox<Empresa> cbEditarEmpresa;

	@FXML
	private TextField txtEditNombre;

	@FXML
	private Label lblEditNombreError;

	@FXML
	private TextField txtEditDireccion;

	@FXML
	private Button btnBuscar;

	@FXML
	private Button btnNuevo;

	@FXML
	private Button btnEditar;

	@FXML
	private TableView<Empresa> tablaEmpresas;

	@FXML
	private TableColumn<Empresa, String> colNombre;

	@FXML
	private TableColumn<Empresa, String> colDireccion;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cbEditarEmpresa.getSelectionModel().selectedItemProperty().addListener((_, _, empresa) -> {
			cargarEditar(empresa);
		});

		cargarEmpresas();
	}

	/**
	 * Carga todos las empresas desde la base de datos y las muestra en la interfaz.
	 * 
	 * Actualiza la tabla de visualización, los ComboBox relacionados, y configura
	 * las columnas de la tabla con los valores correspondientes.
	 */
	private void cargarEmpresas() {
		List<Empresa> empresas = emService.findAll();
		ObservableList<Empresa> datos = FXCollections.observableArrayList(empresas);

		colNombre.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNombre()));
		colDireccion.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getDireccion()));
		if (!empresas.isEmpty()) {
			tablaEmpresas.setItems(datos);
			cbEditarEmpresa.setItems(datos);
		}
	}

	/**
	 * Alterna la visivilidad del menú de búsqueda de Empresas.
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
	 * Alterna la visivilidad del menú de creación de Empresas.
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
	 * Alterna la visivilidad del menú de edición de Empresas.
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
	 * Filtra la lista de empresas según el nombre introducido en el buscador.
	 * 
	 * Si el campo de búsqueda está vacío, se recargan todas las empresas. Si no, se
	 * muestran solo las que coincidan con el nombre.
	 */
	@FXML
	private void buscar() {
		String txt = txtBuscador.getText().trim();
		if (txt.isEmpty()) {
			cargarEmpresas();
		} else {
			tablaEmpresas.setItems(FXCollections.observableArrayList(emService.findByNombreParcial(txt)));
		}
	}

	/**
	 * Valida y guarda una nueva empresa.
	 * 
	 * Si la validación es correcta, se crea una nueva entidad Empresa, se guarda en
	 * la base de datos, se actualiza la lista de empresas y se cambia al menú de
	 * búsqueda, además de notificar la creación por medio de un evento.
	 */
	@FXML
	private void guardar() {
		if (validar(txtNombre, lblNombreError, txtDireccion, false))
			return;

		Empresa empresa = new Empresa();

		empresa.setNombre(txtNombre.getText());
		empresa.setDireccion(txtDireccion.getText());

		emService.save(empresa);

		switchBuscar();
		cargarEmpresas();

		evPublisher.publishEvent(new NewEmpresaEvent(empresa));
	}

	/**
	 * Carga los datos de una empresa en el formulario de edición.
	 *
	 * @param empresa Empresa cuyos datos se cargarán en el formulario.
	 */
	private void cargarEditar(Empresa empresa) {
		if (empresa != null) {
			txtEditNombre.setText(empresa.getNombre());
			txtEditDireccion.setText(empresa.getDireccion());
		}
	}

	/**
	 * Valida y edita una empresa.
	 * 
	 * Si la validación es correcta, actualiza la entidad Empresa seleccionada en la
	 * base de datos, se actualiza la lista de empresas y se cambia al menú de
	 * búsqueda, además de notificar la actualización por medio de un evento.
	 */
	private void editar() {
		Empresa empresa = cbEditarEmpresa.getValue();
		if (empresa == null)
			return;

		if (validar(txtEditNombre, lblEditNombreError, txtEditDireccion, true))
			return;

		empresa.setNombre(txtEditNombre.getText());
		empresa.setDireccion(txtEditDireccion.getText());

		emService.update(empresa);

		switchBuscar();
		cargarEmpresas();

		evPublisher.publishEvent(new NewEmpresaEvent(empresa));
	}

	/**
	 * Valida los datos de una empresa antes de su creación o edición.
	 * 
	 * Validaciones: - El nombre no está vacío ni duplicado - La dirección no está
	 * vacía
	 * 
	 * @param tfNombre
	 * @param lblNombre
	 * @param tfDireccion
	 * @param edit        indica si la validación corresponde a edición (true) o
	 *                    creación (false)
	 * @return true si existen errores de validación, false si los datos son válidos
	 */
	private boolean validar(TextField tfNombre, Label lblNombre, TextField tfDireccion, boolean edit) {

		Empresa empNombre = emService.findByNombre(tfNombre.getText());
		Empresa empEdit = edit ? cbEditarEmpresa.getValue() : null;

		String txtNombre = tfNombre.getText();
		boolean nombre = txtNombre.isEmpty();
		tfNombre.pseudoClassStateChanged(EMPTY, nombre);

		if (!nombre) {
			if (empNombre != null && (empEdit == null || !empNombre.getId().equals(empEdit.getId()))) {
				nombre = true;
				lblNombre.setText("Este nombre ya está registrado en la base de datos.");
			}

			lblNombre.setManaged(nombre);
			lblNombre.setVisible(nombre);

		} else {
			lblNombre.setManaged(false);
			lblNombre.setVisible(false);
		}

		String txtDireccion = tfDireccion.getText();
		boolean direccion = txtDireccion.isEmpty();
		tfDireccion.pseudoClassStateChanged(EMPTY, direccion);

		return nombre || direccion;
	}
}
