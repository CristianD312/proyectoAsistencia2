package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PersonalDAO {

    // ========== CREAR (INSERT) ==========

    /**
     * Guarda un nuevo registro de personal en la base de datos
     * @param personal Objeto Personal con los datos a guardar
     * @return true si se guardó correctamente, false si hubo error
     */
    public boolean guardar(Personal personal) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "INSERT INTO personal (nombre, apellido, dni, cargo) VALUES (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);

            statement.setString(1, personal.getNombre());
            statement.setString(2, personal.getApellido());
            statement.setInt(3, personal.getDni());
            statement.setString(4, personal.getCargo());

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            cerrarRecursos(conn, statement, null);
        }
    }

    // ========== LEER (SELECT) ==========

    /**
     * Busca un personal por su ID
     * @param id ID del personal a buscar
     * @return Objeto Personal si lo encuentra, null si no existe
     */
    public Personal buscarPorId(int id) {
        Personal personal = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE id_personal = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idProfe = resultSet.getInt("id_personal");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int dni = resultSet.getInt("CUIL");
                String cargo = resultSet.getString("cargo");

                personal = new Personal(idProfe, nombre, apellido, dni, cargo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return personal;
    }

    /**
     * Busca un personal por su DNI
     * @param dni DNI del personal a buscar
     * @return Objeto Personal si lo encuentra, null si no existe
     */
    public Personal buscarPorDni(int dni) {
        Personal personal = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE dni = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, dni);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idProfe = resultSet.getInt("id_profe");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int dniResult = resultSet.getInt("dni");
                String cargo = resultSet.getString("cargo");

                personal = new Personal(idProfe, nombre, apellido, dniResult, cargo);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return personal;
    }

    /**
     * Lista todo el personal registrado
     * @return Lista de objetos Personal
     */
    public List<Personal> listarTodos() {
        List<Personal> listaPersonal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal ORDER BY apellido, nombre";
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idProfe = resultSet.getInt("id_profe");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int dni = resultSet.getInt("dni");
                String cargo = resultSet.getString("cargo");

                listaPersonal.add(new Personal(idProfe, nombre, apellido, dni, cargo));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return listaPersonal;
    }

    /**
     * Lista personal filtrado por cargo
     * @param cargo Cargo a filtrar (ej: "Profesor", "Director")
     * @return Lista de objetos Personal con ese cargo
     */
    public List<Personal> listarPorCargo(String cargo) {
        List<Personal> listaPersonal = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE cargo = ? ORDER BY apellido, nombre";
            statement = conn.prepareStatement(sql);
            statement.setString(1, cargo);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idProfe = resultSet.getInt("id_profe");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                int dni = resultSet.getInt("dni");
                String cargoResult = resultSet.getString("cargo");

                listaPersonal.add(new Personal(idProfe, nombre, apellido, dni, cargoResult));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return listaPersonal;
    }

    // ========== ACTUALIZAR (UPDATE) ==========

    /**
     * Actualiza los datos de un personal existente
     * @param personal Objeto Personal con los datos actualizados (debe tener id_profe)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Personal personal) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "UPDATE personal SET nombre=?, apellido=?, dni=?, cargo=? WHERE id_profe=?";
            statement = conn.prepareStatement(sql);

            statement.setString(1, personal.getNombre());
            statement.setString(2, personal.getApellido());
            statement.setInt(3, personal.getDni());
            statement.setString(4, personal.getCargo());
            statement.setInt(5, personal.getId_profe());

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            cerrarRecursos(conn, statement, null);
        }
    }

    // ========== ELIMINAR (DELETE) ==========

    /**
     * Elimina un personal por su ID
     * @param id ID del personal a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "DELETE FROM personal WHERE id_profe = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } finally {
            cerrarRecursos(conn, statement, null);
        }
    }

    // ========== MÉTODO AUXILIAR ==========

    /**
     * Cierra los recursos de base de datos de forma segura
     */
    private void cerrarRecursos(Connection conn, PreparedStatement statement, ResultSet resultSet) {
        try {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (conn != null) conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
