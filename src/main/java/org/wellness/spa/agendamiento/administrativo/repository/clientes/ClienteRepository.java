package main.java.org.wellness.spa.agendamiento.administrativo.repository.clientes;

import main.java.org.wellness.spa.agendamiento.administrativo.model.cliente.Cliente;
import main.java.org.wellness.spa.agendamiento.administrativo.config.DataBaseConnection;
import main.java.org.wellness.spa.agendamiento.administrativo.crud.Crud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ClienteRepository implements Crud<Cliente> {

    @Override
    public void save(Cliente cliente) {
        String sql = "INSERT INTO cliente (id_cliente, nombre_cliente, apellido_cliente, telefono_cliente, cliente_correo_electronico) VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNombreCliente());
            ps.setString(3, cliente.getApellidoCliente());
            ps.setString(4, cliente.getTelefonoCliente());
            ps.setString(5, cliente.getClienteCorreoElectronico());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(Cliente cliente) {
        String sql = "UPDATE cliente SET nombre_cliente = ?, apellido_cliente = ?, telefono_cliente = ?, cliente_correo_electronico = ? WHERE id_cliente = ?";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombreCliente());
            ps.setString(2, cliente.getApellidoCliente());
            ps.setString(3, cliente.getTelefonoCliente());
            ps.setString(4, cliente.getClienteCorreoElectronico());
            ps.setString(5, cliente.getIdCliente());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteById(String id) {
        String sql = "DELETE FROM cliente WHERE id_cliente = ?";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY id_cliente";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                clientes.add(mapearCliente(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return clientes;
    }

    @Override
    public Cliente buscarPorId(String id) {
        String sql = "SELECT * FROM cliente WHERE id_cliente = ?";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearCliente(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public String obtenerUltimoId() {
        String sql = "SELECT id_cliente FROM cliente ORDER BY id_cliente DESC LIMIT 1";

        try (Connection conexion = DataBaseConnection.getConnectionDataBase();
             Statement st = conexion.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getString("id_cliente");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Cliente mapearCliente(ResultSet rs) throws SQLException {
        return new Cliente(
                rs.getString("id_cliente"),
                rs.getString("nombre_cliente"),
                rs.getString("apellido_cliente"),
                rs.getString("telefono_cliente"),
                rs.getString("cliente_correo_electronico")
        );
    }
}