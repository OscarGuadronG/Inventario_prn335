package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoCaracteristicaDAO;

@FacesConverter(value = "tipoProductoCaracteristicaConverter", managed = true)
@ApplicationScoped
public class TipoProductoCaracteristicaConverter implements Converter<TipoProductoCaracteristica> {

    @Inject
    private TipoProductoCaracteristicaDAO tipoProductoCaracteristicaDAO;

    @Override
    public TipoProductoCaracteristica getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        System.out.println("Converter getAsObject: " + value);
        if (value == null || value.isBlank()) return null;
        try {
            Long id = Long.parseLong(value);
            TipoProductoCaracteristica result = tipoProductoCaracteristicaDAO.buscarPorId(id);
            System.out.println("Converter encontrado: " + (result != null ? result.getId() : "null"));
            return result;
        } catch (Exception e) {
            System.out.println("ERROR en converter getAsObject: " + e.getMessage());
            return null; // Mejor retornar null que lanzar excepción
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, TipoProductoCaracteristica tpc) {
        System.out.println("Converter getAsString: " + (tpc != null ? tpc.getId() : "null"));
        if (tpc == null || tpc.getId() == null) return "";
        return tpc.getId().toString();
    }
}