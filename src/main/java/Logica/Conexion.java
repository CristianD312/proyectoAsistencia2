package Logica;

import com.cristiand.proyectoasistencia.GestorVentanas;
import javafx.scene.control.Alert;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexion {
    private static final String URL = "jdbc:mysql://localhost:3306/registro_inasistencias";
    private static final String USUARIO = "root";
    private static final String PASS = "";
    Connection conn = null;
    static GestorVentanas gestor = new GestorVentanas();

    public Conexion() {
    }

    public void setConn(Connection conn) {
        this.conn = conn;
    }

    public static Connection conectar() {

        Connection conn= null;
        try {
            conn = DriverManager.getConnection(URL, USUARIO, PASS);

        } catch (SQLException ex) {
            gestor.mostrarError("Error al conectar");
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return conn;
    }
}