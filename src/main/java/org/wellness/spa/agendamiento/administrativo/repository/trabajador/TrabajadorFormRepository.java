package main.java.org.wellness.spa.agendamiento.administrativo.repository.trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.model.ocupacion.Ocupacion;

public class TrabajadorFormRepository {

    public List<Ocupacion> listarOcupaciones() {

        List<Ocupacion> ocupaciones = new ArrayList<>();

        String sql = "SELECT id_ocupacion, nombre_ocupacion "
                + "FROM ocupacion "
                + "ORDER BY nombre_ocupacion";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Ocupacion ocupacion = new Ocupacion();

                ocupacion.setIdOcupacion(
                        resultSet.getString("id_ocupacion"));

                ocupacion.setNombreOcupacion(
                        resultSet.getString("nombre_ocupacion"));

                ocupaciones.add(ocupacion);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar ocupaciones: "
                    + e.getMessage());
        }

        return ocupaciones;
    }
}