package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity;

import jakarta.persistence.*;

import java.time.OffsetDateTime;
import java.util.Objects;

@Entity
@Table(name = "tipo_producto_caracteristica", schema = "public")
public class TipoProductoCaracteristica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tipo_producto_caracteristica", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_caracteristica")
    private Caracteristica idCaracteristica;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tipo_producto")
    private TipoProducto idTipoProducto;

    @Column(name = "obligatorio")
    private Boolean obligatorio;

    @Column(name = "fecha_creacion")
    private OffsetDateTime fechaCreacion;

    // Métodos equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TipoProductoCaracteristica that = (TipoProductoCaracteristica) o;

        // Si ambos tienen ID, comparar por ID
        if (id != null && that.id != null) {
            return Objects.equals(id, that.id);
        }

        // Si no tienen ID, comparar por todos los campos que definen la unicidad
        return Objects.equals(idCaracteristica, that.idCaracteristica) &&
                Objects.equals(idTipoProducto, that.idTipoProducto) &&
                Objects.equals(obligatorio, that.obligatorio) &&
                Objects.equals(fechaCreacion, that.fechaCreacion);
    }

    @Override
    public int hashCode() {
        // Si tiene ID, usar solo el ID para el hashCode
        if (id != null) {
            return Objects.hash(id);
        }

        // Si no tiene ID, usar todos los campos que definen la unicidad
        return Objects.hash(idCaracteristica, idTipoProducto, obligatorio, fechaCreacion);
    }

    // También puedes implementar toString() para debugging
    @Override
    public String toString() {
        return "TipoProductoCaracteristica{" +
                "id=" + id +
                ", idCaracteristica=" + (idCaracteristica != null ? idCaracteristica.getId() : "null") +
                ", idTipoProducto=" + (idTipoProducto != null ? idTipoProducto.getId() : "null") +
                ", obligatorio=" + obligatorio +
                ", fechaCreacion=" + fechaCreacion +
                '}';
    }

    // Tus getters y setters existentes...
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Caracteristica getIdCaracteristica() {
        return idCaracteristica;
    }

    public void setIdCaracteristica(Caracteristica idCaracteristica) {
        this.idCaracteristica = idCaracteristica;
    }

    public TipoProducto getIdTipoProducto() {
        return idTipoProducto;
    }

    public void setIdTipoProducto(TipoProducto idTipoProducto) {
        this.idTipoProducto = idTipoProducto;
    }

    public Boolean getObligatorio() {
        return obligatorio;
    }

    public void setObligatorio(Boolean obligatorio) {
        this.obligatorio = obligatorio;
    }

    public OffsetDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(OffsetDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}