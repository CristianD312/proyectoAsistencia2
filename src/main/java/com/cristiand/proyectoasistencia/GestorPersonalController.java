package com.cristiand.proyectoasistencia;

import Logica.Personal;
import Logica.PersonalDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class GestorPersonalController {

    // ========== CAMPOS DE TEXTO ==========
    @FXML
    private TextField txtCampoNombre;
    @FXML
    private TextField txtCampoApellido;
    @FXML
    private TextField txtCampoDni;
    @FXML
    private TextField txtCampoCargo;

    // ========== BOTONES ==========
    @FXML
    private Button btnCargar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private Button btnBuscarDni;
    @FXML
    private Button btnCargarTabla;

    // ========== TABLA ==========
    @FXML
    private TableView<Personal> tablaPersonal;
    @FXML
    private TableColumn<Personal, Integer> colID;
    @FXML
    private TableColumn<Personal, String> colNombre;
    @FXML
    private TableColumn<Personal, String> colApellido;
    @FXML
    private TableColumn<Personal, Integer> colDNI;
    @FXML
    private TableColumn<Personal, String> colCargo;

    // ========== INSTANCIAS ==========
    private PersonalDAO personalDAO = new PersonalDAO();
    private GestorVentanas gestor = new GestorVentanas();
    private Personal personalSeleccionado = null; // Para actualizar/eliminar

    // ========== INICIALIZACIÓN ==========
    @FXML
    public void initialize() {
        configurarTabla();
        cargarTabla();
        configurarSeleccionTabla();
    }

    /**
     * Configura las columnas de la tabla
     */
    private void configurarTabla() {
        colID.setCellValueFactory(cellData -> {
            Personal personal = cellData.getValue();
            return new SimpleIntegerProperty(personal.getId_personal()).asObject();
        });

        colNombre.setCellValueFactory(cellData -> {
            Personal personal = cellData.getValue();
            return new SimpleStringProperty(personal.getNombre());
        });

        colApellido.setCellValueFactory(cellData -> {
            Personal personal = cellData.getValue();
            return new SimpleStringProperty(personal.getApellido());
        });

        colDNI.setCellValueFactory(cellData -> {
            Personal personal = cellData.getValue();
            return new SimpleIntegerProperty(personal.getCUIL()).asObject();
        });

        colCargo.setCellValueFactory(cellData -> {
            Personal personal = cellData.getValue();
            return new SimpleStringProperty(personal.getCargo());
        });
    }

    /**
     * Configura el evento de selección en la tabla
     * Al hacer clic en una fila, carga los datos en los campos
     */
    private void configurarSeleccionTabla() {
        tablaPersonal.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                personalSeleccionado = newValue;
                cargarDatosEnCampos(newValue);
            }
        });
    }

    /**
     * Carga los datos de un Personal en los campos de texto
     */
    private void cargarDatosEnCampos(Personal personal) {
        txtCampoNombre.setText(personal.getNombre());
        txtCampoApellido.setText(personal.getApellido());
        txtCampoDni.setText(String.valueOf(personal.getCUIL()));
        txtCampoCargo.setText(personal.getCargo());
    }

    // ========== CRUD: CREATE (GUARDAR) ==========
    @FXML
    protected void onCargarPersonalClick() {
        // Validar campos
        if (!validarCampos()) {
            return;
        }

        try {
            String nombre = txtCampoNombre.getText().trim();
            String apellido = txtCampoApellido.getText().trim();
            int dni = Integer.parseInt(txtCampoDni.getText().trim());
            String cargo = txtCampoCargo.getText().trim();

            // Verificar si el DNI ya existe
            Personal existe = personalDAO.buscarPorDni(dni);
            if (existe != null) {
                gestor.mostrarAdvertencia("El DNI " + dni + " ya está registrado");
                return;
            }

            // Crear objeto Personal
            Personal nuevoPersonal = new Personal(nombre, apellido, dni, cargo);

            // Guardar en BD
            if (personalDAO.guardar(nuevoPersonal)) {
                gestor.mostrarExito("Personal guardado exitosamente");
                limpiarCampos();
                cargarTabla();
            } else {
                gestor.mostrarError("Error al guardar el personal");
            }

        } catch (NumberFormatException e) {
            gestor.mostrarError("El DNI debe ser un número válido");
        }
    }

    // ========== CRUD: READ (BUSCAR) ==========
    @FXML
    protected void onBuscarPorDniClick() {
        String dniTexto = txtCampoDni.getText().trim();

        if (dniTexto.isEmpty()) {
            gestor.mostrarAdvertencia("Ingresa un DNI para buscar");
            return;
        }

        try {
            int dni = Integer.parseInt(dniTexto);
            Personal personal = personalDAO.buscarPorDni(dni);

            if (personal != null) {
                cargarDatosEnCampos(personal);
                personalSeleccionado = personal;
                gestor.mostrarExito("Personal encontrado");
            } else {
                gestor.mostrarAdvertencia("No se encontró personal con DNI: " + dni);
            }

        } catch (NumberFormatException e) {
            gestor.mostrarError("El DNI debe ser un número válido");
        }
    }

    // ========== CRUD: UPDATE (ACTUALIZAR) ==========
    @FXML
    protected void onActualizarPersonalClick() {
        if (personalSeleccionado == null) {
            gestor.mostrarAdvertencia("Selecciona un personal de la tabla o búscalo por DNI");
            return;
        }

        if (!validarCampos()) {
            return;
        }

        try {
            String nombre = txtCampoNombre.getText().trim();
            String apellido = txtCampoApellido.getText().trim();
            int dni = Integer.parseInt(txtCampoDni.getText().trim());
            String cargo = txtCampoCargo.getText().trim();

            // Actualizar objeto
            personalSeleccionado.setNombre(nombre);
            personalSeleccionado.setApellido(apellido);
            personalSeleccionado.setCUIL(dni);
            personalSeleccionado.setCargo(cargo);

            // Actualizar en BD
            if (personalDAO.actualizar(personalSeleccionado)) {
                gestor.mostrarExito("Personal actualizado exitosamente");
                limpiarCampos();
                cargarTabla();
                personalSeleccionado = null;
            } else {
                gestor.mostrarError("Error al actualizar el personal");
            }

        } catch (NumberFormatException e) {
            gestor.mostrarError("El DNI debe ser un número válido");
        }
    }

    // ========== CRUD: DELETE (ELIMINAR) ==========
    @FXML
    protected void onEliminarPersonalClick() {
        if (personalSeleccionado == null) {
            gestor.mostrarAdvertencia("Selecciona un personal de la tabla para eliminar");
            return;
        }

        // Confirmación (opcional pero recomendado)
        // Aquí podrías agregar un Alert de confirmación

        if (personalDAO.eliminar(personalSeleccionado.getId_personal())) {
            gestor.mostrarExito("Personal eliminado exitosamente");
            limpiarCampos();
            cargarTabla();
            personalSeleccionado = null;
        } else {
            gestor.mostrarError("Error al eliminar el personal");
        }
    }

    // ========== MÉTODOS AUXILIARES ==========

    /**
     * Carga todos los registros en la tabla
     */
    @FXML
    protected void onCargarTablaClick() {
        cargarTabla();
        gestor.mostrarExito("Tabla recargada");
    }

    private void cargarTabla() {
        List<Personal> listaPersonal = personalDAO.listarTodos();
        ObservableList<Personal> listaObservable = FXCollections.observableArrayList(listaPersonal);
        tablaPersonal.setItems(listaObservable);
    }

    /**
     * Limpia todos los campos de texto
     */
    @FXML
    protected void onLimpiarCamposClick() {
        limpiarCampos();
        gestor.mostrarExito("Campos limpiados");
    }

    private void limpiarCampos() {
        txtCampoNombre.clear();
        txtCampoApellido.clear();
        txtCampoDni.clear();
        txtCampoCargo.clear();
        personalSeleccionado = null;
        tablaPersonal.getSelectionModel().clearSelection();
    }

    /**
     * Valida que todos los campos estén llenos
     * @return true si todos los campos son válidos
     */
    private boolean validarCampos() {
        if (txtCampoNombre.getText().trim().isEmpty()) {
            gestor.mostrarAdvertencia("El campo Nombre es obligatorio");
            txtCampoNombre.requestFocus();
            return false;
        }

        if (txtCampoApellido.getText().trim().isEmpty()) {
            gestor.mostrarAdvertencia("El campo Apellido es obligatorio");
            txtCampoApellido.requestFocus();
            return false;
        }

        if (txtCampoDni.getText().trim().isEmpty()) {
            gestor.mostrarAdvertencia("El campo DNI es obligatorio");
            txtCampoDni.requestFocus();
            return false;
        }

        if (txtCampoCargo.getText().trim().isEmpty()) {
            gestor.mostrarAdvertencia("El campo Cargo es obligatorio");
            txtCampoCargo.requestFocus();
            return false;
        }

        // Validar que DNI sea número
        try {
            Integer.parseInt(txtCampoDni.getText().trim());
        } catch (NumberFormatException e) {
            gestor.mostrarError("El DNI debe ser un número válido");
            txtCampoDni.requestFocus();
            return false;
        }

        return true;
    }
}
