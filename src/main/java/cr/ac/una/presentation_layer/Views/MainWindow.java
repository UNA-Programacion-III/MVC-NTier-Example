package cr.ac.una.presentation_layer.Views;

import javax.swing.*;
import java.util.Dictionary;
import java.util.Enumeration;

public class MainWindow extends JFrame {
    private JPanel ContentPanel;
    private JTabbedPane MainTabPanel;

    public MainWindow() {
        setTitle("Sistema con Tabs");
        setContentPane(MainTabPanel);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    /**
     * Agrega pestañas al tab panel.
     * @param tabs Diccionario con título y contenido del tab.
     */
    public void AddTabs(Dictionary<String, JPanel> tabs) {
        Enumeration<String> keys = tabs.keys();
        while (keys.hasMoreElements()) {
            String titulo = keys.nextElement();
            JPanel contenido = tabs.get(titulo);
            MainTabPanel.addTab(titulo, contenido);
        }
    }
}
