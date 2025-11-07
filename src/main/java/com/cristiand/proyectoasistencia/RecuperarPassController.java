package com.cristiand.proyectoasistencia;

import Logica.Conexion;
import Logica.Personal;
import Logica.PersonalDAO;
import Logica.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecuperarPassController {

    @FXML
    public Button BtnBuscarUsuario;
    @FXML
    public TextField txtCampoUsuario;
    @FXML
    protected void onBuscarUsuarioClick() {
        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE nombre_usuario = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, nombre_usuario);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {

            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }
    }
}
