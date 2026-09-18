package main.java.org.wellness.spa.agendamiento.administrativo.service.recepcionista;

import main.java.org.wellness.spa.agendamiento.administrativo.model.recepcionista.Recepcionista;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.recepcionista.RecepcionistaRepository;
import org.mindrot.jbcrypt.BCrypt;

public class RecepcionistaService {

    private final RecepcionistaRepository recepcionistaRepository;

    public RecepcionistaService() {
        recepcionistaRepository = new RecepcionistaRepository();
    }

    public Recepcionista iniciarSesion(String usuario, String contrasena) {

        if (usuario == null || usuario.isBlank()) {
            return null;
        }

        if (contrasena == null || contrasena.isBlank()) {
            return null;
        }

        Recepcionista recepcionista =
                recepcionistaRepository.buscarPorUsuario(usuario);

        if (recepcionista == null) {
            return null;
        }

        boolean contrasenaCorrecta = BCrypt.checkpw(
                contrasena,
                recepcionista.getContrasenaHash()
        );

        if (contrasenaCorrecta) {
            return recepcionista;
        }

        return null;
    }
}
