package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.*;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cliente extends Persona {

    @XmlElement(name = "direccion")
    private String direccion;

    public Cliente(int id, String nombreCompleto, String telefono, String email, String direccion) {
        super(id, nombreCompleto, telefono, email);
        this.direccion = direccion;
    }

    public Cliente() { super(); }

    public String getDireccion() { return direccion; }
    public void setDireccion(String direccion) { this.direccion = direccion; }
}