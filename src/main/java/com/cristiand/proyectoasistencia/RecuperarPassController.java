package com.cristiand.proyectoasistencia;

import Logica.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecuperarPassController {

    GestorVentanas gestor = new GestorVentanas();
    UsuarioDAO userDao = new UsuarioDAO();

    @FXML
    public Button BtnBuscarUsuario;
    @FXML
    public TextField txtCampoUsuario;
    @FXML
    public Label LblPregunta;
    @FXML
    public TextField txtCampoRespuesta;
    @FXML
    public Button btnIngresarRespuesta;

    @FXML
    protected void onBuscarUsuarioClick() {
        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String nombreUsuario = txtCampoUsuario.getText();

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String preguntaSecreta = resultSet.getString("pregunta_secreta");
                LblPregunta.setText(preguntaSecreta);
            } else {
                gestor.mostrarError("No se encontró el usuario");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                gestor.mostrarError("Error" + ex.toString());
            }

        }
    }


    @FXML
    protected void onIngresarRespuestaClick() {

        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String respuestaIngresada = txtCampoRespuesta.getText();
        String nombreUsuario = txtCampoUsuario.getText();
        String respuestaSecreta = null;
        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            resultSet = statement.executeQuery();
            if (resultSet.next()) {
                respuestaSecreta = resultSet.getString("respuesta_secreta");

            }

            if (respuestaSecreta.equals(respuestaIngresada)) {
                gestor.crearVentana("CambiarPass.fxml", "Cambiar Contraseña");
            } else {
                gestor.mostrarError("Respuesta invalidad");
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                gestor.mostrarError("Error" + ex.toString());
            }

        }


    }


}
