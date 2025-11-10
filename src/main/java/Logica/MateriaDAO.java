package Logica;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MateriaDAO {

    /**
     * Guarda un nuevo registro de personal en la base de datos
     *
     * @param materia Objeto Personal con los datos a guardar
     * @return true si se guardó correctamente, false si hubo error
     */
    public boolean guardar(Materia materia) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "INSERT INTO materia (nombre_materia, cantidad_modulos, grupo, orientacion) VALUES (?, ?, ?, ?)";
            statement = conn.prepareStatement(sql);

            statement.setString(1, materia.getNombreMateria());
            statement.setInt(2, materia.getCantidadModulos());
            statement.setInt(3, materia.getGrupo());
            statement.setString(4, materia.getOrientacion());

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
     *
     * @param id id de las materias a buscar
     * @return Objeto Personal si lo encuentra, null si no existe
     */
    public Materia buscarPorId(int id) {
        Materia materia = null;
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE id_materia = ?";
            statement = conn.prepareStatement(sql);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int idMateria = resultSet.getInt("id_materia");
                String nombreMateria = resultSet.getString("nombre_materia");
                int grupo = resultSet.getInt("grupo");
                int cantidadModulos = resultSet.getInt("cantidad_modulos");
                String orientacion = resultSet.getString("cargo");

                materia = new Materia(idMateria, nombreMateria, grupo, cantidadModulos, orientacion);
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return materia;
    }

    /**
     * Lista todas las materias registradas
     *
     * @return Lista de objetos Materia
     */
    public List<Materia> listarTodos() {
        List<Materia> listaMaterias = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM materias ";
            statement = conn.prepareStatement(sql);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idMateria = resultSet.getInt("id_materia");
                String nombreMateria = resultSet.getString("nombre_materia");
                int grupo = resultSet.getInt("grupo");
                int cantidadModulos = resultSet.getInt("cantidad_modulos");
                String orientacion = resultSet.getString("cargo");


                listaMaterias.add(new Materia(idMateria, nombreMateria, grupo, cantidadModulos, orientacion));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return listaMaterias;
    }

    /**
     * Lista materias filtrado por orientacion
     *
     * @param orientacion Cargo a filtrar (ej: "Profesor", "Director")
     * @return Lista de objetos Personal con ese cargo
     */
    public List<Materia> listarPorOrientacion(String orientacion) {
        List<Materia> listaMaterias = new ArrayList<>();
        Connection conn = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            conn = Conexion.conectar();
            String sql = "SELECT * FROM personal WHERE orientacion = ? ";
            statement = conn.prepareStatement(sql);
            statement.setString(1, orientacion);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int idMateria = resultSet.getInt("id_materia");
                String nombreMateria = resultSet.getString("nombre_materia");
                int grupo = resultSet.getInt("grupo");
                int cantidadModulos = resultSet.getInt("cantidad_modulos");


                listaMaterias.add(new Materia(idMateria, nombreMateria, grupo, cantidadModulos, orientacion));
            }

        } catch (SQLException ex) {
            Logger.getLogger(PersonalDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            cerrarRecursos(conn, statement, resultSet);
        }

        return listaMaterias;
    }

    // ========== ACTUALIZAR (UPDATE) ==========

    /**
     * Actualiza los datos de un personal existente
     *
     * @param materia Objeto Personal con los datos actualizados (debe tener id_personal)
     * @return true si se actualizó correctamente, false si hubo error
     */
    public boolean actualizar(Materia materia) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "UPDATE personal SET nombre_materia=?, cantidad_modulos=?, grupo=?, orientacion=? WHERE id_personal=?";
            statement = conn.prepareStatement(sql);

            statement.setString(1, materia.getNombreMateria());
            statement.setInt(2, materia.getCantidadModulos());
            statement.setInt(3, materia.getGrupo());
            statement.setString(4, materia.getOrientacion());
            statement.setInt(5, materia.getIdMateria());

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
     *
     * @param id ID del personal a eliminar
     * @return true si se eliminó correctamente, false si hubo error
     */
    public boolean eliminar(int id) {
        Connection conn = null;
        PreparedStatement statement = null;

        try {
            conn = Conexion.conectar();
            String sql = "DELETE FROM materias WHERE id_materia = ?";
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
