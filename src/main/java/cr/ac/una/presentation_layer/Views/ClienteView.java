package cr.ac.una.presentation_layer.Views;

import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.presentation_layer.Controllers.ClienteController;
import cr.ac.una.presentation_layer.Models.ClienteTableModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import java.util.List;

public class ClienteView {

    // Componentes generados
    private JPanel ContentPanel;
    private JPanel FormPanel;
    private JPanel ActionPanel;
    private JPanel LeftFormPanel;
    private JPanel RightFormPanel;
    private JPanel IdPanel;
    private JPanel IdLabelPanel;
    private JPanel IdTextFieldPanel;
    private JTextField IdTextFIeld;
    private JPanel NamePanel;
    private JPanel NameLabelPanel;
    private JPanel NameTextFieldPanel;
    private JLabel NameLabel;
    private JTextField textField1;
    private JPanel PhoneLabelPanel;
    private JPanel PhonePanel;
    private JPanel PhoneTextFieldPanel;
    private JTextField PhoneTextField;
    private JPanel EmailPanel;
    private JPanel AddressPanel;
    private JPanel EmailLabelPanel;
    private JPanel EmailTextFieldPanel;
    private JLabel EmailLabel;
    private JTextField LabelTextField;
    private JPanel AdressLabelPanel;
    private JPanel AdressTextFieldPanel;
    private JTextField EmailTextField;
    private JTable table1;
    private JButton ClearButton;
    private JButton borrarButton;
    private JButton UpdateButton;
    private JButton AddButton;
    private JLabel AddressTextField;
    private JScrollPane ClienteScroll;

    // Dependencias
    private ClienteController controller;
    private ClienteTableModel tableModel;


    ClienteView() {
        addListeners();
    }

    public ClienteView(ClienteController controller, ClienteTableModel model, List<Cliente> datosIniciales) {
        this.controller = controller;
        this.tableModel = model;
        addListeners();
        bind(controller, model, datosIniciales);
    }

    private void addListeners() {
        AddButton.addActionListener(e -> onAdd());
        UpdateButton.addActionListener(e -> onUpdate());
        borrarButton.addActionListener(e -> onDelete());
        ClearButton.addActionListener(e -> onClear());
        table1.getSelectionModel().addListSelectionListener(this::onTableSelection);
    }

    public void bind(ClienteController controller, ClienteTableModel model, List<Cliente> datosIniciales) {
        this.controller = controller;
        this.tableModel = model;
        table1.setModel(tableModel);
        if (datosIniciales != null) tableModel.setRows(datosIniciales);
        IdTextFIeld.requestFocus();
    }

    public JPanel getContentPanel() {
        return ContentPanel;
    }

    // Handle de las operaciones
    private void onAdd() {
        try {
            requireBound();
            DatosForm d = readForm();
            controller.agregar(d.id, d.nombre, d.tel, d.email, d.dir);
            onClear();
        } catch (Exception ex) {
            showError("Error al agregar: " + ex.getMessage(), ex);
        }
    }

    private void onUpdate() {
        try {
            requireBound();
            DatosForm d = readForm();
            controller.actualizar(d.id, d.nombre, d.tel, d.email, d.dir);
            onClear();
        } catch (Exception ex) {
            showError("Error al actualizar: " + ex.getMessage(), ex);
        }
    }

    private void onDelete() {
        try {
            requireBound();
            Integer id = parseInt(IdTextFIeld.getText());
            if (id == null || id <= 0) { warn("ID inválido."); return; }
            int op = JOptionPane.showConfirmDialog(ContentPanel,
                    "¿Eliminar cliente con ID " + id + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                controller.borrar(id);
                onClear();
            }
        } catch (Exception ex) {
            showError("Error al borrar: " + ex.getMessage(), ex);
        }
    }

    private void onClear() {
        table1.clearSelection();
        IdTextFIeld.setText("");
        textField1.setText("");
        PhoneTextField.setText("");
        EmailTextField.setText("");
        LabelTextField.setText("");
        IdTextFIeld.requestFocus();
    }

    private void onTableSelection(ListSelectionEvent e) {
        if (e.getValueIsAdjusting()) return;
        if (tableModel == null) return;
        int row = table1.getSelectedRow();
        if (row < 0) return;
        Cliente c = tableModel.getAt(row);
        if (c == null) return;

        IdTextFIeld.setText(String.valueOf(c.getId()));
        textField1.setText(c.getNombreCompleto());
        PhoneTextField.setText(c.getTelefono());
        EmailTextField.setText(c.getEmail());
        LabelTextField.setText(c.getDireccion());
    }

    // ====== Lectura/validación de formulario ======
    private static class DatosForm {
        int id; String nombre; String tel; String email; String dir;
    }

    private DatosForm readForm() {
        DatosForm d = new DatosForm();
        d.id    = orDefault(parseInt(IdTextFIeld.getText()), -1);
        d.nombre= safe(textField1.getText());
        d.tel   = safe(PhoneTextField.getText());
        d.email = safe(EmailTextField.getText());
        d.dir   = safe(LabelTextField.getText());

        if (d.id <= 0) throw new IllegalArgumentException("El ID debe ser mayor que 0.");
        if (d.nombre.isEmpty()) throw new IllegalArgumentException("El nombre es obligatorio.");
        return d;
    }

    // ====== Helpers ======
    private void requireBound() {
        if (controller == null || tableModel == null)
            throw new IllegalStateException("ClienteView no está enlazado (bind) a controller/model.");
    }

    private Integer parseInt(String s) {
        try { return (s == null || s.trim().isEmpty()) ? null : Integer.parseInt(s.trim()); }
        catch (Exception e) { return null; }
    }
    private int orDefault(Integer v, int def) { return v == null ? def : v; }
    private String safe(String s) { return s == null ? "" : s.trim(); }

    private void showError(String msg, Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(ContentPanel, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
    private void warn(String msg) {
        JOptionPane.showMessageDialog(ContentPanel, msg, "Atención", JOptionPane.WARNING_MESSAGE);
    }
}
