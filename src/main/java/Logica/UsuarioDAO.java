package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UsuarioDAO {

    public List<Usuario> listarTodos() {
        List<Usuario> usuarios = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM usuarios";
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_usuario");
                String nombreUsuario = resultSet.getString("nombre_usuario");
                String password = resultSet.getString("password");
                boolean admin = resultSet.getBoolean("admin");

                usuarios.add(new Usuario(id, nombreUsuario, password, admin));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return usuarios;
    }

    public Usuario autenticar(String nombreUsuario, String password) {
        Usuario usuario = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM usuarios WHERE nombre_usuario = ?";
            statement = conn.prepareStatement(sql);
            statement.setString(1, nombreUsuario);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String user = resultSet.getString("nombre_usuario");
                String pass = resultSet.getString("password");
                boolean admin = resultSet.getBoolean("admin");

                // Verificar contraseña
                if (password.equals(pass)) {
                    usuario = new Usuario(id, user, pass, admin);
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return usuario;
    }

    private void cerrarRecursos(Connection conn, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
