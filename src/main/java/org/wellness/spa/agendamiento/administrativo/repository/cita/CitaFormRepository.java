package main.java.org.wellness.spa.agendamiento.administrativo.repository.cita;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.model.cita.OpcionComboBox;

public class CitaFormRepository {

    //Metodos para conseguir datos para el ComboBox del CRUD de citas
    public List<OpcionComboBox> listarClientes() {

        List<OpcionComboBox> lista = new ArrayList<>();

        String sql = """
                SELECT
                    id_cliente,
                    CONCAT(nombre_cliente, ' ', apellido_cliente) AS nombre
                FROM cliente
                ORDER BY nombre_cliente, apellido_cliente
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                lista.add(
                        new OpcionComboBox(
                                resultSet.getString("id_cliente"),
                                resultSet.getString("nombre")
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes para citas: " + e.getMessage());
        }

        return lista;
    }

    public List<OpcionComboBox> listarTrabajadores() {

        List<OpcionComboBox> lista = new ArrayList<>();

        String sql = """
                SELECT
                    id_trabajador,
                    CONCAT(nombre_trabajador, ' ', apellido_trabajador) AS nombre
                FROM trabajador
                ORDER BY nombre_trabajador, apellido_trabajador
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                lista.add(
                        new OpcionComboBox(
                                resultSet.getString("id_trabajador"),
                                resultSet.getString("nombre")
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar trabajadores para citas: " + e.getMessage());
        }

        return lista;
    }

    public List<OpcionComboBox> listarTratamientos() {

        List<OpcionComboBox> lista = new ArrayList<>();

        String sql = """
                SELECT
                    id_tratamiento,
                    nombre_tratamiento
                FROM tratamiento
                ORDER BY nombre_tratamiento
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                lista.add(
                        new OpcionComboBox(
                                resultSet.getString("id_tratamiento"),
                                resultSet.getString("nombre_tratamiento")
                        )
                );
            }

        } catch (SQLException e) {
            System.out.println("Error al listar tratamientos para citas: " + e.getMessage());
        }

        return lista;
    }
}