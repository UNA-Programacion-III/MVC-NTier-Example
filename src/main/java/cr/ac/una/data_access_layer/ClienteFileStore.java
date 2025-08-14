package cr.ac.una.data_access_layer;

import cr.ac.una.domain_layer.Cliente;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import javax.xml.stream.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ClienteFileStore implements IFileStore<Cliente>{
    private final File xmlFile;

    public ClienteFileStore(File xmlFile) {
        this.xmlFile = xmlFile;
        ensureFile();
    }

    @Override
    public List<Cliente> readAll() {
        List<Cliente> out = new ArrayList<>();
        if (xmlFile.length() == 0) return out;

        try (FileInputStream in = new FileInputStream(xmlFile)) {
            JAXBContext ctx = JAXBContext.newInstance(Cliente.class);
            Unmarshaller u = ctx.createUnmarshaller();
            XMLInputFactory xif = XMLInputFactory.newFactory();
            XMLStreamReader xr = xif.createXMLStreamReader(in);

            while (xr.hasNext()) {
                int ev = xr.next();
                if (ev == XMLStreamConstants.START_ELEMENT && "cliente".equals(xr.getLocalName())) {
                    // Unmarshal del elemento actual
                    Cliente c = u.unmarshal(xr, Cliente.class).getValue();
                    out.add(c);
                }
            }
            xr.close();
        } catch (Exception ex) {
            System.err.println("[WARN] Error leyendo " + xmlFile + ": " + ex.getMessage());
            ex.printStackTrace();
        }
        return out;
    }

    @Override
    public void writeAll(List<Cliente> data) {
        try (FileOutputStream out = new FileOutputStream(xmlFile)) {
            JAXBContext ctx = JAXBContext.newInstance(Cliente.class);
            Marshaller m = ctx.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            m.setProperty(Marshaller.JAXB_FRAGMENT, true);

            XMLOutputFactory xof = XMLOutputFactory.newFactory();
            XMLStreamWriter xw = xof.createXMLStreamWriter(out, "UTF-8");

            xw.writeStartDocument("UTF-8", "1.0");
            xw.writeStartElement("clientes");

            if (data != null) {
                for (Cliente c : data) {
                    m.marshal(c, xw);
                }
            }

            xw.writeEndElement();
            xw.writeEndDocument();
            xw.flush();
            xw.close();
        } catch (Exception ex) {
            System.err.println("[WARN] Error escribiendo " + xmlFile);
            ex.printStackTrace();
        }
    }

    /**
     * Se asegura de que el archivo donde se guardara la informacion exista.
     */
    private void ensureFile() {
        try {
            File parent = xmlFile.getParentFile();

            if (parent != null) {
                parent.mkdirs();
            }

            if (!xmlFile.exists()) {
                xmlFile.createNewFile();
                writeAll(new ArrayList<>());
            }
        } catch (Exception ignored) {}
    }
}
