package main.java.org.wellness.spa.agendamiento.administrativo.crud;

import java.util.List;

public interface Crud<T> {

    void save(T objeto);

    void update(T objeto);

    void deleteById(String id);

    List<T> listar();

    T buscarPorId(String id);
}