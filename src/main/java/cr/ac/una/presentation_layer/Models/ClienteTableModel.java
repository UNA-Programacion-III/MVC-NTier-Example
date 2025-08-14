package cr.ac.una.presentation_layer.Models;

import cr.ac.una.domain_layer.Cliente;
import cr.ac.una.service_layer.IServiceObserver;
import cr.ac.una.utilities.ChangeType;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ClienteTableModel extends AbstractTableModel implements IServiceObserver<Cliente> {
    private final String[] cols = { "ID", "Nombre completo", "Teléfono", "Email", "Dirección" };
    private final Class<?>[] types = { Integer.class, String.class, String.class, String.class, String.class };

    private final List<Cliente> rows = new ArrayList<>();

    // ----- API pública -----
    public void setRows(List<Cliente> data) {
        rows.clear();
        if (data != null) rows.addAll(data);
        fireTableDataChanged();
    }

    public Cliente getAt(int row) {
        return (row >= 0 && row < rows.size()) ? rows.get(row) : null;
    }

    // ----- AbstractTableModel -----
    @Override public int getRowCount() { return rows.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int c) { return cols[c]; }
    @Override public Class<?> getColumnClass(int c) { return types[c]; }
    @Override public boolean isCellEditable(int r, int c) { return false; }

    @Override
    public Object getValueAt(int r, int c) {
        Cliente x = rows.get(r);
        switch (c) {
            case 0: return x.getId();
            case 1: return x.getNombreCompleto();
            case 2: return x.getTelefono();
            case 3: return x.getEmail();
            case 4: return x.getDireccion();
            default: return null;
        }
    }

    // ----- Observer -----
    @Override
    public void onDataChanged(ChangeType type, Cliente entity) {
        switch (type) {
            case CREATED: {
                rows.add(entity);
                int i = rows.size() - 1;
                fireTableRowsInserted(i, i);
                break;
            }
            case UPDATED: {
                int i = indexOf(entity.getId());
                if (i >= 0) {
                    rows.set(i, entity);
                    fireTableRowsUpdated(i, i);
                }
                break;
            }
            case DELETED: {
                int i = indexOf(entity.getId());
                if (i >= 0) {
                    rows.remove(i);
                    fireTableRowsDeleted(i, i);
                }
                break;
            }
        }
    }

    private int indexOf(int id) {
        for (int i = 0; i < rows.size(); i++) {
            if (rows.get(i).getId() == id) return i;
        }
        return -1;
    }
}
