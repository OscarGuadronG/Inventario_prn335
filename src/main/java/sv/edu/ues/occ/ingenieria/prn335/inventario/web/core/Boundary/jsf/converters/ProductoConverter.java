package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ProductoDAO;

import java.util.UUID;

@FacesConverter(value = "productoConverter", managed = true)
@ApplicationScoped
public class ProductoConverter implements Converter<Producto> {

    @Inject
    private ProductoDAO productoDAO;

    @Override
    public Producto getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }

        try {
            UUID id = UUID.fromString(value);
            return productoDAO.buscarPorId(id);
        } catch (IllegalArgumentException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "ID de Producto inválido", "El formato del ID no es correcto"));
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error al convertir Producto", "No se pudo encontrar el producto con ID: " + value));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Producto value) {
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