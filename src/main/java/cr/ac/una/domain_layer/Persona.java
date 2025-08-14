package cr.ac.una.domain_layer;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlSeeAlso({Cliente.class})
public abstract class Persona {

    @XmlAttribute(name = "id")
    private int id;

    @XmlElement(name = "nombreCompleto")
    private String nombreCompleto;

    @XmlElement(name = "telefono")
    private String telefono;

    @XmlElement(name = "email")
    private String email;

    protected Persona(int id, String nombreCompleto, String telefono, String email) {
        this.id = id;
        this.nombreCompleto = nombreCompleto;
        this.telefono = telefono;
        this.email = email;
    }

    Persona() {
    }

    public int getId() { return id; }
    public String getNombreCompleto() { return nombreCompleto; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }

    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        Persona persona = (Persona) o;
        return id == (persona.id);
    }

    @Override
    public String toString() { return nombreCompleto + " (" + email + ")"; }
}
