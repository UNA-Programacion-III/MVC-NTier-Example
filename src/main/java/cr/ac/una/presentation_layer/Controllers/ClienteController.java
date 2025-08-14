package cr.ac.una.presentation_layer.Controllers;

import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.service_layer.IService;

import java.util.List;

public class ClienteController {

    private final IService<Cliente> service;

    public ClienteController(IService<Cliente> service) {
        this.service = service;
    }

    // C – Agregar
    public void agregar(int id, String nombreCompleto, String telefono, String email, String direccion) {
        validarId(id);
        validarNombre(nombreCompleto);
        Cliente c = new Cliente(id, nombreCompleto, telefono, email, direccion);
        service.agregar(c);
    }

    // U – Actualizar
    public void actualizar(int id, String nombreCompleto, String telefono, String email, String direccion) {
        validarId(id);
        validarNombre(nombreCompleto);
        Cliente c = new Cliente(id, nombreCompleto, telefono, email, direccion);
        service.actualizar(c);
    }

    // D – Borrar
    public void borrar(int id) {
        validarId(id);
        service.borrar(id);
    }

    // R – Leer
    public List<Cliente> leerTodos() {
        return service.leerTodos();
    }

    public Cliente leerPorId(int id) {
        validarId(id);
        return service.leerPorId(id);
    }

    // --- Validaciones mínimas ---
    private void validarId(int id) {
        if (id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre es obligatorio.");
    }
}