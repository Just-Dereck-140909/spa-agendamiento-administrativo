package main.java.org.wellness.spa.agendamiento.administrativo.repository.cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.CitaResumen;

public class CitaRepository {

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
}