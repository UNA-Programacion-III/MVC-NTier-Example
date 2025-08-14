package cr.ac.una.service_layer;

import cr.ac.una.data_access_layer.IFileStore;
import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.utilities.ChangeType;

import java.util.ArrayList;
import java.util.List;

public class ClienteService implements IService<Cliente> {

    private final IFileStore<Cliente> fileStore;
    private final List<IServiceObserver<Cliente>> listeners = new ArrayList<>();

    public ClienteService(IFileStore<Cliente> fileStore) {
        this.fileStore = fileStore;
    }

    @Override
    public void agregar(Cliente cliente) {
        List<Cliente> clientes = fileStore.readAll();
        clientes.add(cliente);
        fileStore.writeAll(clientes);
        notifyObservers(ChangeType.CREATED, cliente);
    }

    @Override
    public void borrar(int id) {
        List<Cliente> clientes = fileStore.readAll();
        Cliente removed = null;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == id) { removed = clientes.remove(i); break; }
        }
        fileStore.writeAll(clientes);
        if (removed != null) notifyObservers(ChangeType.DELETED, removed);
    }

    @Override
    public void actualizar(Cliente cliente) {
        List<Cliente> clientes = fileStore.readAll();
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getId() == cliente.getId()) {
                clientes.set(i, cliente);
                break;
            }
        }
        fileStore.writeAll(clientes);
        notifyObservers(ChangeType.UPDATED, cliente);
    }

    @Override
    public List<Cliente> leerTodos() {
        return fileStore.readAll();
    }

    @Override
    public Cliente leerPorId(int id) {
        return fileStore.readAll()
                .stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElse(null);
    }


    @Override public void addObserver(IServiceObserver<Cliente> l) { if (l!=null) listeners.add(l); }

    private void notifyObservers(ChangeType type, Cliente entity) {
        for (IServiceObserver<Cliente> l : listeners) l.onDataChanged(type, entity);
    }
}