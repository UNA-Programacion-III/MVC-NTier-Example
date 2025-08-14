package cr.ac.una.utilities;

import com.sun.istack.NotNull;
import cr.ac.una.data_access_layer.ClienteFileStore;
import cr.ac.una.data_access_layer.IFileStore;
import cr.ac.una.domain_layer.Cliente;

import java.io.File;

public class FileManagement {
    static File baseDir = new File(System.getProperty("user.dir"));

    @NotNull
    public static IFileStore<Cliente> getClientesFileStore(String fileName) {
        File clientsXml = new File(baseDir, fileName);
        return new ClienteFileStore(clientsXml);
    }
}
