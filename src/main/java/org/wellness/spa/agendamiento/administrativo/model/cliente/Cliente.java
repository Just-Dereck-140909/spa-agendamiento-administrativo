package main.java.org.wellness.spa.agendamiento.administrativo.model.cliente;

public class Cliente {

    private String idCliente;
    private String nombreCliente;
    private String apellidoCliente;
    private String telefonoCliente;
    private String clienteCorreoElectronico;

    public Cliente() {
    }

    public Cliente(String idCliente, String nombreCliente, String apellidoCliente,
            String telefonoCliente, String clienteCorreoElectronico) {

        this.idCliente = idCliente;
        this.nombreCliente = nombreCliente;
        this.apellidoCliente = apellidoCliente;
        this.telefonoCliente = telefonoCliente;
        this.clienteCorreoElectronico = clienteCorreoElectronico;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getApellidoCliente() {
        return apellidoCliente;
    }

    public void setApellidoCliente(String apellidoCliente) {
        this.apellidoCliente = apellidoCliente;
    }

    public String getTelefonoCliente() {
        return telefonoCliente;
    }

    public void setTelefonoCliente(String telefonoCliente) {
        this.telefonoCliente = telefonoCliente;
    }

    public String getClienteCorreoElectronico() {
        return clienteCorreoElectronico;
    }

    public void setClienteCorreoElectronico(String clienteCorreoElectronico) {
        this.clienteCorreoElectronico = clienteCorreoElectronico;
    }
}