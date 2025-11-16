package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converter;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

@FacesConverter(value = "tipoAlmacenConverter", managed = true)
@ApplicationScoped
public class TipoAlmacenConverter implements Converter<TipoAlmacen> {

    @Inject
    private TipoAlmacenDAO tipoAlmacenDAO;

    @Override
    public TipoAlmacen getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return tipoAlmacenDAO.buscarPorId(Integer.valueOf(value));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, TipoAlmacen tipo) {
        if (tipo == null || tipo.getId() == null) return "";
        return tipo.getId().toString();
    }
}
