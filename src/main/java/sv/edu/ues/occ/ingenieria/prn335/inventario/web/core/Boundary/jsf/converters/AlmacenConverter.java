package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO;

@FacesConverter(value = "almacenConverter", managed = true)
@ApplicationScoped
public class AlmacenConverter implements Converter<Almacen> {

    @Inject
    private AlmacenDAO almacenDAO;

    @Override
    public Almacen getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            Integer id = Integer.parseInt(value);
            return almacenDAO.buscarPorId(id);
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "ID de Almacén inválido", "El formato del ID debe ser numérico"));
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al convertir Almacén", "No se pudo encontrar el almacén con ID: " + value));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Almacen value) {
        if (value == null) {
            return "";
        }

        if (value.getId() != null) {
            return value.getId().toString();
        } else {
            return "";
        }
    }
}