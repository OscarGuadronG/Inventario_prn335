package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.UUID;

@Entity
@Table(name = "cliente", schema = "public")
public class Cliente {
    @Id
    @Column(name = "id_cliente", nullable = false, updatable = false)
    private UUID id;

    @NotBlank(message = "{msg.nombre.blanco}")
    @Size(min = 2, max = 155, message = "{msg.nombre.longitud}")
    @Column(name = "nombre", length = 155)
    @Pattern(regexp = "^[A-Za-zÁÉÍÓÚáéíóúüÑñ ]+$", message = "{msg.nombre.caracteres}")
    private String nombre;

    @Size(max = 9)
    @Pattern(regexp = "\\d{9}", message = "{msg.dui.format}")
    @Column(name = "dui", length = 9)
    private String dui;

    @Size(max = 14)
    @Pattern(regexp = "\\d{14}", message = "{msg.nit.format}")
    @Column(name = "nit", length = 14)
    private String nit;

    @Column(name = "activo")
    private Boolean activo;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

}