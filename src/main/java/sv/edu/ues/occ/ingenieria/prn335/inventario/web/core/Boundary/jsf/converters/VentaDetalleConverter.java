package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.VentaDetalleDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;

import java.util.UUID;

@FacesConverter(value = "ventaDetalleConverter", managed = true)
@ApplicationScoped
public class VentaDetalleConverter implements Converter<VentaDetalle> {

    @Inject
    private VentaDetalleDAO ventaDetalleDAO;

    @Override
    public VentaDetalle getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return ventaDetalleDAO.buscarPorId(UUID.fromString(value));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al convertir VentaDetalle: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, VentaDetalle ventaDetalle) {
        if (ventaDetalle == null || ventaDetalle.getId() == null) return "";
        return ventaDetalle.getId().toString();
    }
}