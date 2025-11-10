package com.cristiand.proyectoasistencia;

import Logica.Materia;
import Logica.MateriaDAO;
import Logica.Personal;
import Logica.PersonalDAO;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.List;

public class GestorMateriaController {

    @FXML
    private Button btnCargar;
    @FXML
    private Button btnActualizar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnLimpiar;
    @FXML
    private TextField txtCampoGrupo;
    @FXML
    private TextField txtCampoCantidadModulos;
    @FXML
    public TextField txtCampoMateria;
    @FXML
    public TextField txtCampoOrientacion;

    @FXML
    private TableView<Materia> tablaMateria;
    @FXML
    private TableColumn<Materia,Integer> colID;
    @FXML
    public TableColumn <Materia,String>colMateria;
    @FXML
    public TableColumn <Materia,Integer>colCantModulos;
    @FXML
    public TableColumn <Materia,Integer>colGrupo;
    @FXML
    public TableColumn <Materia,String>colOrientacion;


    // ========== INSTANCIAS ==========
    private MateriaDAO materiaDAO = new MateriaDAO();
    private GestorVentanas gestor = new GestorVentanas();
    private Materia materiaSeleccionada = null; // Para actualizar/eliminar

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
            Materia materia = cellData.getValue();
            return new SimpleIntegerProperty(materia.getIdMateria()).asObject();
        });

        colMateria.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            return new SimpleStringProperty(materia.getNombreMateria());
        });

        colCantModulos.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            return new SimpleIntegerProperty(materia.getCantidadModulos()).asObject();
        });

        colGrupo.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            return new SimpleIntegerProperty(materia.getGrupo()).asObject();
        });

        colOrientacion.setCellValueFactory(cellData -> {
            Materia materia = cellData.getValue();
            return new SimpleStringProperty(materia.getOrientacion());
        });
    }
    private void configurarSeleccionTabla() {
        tablaMateria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                materiaSeleccionada = newValue;
                cargarDatosEnCampos(newValue);
            }
        });

    }
    private void cargarTabla() {
        List<Materia> listaMateria = materiaDAO.listarTodos();
        ObservableList<Materia> listaObservable = FXCollections.observableArrayList(listaMateria);
        tablaMateria.setItems(listaObservable);
    }
    private void cargarDatosEnCampos(Materia materia) {
        txtCampoMateria.setText(materia.getNombreMateria());
        txtCampoCantidadModulos.setText(String.valueOf(materia.getCantidadModulos()));
        txtCampoGrupo.setText(String.valueOf(materia.getGrupo()));
        txtCampoOrientacion.setText(materia.getOrientacion());
    }
    @FXML
    protected void onCargarMateriaClick() {
    }

    public void onActualizarMateriaClick() {
    }

    public void onEliminarMateriaClick() {
    }

    public void onLimpiarCamposClick() {
    }
}
