package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CompraDetalleDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.CompraDetalle;

import java.util.UUID;

@FacesConverter(value = "compraDetalleConverter", managed = true)
@ApplicationScoped
public class CompraDetalleConverter implements Converter<CompraDetalle> {

    @Inject
    private CompraDetalleDAO compraDetalleDAO;

    @Override
    public CompraDetalle getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return compraDetalleDAO.buscarPorId(UUID.fromString(value));
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Error al convertir CompraDetalle: " + value, e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, CompraDetalle compraDetalle) {
        if (compraDetalle == null || compraDetalle.getId() == null) return "";
        return compraDetalle.getId().toString();
    }
}