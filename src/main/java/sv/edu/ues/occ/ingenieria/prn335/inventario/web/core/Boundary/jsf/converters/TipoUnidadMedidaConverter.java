package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoUnidadMedidaDAO;

@FacesConverter(value = "tipoUnidadMedidaConverter", managed = true)
@ApplicationScoped
public class TipoUnidadMedidaConverter implements Converter<TipoUnidadMedida> {

    @Inject
    private TipoUnidadMedidaDAO tipoUnidadMedidaDAO;

    @Override
    public TipoUnidadMedida getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            return tipoUnidadMedidaDAO.buscarPorId(Integer.valueOf(value));

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, TipoUnidadMedida tipo) {
        if (tipo == null || tipo.getId() == null) return "";
        return tipo.getId().toString();
    }
}

