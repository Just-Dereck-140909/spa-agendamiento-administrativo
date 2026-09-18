package main.java.org.wellness.spa.agendamiento.administrativo.repository.recepcionista;

import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.model.recepcionista.Recepcionista;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RecepcionistaRepository {

    public Recepcionista buscarPorUsuario(String usuario) {

        String sql = """
                SELECT id_recepcionista,
                       usuario_recepcionista,
                       nombre_recepcionista,
                       apellido_recepcionista,
                       contrasena_hash
                FROM recepcionista
                WHERE usuario_recepcionista = ?
                """;

        try (Connection connection = DataBaseConnection.getConnectionDataBase();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, usuario);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {

                    return new Recepcionista(
                            resultSet.getString("id_recepcionista"),
                            resultSet.getString("usuario_recepcionista"),
                            resultSet.getString("nombre_recepcionista"),
                            resultSet.getString("apellido_recepcionista"),
                            resultSet.getString("contrasena_hash")
                    );
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar el recepcionista: " + e.getMessage());
        }

        return null;
    }
}