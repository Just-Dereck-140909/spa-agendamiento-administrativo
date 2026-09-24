package main.java.org.wellness.spa.agendamiento.administrativo.service.trabajador;
import java.util.List;
import main.java.org.wellness.spa.agendamiento.administrativo.model.ocupacion.Ocupacion;
import main.java.org.wellness.spa.agendamiento.administrativo.repository.trabajador.TrabajadorFormRepository;

public class TrabajadorFormService {

    private final TrabajadorFormRepository repository;

    public TrabajadorFormService() {
        repository = new TrabajadorFormRepository();
    }

    public List<Ocupacion> listarOcupaciones() {
        return repository.listarOcupaciones();
    }
}