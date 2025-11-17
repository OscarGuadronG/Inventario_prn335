package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

@FacesConverter(value = "caracteristicaConverter", managed = true)
@ApplicationScoped
public class CaracteristicaConverter implements Converter<Caracteristica> {

    @Inject
    private CaracteristicaDAO caracteristicaDAO;

    @Override
    public Caracteristica getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            System.out.println("encontrado: " + caracteristicaDAO.buscarPorId(Integer.valueOf(value)));
            return caracteristicaDAO.buscarPorId(Integer.valueOf(value));

        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, Caracteristica tipo) {
        if (tipo == null || tipo.getId() == null) return "";
        return tipo.getId().toString();
    }
}
