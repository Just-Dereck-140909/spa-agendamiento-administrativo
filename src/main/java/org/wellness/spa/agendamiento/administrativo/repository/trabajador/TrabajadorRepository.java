package main.java.org.wellness.spa.agendamiento.administrativo.repository.trabajador;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.crud.Crud;
import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.Trabajador;
import main.java.org.wellness.spa.agendamiento.administrativo.model.trabajador.TrabajadorDetalle;

public class TrabajadorRepository implements Crud<Trabajador> {

    @Override
    public void save(Trabajador trabajador) {

        String sql = "INSERT INTO trabajador "
                + "(id_trabajador, nombre_trabajador, apellido_trabajador, "
                + "id_ocupacion, trabajador_correo_electronico) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, trabajador.getIdTrabajador());
            statement.setString(2, trabajador.getNombreTrabajador());
            statement.setString(3, trabajador.getApellidoTrabajador());
            statement.setString(4, trabajador.getIdOcupacion());
            statement.setString(5, trabajador.getTrabajadorCorreoElectronico());

            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }

    @Override
    public void update(Trabajador trabajador) {

        String sql = "UPDATE trabajador SET "
            + "nombre_trabajador = ?, "
            + "apellido_trabajador = ?, "
            + "id_ocupacion = ?, "
            + "trabajador_correo_electronico = ? "
            + "WHERE id_trabajador = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, trabajador.getNombreTrabajador());
            statement.setString(2, trabajador.getApellidoTrabajador());
            statement.setString(3, trabajador.getIdOcupacion());
            statement.setString(4, trabajador.getTrabajadorCorreoElectronico());
            statement.setString(5, trabajador.getIdTrabajador());


            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }

    @Override
    public void deleteById(String id) {

        String sql = "DELETE FROM trabajador WHERE id_trabajador = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
        }
    }

    @Override
    public List<Trabajador> listar() {

        List<Trabajador> trabajadores = new ArrayList<>();

        String sql = "SELECT * FROM trabajador";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                Trabajador trabajador = new Trabajador();

                trabajador.setIdTrabajador(
                        resultSet.getString("id_trabajador"));

                trabajador.setNombreTrabajador(
                        resultSet.getString("nombre_trabajador"));

                trabajador.setApellidoTrabajador(
                        resultSet.getString("apellido_trabajador"));

                trabajador.setIdOcupacion(
                        resultSet.getString("id_ocupacion"));

                trabajador.setTrabajadorCorreoElectronico(
                        resultSet.getString("trabajador_correo_electronico"));

                trabajadores.add(trabajador);
            }

        } catch (SQLException e) {
        }

        return trabajadores;
    }

    @Override
    public Trabajador buscarPorId(String id) {

        String sql = "SELECT * FROM trabajador WHERE id_trabajador = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    Trabajador trabajador = new Trabajador();

                    trabajador.setIdTrabajador(
                            resultSet.getString("id_trabajador"));

                    trabajador.setNombreTrabajador(
                            resultSet.getString("nombre_trabajador"));

                    trabajador.setApellidoTrabajador(
                            resultSet.getString("apellido_trabajador"));

                    trabajador.setIdOcupacion(
                            resultSet.getString("id_ocupacion"));

                    trabajador.setTrabajadorCorreoElectronico(
                            resultSet.getString("trabajador_correo_electronico"));

                    return trabajador;
                }
            }

        } catch (SQLException e) {
        }

        return null;
    }

    public String obtenerUltimoId() {

        String sql = "SELECT id_trabajador "
                + "FROM trabajador "
                + "ORDER BY id_trabajador DESC "
                + "LIMIT 1";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getString("id_trabajador");
            }

        } catch (SQLException e) {
        }

        return null;
    }

    public List<TrabajadorDetalle> listarTrabajadoresDetalle() {

        List<TrabajadorDetalle> trabajadores = new ArrayList<>();

        String sql = "SELECT "
                + "t.id_trabajador, "
                + "t.nombre_trabajador, "
                + "t.apellido_trabajador, "
                + "o.nombre_ocupacion AS ocupacion, "
                + "t.trabajador_correo_electronico "
                + "FROM trabajador t "
                + "INNER JOIN ocupacion o "
                + "ON t.id_ocupacion = o.id_ocupacion "
                + "ORDER BY t.nombre_trabajador, t.apellido_trabajador";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {

                TrabajadorDetalle trabajador = new TrabajadorDetalle();

                trabajador.setIdTrabajador(
                        resultSet.getString("id_trabajador"));

                trabajador.setNombreTrabajador(
                        resultSet.getString("nombre_trabajador"));

                trabajador.setApellidoTrabajador(
                        resultSet.getString("apellido_trabajador"));

                trabajador.setOcupacion(
                        resultSet.getString("ocupacion"));

                trabajador.setTrabajadorCorreoElectronico(
                        resultSet.getString("trabajador_correo_electronico"));

                trabajadores.add(trabajador);
            }

        } catch (SQLException e) {
        }

        return trabajadores;
    }
    
    public boolean tieneCitasAsociadas(String idTrabajador) {

        String sql = "SELECT COUNT(*) "
                + "FROM cita "
                + "WHERE id_trabajador = ?";

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
                PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, idTrabajador);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return resultSet.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
        }

        return false;
    }
}