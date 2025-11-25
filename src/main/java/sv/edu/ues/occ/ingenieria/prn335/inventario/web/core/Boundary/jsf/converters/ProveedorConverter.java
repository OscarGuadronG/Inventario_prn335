package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ProveedorDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Proveedor;

import java.util.UUID;

@FacesConverter(value = "proveedorConverter", managed = true)
@ApplicationScoped
public class ProveedorConverter implements Converter<Proveedor> {

    @Inject
    private ProveedorDAO proveedorDAO;

    @Override
    public Proveedor getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.isBlank()) return null;
        try {
            Proveedor encontrado = proveedorDAO.buscarPorId(Integer.parseInt(value));
            if (encontrado == null) {
                System.out.println("No se encontro el Proveedor");
                return null;
            }else {
                System.out.println("Proveerdor encontrado: " + encontrado);
            }
            return encontrado;
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, Proveedor proveedor) {
        if (proveedor == null || proveedor.getId() == null) return "";
        return proveedor.getId().toString();
    }
}