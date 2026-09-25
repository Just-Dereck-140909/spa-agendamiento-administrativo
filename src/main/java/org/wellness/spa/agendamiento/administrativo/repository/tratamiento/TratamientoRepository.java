package main.java.org.wellness.spa.agendamiento.administrativo.repository.tratamiento;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.crud.Crud;
import main.java.org.wellness.spa.agendamiento.administrativo.model.tratamiento.Tratamiento;

public class TratamientoRepository implements Crud<Tratamiento> {

    @Override
    public void save(Tratamiento tratamiento) {

        String sql = """
                INSERT INTO tratamiento (
                    id_tratamiento,
                    nombre_tratamiento,
                    costo_tratamiento,
                    descripcion_tratamiento,
                    duracion_tratamiento
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tratamiento.getIdTratamiento());
            statement.setString(2, tratamiento.getNombreTratamiento());
            statement.setBigDecimal(3, tratamiento.getCostoTratamiento());
            statement.setString(4, tratamiento.getDescripcionTratamiento());
            statement.setInt(5, tratamiento.getDuracionTratamiento());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar el tratamiento: " + e.getMessage());
        }
    }

    @Override
    public void update(Tratamiento tratamiento) {

        String sql = """
                UPDATE tratamiento
                SET nombre_tratamiento = ?,
                    costo_tratamiento = ?,
                    descripcion_tratamiento = ?,
                    duracion_tratamiento = ?
                WHERE id_tratamiento = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, tratamiento.getNombreTratamiento());
            statement.setBigDecimal(2, tratamiento.getCostoTratamiento());
            statement.setString(3, tratamiento.getDescripcionTratamiento());
            statement.setInt(4, tratamiento.getDuracionTratamiento());
            statement.setString(5, tratamiento.getIdTratamiento());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar el tratamiento: " + e.getMessage());
        }
    }

    @Override
    public void deleteById(String id) {

        String sql = """
                DELETE FROM tratamiento
                WHERE id_tratamiento = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar el tratamiento: " + e.getMessage());
        }
    }

    @Override
    public List<Tratamiento> listar() {

        List<Tratamiento> lista = new ArrayList<>();

        String sql = """
                SELECT
                    id_tratamiento,
                    nombre_tratamiento,
                    costo_tratamiento,
                    descripcion_tratamiento,
                    duracion_tratamiento
                FROM tratamiento
                ORDER BY id_tratamiento
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Tratamiento tratamiento = new Tratamiento(
                        resultSet.getString("id_tratamiento"),
                        resultSet.getString("nombre_tratamiento"),
                        resultSet.getBigDecimal("costo_tratamiento"),
                        resultSet.getString("descripcion_tratamiento"),
                        resultSet.getInt("duracion_tratamiento")
                );

                lista.add(tratamiento);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar los tratamientos: " + e.getMessage());
        }

        return lista;
    }

    
    @Override
    public Tratamiento buscarPorId(String id) {

        String sql = """
                SELECT
                    id_tratamiento,
                    nombre_tratamiento,
                    costo_tratamiento,
                    descripcion_tratamiento,
                    duracion_tratamiento
                FROM tratamiento
                WHERE id_tratamiento = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Tratamiento(
                            resultSet.getString("id_tratamiento"),
                            resultSet.getString("nombre_tratamiento"),
                            resultSet.getBigDecimal("costo_tratamiento"),
                            resultSet.getString("descripcion_tratamiento"),
                            resultSet.getInt("duracion_tratamiento")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el tratamiento: " + e.getMessage());
        }

        return null;
    }

    public String obtenerUltimoId() {

        String sql = """
                SELECT id_tratamiento
                FROM tratamiento
                ORDER BY id_tratamiento DESC
                LIMIT 1
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id_tratamiento");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el último ID de tratamiento: " + e.getMessage());
        }

        return null;
    }
    
    
    public boolean tieneCitasAsociadas(String idTratamiento) {

        String sql = "SELECT COUNT(*) "
                + "FROM cita "
                + "WHERE id_tratamiento = ?";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, idTratamiento);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}