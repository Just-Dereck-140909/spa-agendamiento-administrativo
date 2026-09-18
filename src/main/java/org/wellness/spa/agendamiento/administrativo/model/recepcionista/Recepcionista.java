package main.java.org.wellness.spa.agendamiento.administrativo.model.recepcionista;

public class Recepcionista {

    private String idRecepcionista;
    private String usuarioRecepcionista;
    private String nombreRecepcionista;
    private String apellidoRecepcionista;
    private String contrasenaHash;
    
    public Recepcionista(){
    }

    public Recepcionista(String idRecepcionista, String usuarioRecepcionista, String nombreRecepcionista, String apellidoRecepcionista, String contrasenaHash) {
        this.idRecepcionista = idRecepcionista;
        this.usuarioRecepcionista = usuarioRecepcionista;
        this.nombreRecepcionista = nombreRecepcionista;
        this.apellidoRecepcionista = apellidoRecepcionista;
        this.contrasenaHash = contrasenaHash;
    }

    public String getIdRecepcionista() {
        return idRecepcionista;
    }

    public void setIdRecepcionista(String idRecepcionista) {
        this.idRecepcionista = idRecepcionista;
    }

    public String getUsuarioRecepcionista() {
        return usuarioRecepcionista;
    }

    public void setUsuarioRecepcionista(String usuarioRecepcionista) {
        this.usuarioRecepcionista = usuarioRecepcionista;
    }

    public String getNombreRecepcionista() {
        return nombreRecepcionista;
    }

    public void setNombreRecepcionista(String nombreRecepcionista) {
        this.nombreRecepcionista = nombreRecepcionista;
    }

    public String getApellidoRecepcionista() {
        return apellidoRecepcionista;
    }

    public void setApellidoRecepcionista(String apellidoRecepcionista) {
        this.apellidoRecepcionista = apellidoRecepcionista;
    }

    public String getContrasenaHash() {
        return contrasenaHash;
    }

    public void setContrasenaHash(String contrasenaHash) {
        this.contrasenaHash = contrasenaHash;
    }
    
    

}
