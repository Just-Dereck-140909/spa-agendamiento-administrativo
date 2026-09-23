package main.java.org.wellness.spa.agendamiento.administrativo.repository.cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaResumen;
import main.java.org.wellness.spa.agendamiento.administrativo.crud.Crud;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.Cita;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaDetalle;

public class CitaRepository implements Crud<Cita> {
    
    
    @Override
    public void save(Cita objeto) {

        String sql = """
                INSERT INTO cita (
                    id_cita,
                    fecha_cita,
                    hora_cita,
                    estado_cita,
                    id_cliente,
                    id_trabajador,
                    id_tratamiento
                )
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, objeto.getIdCita());
            statement.setDate(2, java.sql.Date.valueOf(objeto.getFechaCita()));
            statement.setTime(3, java.sql.Time.valueOf(objeto.getHoraCita()));
            statement.setString(4, objeto.getEstadoCita());
            statement.setString(5, objeto.getIdCliente());
            statement.setString(6, objeto.getIdTrabajador());
            statement.setString(7, objeto.getIdTratamiento());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al guardar la cita: " + e.getMessage());
        }
    }

    

    @Override
    public void update(Cita objeto) {

        String sql = """
                UPDATE cita
                SET fecha_cita = ?,
                    hora_cita = ?,
                    estado_cita = ?,
                    id_cliente = ?,
                    id_trabajador = ?,
                    id_tratamiento = ?
                WHERE id_cita = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDate(1, java.sql.Date.valueOf(objeto.getFechaCita()));
            statement.setTime(2, java.sql.Time.valueOf(objeto.getHoraCita()));
            statement.setString(3, objeto.getEstadoCita());
            statement.setString(4, objeto.getIdCliente());
            statement.setString(5, objeto.getIdTrabajador());
            statement.setString(6, objeto.getIdTratamiento());
            statement.setString(7, objeto.getIdCita());

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al actualizar la cita: " + e.getMessage());
        }
    }
    
    
    
    @Override
    public void deleteById(String id) {

        String sql = """
                DELETE FROM cita
                WHERE id_cita = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error al eliminar la cita: " + e.getMessage());
        }
    }
    
    
    @Override
    public List<Cita> listar() {

        List<Cita> lista = new ArrayList<>();

        String sql = """
                SELECT
                    id_cita,
                    fecha_cita,
                    hora_cita,
                    estado_cita,
                    id_cliente,
                    id_trabajador,
                    id_tratamiento
                FROM cita
                ORDER BY fecha_cita, hora_cita
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Cita cita = new Cita(
                        resultSet.getString("id_cita"),
                        resultSet.getDate("fecha_cita").toLocalDate(),
                        resultSet.getTime("hora_cita").toLocalTime(),
                        resultSet.getString("estado_cita"),
                        resultSet.getString("id_cliente"),
                        resultSet.getString("id_trabajador"),
                        resultSet.getString("id_tratamiento")
                );

                lista.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar las citas: " + e.getMessage());
        }

        return lista;
    }
    
    
    @Override
    public Cita buscarPorId(String id) {

        String sql = """
                SELECT
                    id_cita,
                    fecha_cita,
                    hora_cita,
                    estado_cita,
                    id_cliente,
                    id_trabajador,
                    id_tratamiento
                FROM cita
                WHERE id_cita = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Cita(
                            resultSet.getString("id_cita"),
                            resultSet.getDate("fecha_cita").toLocalDate(),
                            resultSet.getTime("hora_cita").toLocalTime(),
                            resultSet.getString("estado_cita"),
                            resultSet.getString("id_cliente"),
                            resultSet.getString("id_trabajador"),
                            resultSet.getString("id_tratamiento")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar la cita: " + e.getMessage());
        }

        return null;
    }

    
    //metodo para obtener el ultimo id para la generacion automatica
    public String obtenerUltimoId() {

        String sql = """
                SELECT id_cita
                FROM cita
                ORDER BY id_cita DESC
                LIMIT 1
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id_cita");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener el último ID de cita: " + e.getMessage());
        }

        return null;
    }

    
    //Metodo para obtener la lista de citas para el menu principal
    public List<CitaResumen> listarCitasResumen() {

        List<CitaResumen> lista = new ArrayList<>();

        String sql = """
                SELECT
                    cl.nombre_cliente,
                    cl.apellido_cliente,
                    CONCAT(t.nombre_trabajador, ' ', t.apellido_trabajador) AS trabajador,
                    tr.nombre_tratamiento AS tratamiento,
                    tr.descripcion_tratamiento,
                    c.hora_cita AS hora,
                    c.fecha_cita AS fecha,
                    c.estado_cita AS estado
                FROM cita c
                INNER JOIN cliente cl
                    ON c.id_cliente = cl.id_cliente
                INNER JOIN trabajador t
                    ON c.id_trabajador = t.id_trabajador
                INNER JOIN tratamiento tr
                    ON c.id_tratamiento = tr.id_tratamiento
                ORDER BY c.fecha_cita, c.hora_cita
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                CitaResumen cita = new CitaResumen(
                        resultSet.getString("nombre_cliente"),
                        resultSet.getString("apellido_cliente"),
                        resultSet.getString("trabajador"),
                        resultSet.getString("tratamiento"),
                        resultSet.getString("descripcion_tratamiento"),
                        resultSet.getTime("hora").toLocalTime(),
                        resultSet.getDate("fecha").toLocalDate(),
                        resultSet.getString("estado")
                );

                lista.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar el resumen de citas: " + e.getMessage());
        }

        return lista;
    }
    
    
    //Metodo para obtener la lista de citas para el CRUD
    public List<CitaDetalle> listarCitasDetalle() {

        List<CitaDetalle> lista = new ArrayList<>();

        String sql = """
                SELECT
                    c.id_cita,
                    CONCAT(cl.nombre_cliente, ' ', cl.apellido_cliente) AS cliente,
                    CONCAT(t.nombre_trabajador, ' ', t.apellido_trabajador) AS trabajador,
                    tr.nombre_tratamiento AS tratamiento,
                    c.fecha_cita,
                    c.hora_cita,
                    c.estado_cita
                FROM cita c
                INNER JOIN cliente cl
                    ON c.id_cliente = cl.id_cliente
                INNER JOIN trabajador t
                    ON c.id_trabajador = t.id_trabajador
                INNER JOIN tratamiento tr
                    ON c.id_tratamiento = tr.id_tratamiento
                ORDER BY c.fecha_cita, c.hora_cita
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                CitaDetalle cita = new CitaDetalle(
                        resultSet.getString("id_cita"),
                        resultSet.getString("cliente"),
                        resultSet.getString("trabajador"),
                        resultSet.getString("tratamiento"),
                        resultSet.getDate("fecha_cita").toLocalDate(),
                        resultSet.getTime("hora_cita").toLocalTime(),
                        resultSet.getString("estado_cita")
                );

                lista.add(cita);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar el detalle de citas: " + e.getMessage());
        }

        return lista;
    }
    
}