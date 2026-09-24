package main.java.org.wellness.spa.agendamiento.administrativo.model.cita;

public class OpcionComboBox {

    private String id;
    private String nombre;

    public OpcionComboBox(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }
}