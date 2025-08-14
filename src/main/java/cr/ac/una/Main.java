package cr.ac.una;

import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.presentation_layer.Controllers.ClienteController;
import cr.ac.una.presentation_layer.Models.ClienteTableModel;
import cr.ac.una.presentation_layer.Views.ClienteView;
import cr.ac.una.presentation_layer.Views.MainWindow;
import cr.ac.una.service_layer.ClienteService;
import cr.ac.una.service_layer.IService;
import cr.ac.una.utilities.FileManagement;

import javax.swing.*;
import java.util.Dictionary;
import java.util.Hashtable;


public class Main {
    public static void main(String[] args) {

        // Crear infraestructura para Clientes
        IService<Cliente> clienteService = new ClienteService(FileManagement.getClientesFileStore("clientes.xml"));
        ClienteController clienteController = new ClienteController(clienteService);
        ClienteTableModel clienteTableModel = new ClienteTableModel();
        ClienteView clienteView = new ClienteView(clienteController, clienteTableModel, clienteController.leerTodos());
        clienteService.addObserver(clienteTableModel);


        Dictionary<String, JPanel> tabs = new Hashtable<>();
        tabs.put("Clientes", clienteView.getContentPanel());

        // Inicializar Ventana principal
        MainWindow window = new MainWindow();
        window.AddTabs(tabs);
        window.setVisible(true);
    }
}