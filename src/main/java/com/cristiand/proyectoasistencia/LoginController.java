package com.cristiand.proyectoasistencia;

import Logica.Conexion;
import Logica.Usuario;
import Logica.UsuarioDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;


public class LoginController {

    GestorVentanas gestor = new GestorVentanas();
    UsuarioDAO usuarioDAO = new UsuarioDAO();
    @FXML
    private ComboBox<Usuario> CBUsuarios;
    @FXML
    private Button btnIngresar;
    @FXML
    private TextField TxtCampoUsuario;
    @FXML
    private TextField TxtCampoPass;
    @FXML
    private Label welcomeText;

    @FXML
    public void initialize() {
        cargarUsuarios();
        CBUsuarios.setPromptText("-- Selecciona un usuario --");
    }

    private void cargarUsuarios() {
        // 1. Obtener usuarios de la base de datos
        List<Usuario> usuarios = usuarioDAO.listarTodos();

        // 2. Convertir a ObservableList
        ObservableList<Usuario> listaObservable = FXCollections.observableArrayList(usuarios);

        // 3. Cargar en el ComboBox
        CBUsuarios.setItems(listaObservable);
    }

    // Método para obtener el usuario seleccionado
    @FXML
    protected void onSeleccionarUsuario() {
        Usuario usuarioSeleccionado = CBUsuarios.getValue();

        if (usuarioSeleccionado != null) {
            System.out.println("Usuario seleccionado: " + usuarioSeleccionado.getNombreUsuario());
            System.out.println("ID: " + usuarioSeleccionado.getId());
            System.out.println("¿Es admin?: " + usuarioSeleccionado.isAdmin());
        } else {
            System.out.println("No hay usuario seleccionado");
        }
    }

    @FXML
    protected void onLoginButtonClick() {

        Usuario usuarioSeleccionado = CBUsuarios.getValue();
        if (usuarioSeleccionado == null) {
            gestor.mostrarAdvertencia("Por favor, selecciona un usuario");
            return;
        }

        // 1. VALIDAR que los campos no estén vacíos
        String nombreUsuario = TxtCampoUsuario.getText().trim();
        String password = TxtCampoPass.getText().trim();

        if (nombreUsuario.isEmpty()) {
            gestor.mostrarAdvertencia("Por favor, ingresa tu usuario");
            return;
        }

        if (password.isEmpty()) {
            gestor.mostrarAdvertencia("Por favor, ingresa tu contraseña");
            return;
        }

        // 2. Intentar autenticar
        Usuario usuario = usuarioDAO.autenticar(nombreUsuario, password);
        String passwordGuardada = usuarioSeleccionado.getPassword();

        // 3. Verificar resultado
        if (usuario != null) {
            // Login exitoso
            gestor.mostrarExito("¡Bienvenido, " + usuario.getNombreUsuario() + "!");

            // Cerrar ventana de login
            Stage stage = (Stage) btnIngresar.getScene().getWindow();
            stage.close();

            // Abrir menú principal
            if (usuario.isAdmin()) {
                gestor.crearVentana("menuAdmin.fxml", "Menú Administrador");
            } else {
                gestor.crearVentana("menu.fxml", "Menú Principal");
            }

        } else {
            // Login fallido
            gestor.mostrarError("Usuario o contraseña incorrectos");
            TxtCampoPass.clear(); // Limpiar contraseña por seguridad
        }

    }

    @FXML
    protected void onMostrarUsuariosClick() {

    }

    @FXML
    protected void onRecuperarPassClick() {

    }
}