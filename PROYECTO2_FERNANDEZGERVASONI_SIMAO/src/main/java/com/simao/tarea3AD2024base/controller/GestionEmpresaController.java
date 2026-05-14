package com.simao.tarea3AD2024base.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;

import com.simao.tarea3AD2024base.config.StageManager;
import com.simao.tarea3AD2024base.modelo.Empresa;
import com.simao.tarea3AD2024base.services.EmpresaService;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@Controller
public class GestionEmpresaController implements Initializable {

	private PseudoClass EMPTY = PseudoClass.getPseudoClass("error");

	@Lazy
	@Autowired
	private StageManager stageManager;

	@Autowired
	private EmpresaService emService;

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
			cargarEmpresas();
		} else {
			tablaEmpresas.setItems(FXCollections.observableArrayList(emService.findByNombreParcial(txt)));
		}
	}

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
	}

	private void cargarEditar(Empresa empresa) {
		if (empresa != null) {
			txtEditNombre.setText(empresa.getNombre());
			txtEditDireccion.setText(empresa.getDireccion());
		}
	}

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
	}

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

	public void logout(ActionEvent event) {
		stageManager.switchScene(FxmlView.LOGIN);
	}

	public void exit(ActionEvent event) {
		System.exit(0);
	}
}
