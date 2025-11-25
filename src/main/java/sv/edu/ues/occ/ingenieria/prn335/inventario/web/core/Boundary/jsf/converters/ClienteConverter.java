package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ClienteDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;

import java.util.UUID;

@FacesConverter(value = "clienteConverter", managed = true)
@ApplicationScoped
public class ClienteConverter implements Converter<Cliente> {

    @Inject
    private ClienteDAO clienteDAO;

    @Override
    public Cliente getAsObject(FacesContext ctx, UIComponent cmp, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        try {
            UUID id = UUID.fromString(value);
            Cliente cliente = clienteDAO.buscarPorId(id);
            System.out.println("Converter - ID: " + value + ", Cliente: " + (cliente != null ? cliente.getNombre() : "null"));
            return cliente;
        } catch (Exception e) {
            System.err.println("Error en converter: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, Cliente cliente) {
        if (cliente == null || cliente.getId() == null) {
            return "";
        }
        return cliente.getId().toString();
    }
}