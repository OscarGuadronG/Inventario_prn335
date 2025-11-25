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
        System.out.println("🎯 === ClienteConverter.getAsObject INVOCADO ===");
        System.out.println("🎯 Valor recibido para conversión: '" + value + "'");
        System.out.println("🎯 Componente: " + cmp.getId());

        if (value == null || value.isBlank()) {
            System.out.println("🎯 Valor vacío, retornando null");
            return null;
        }

        try {
            UUID id = UUID.fromString(value);
            System.out.println("🎯 Buscando cliente con UUID: " + id);

            Cliente encontrado = clienteDAO.buscarPorId(id);

            if (encontrado == null) {
                System.out.println("❌ No se encontró el cliente con ID: " + id);
                return null;
            } else {
                System.out.println("✅ Cliente CONVERTIDO: " + encontrado.getId() + " - " + encontrado.getNombre());
            }
            return encontrado;

        } catch (IllegalArgumentException e) {
            System.err.println("❌ Error: UUID inválido - '" + value + "'");
            return null;
        } catch (IllegalAccessException e) {
            System.err.println("❌ Error de acceso: " + e.getMessage());
            throw new RuntimeException(e);
        } catch (Exception e) {
            System.err.println("❌ Error inesperado: " + e.getMessage());
            return null;
        }
    }

    @Override
    public String getAsString(FacesContext ctx, UIComponent cmp, Cliente cliente) {
        // Este se llama para cada elemento al renderizar - ES NORMAL
        if (cliente == null) {
            return "";
        }

        if (cliente.getId() == null) {
            return "";
        }

        String result = cliente.getId().toString();
        // Comentamos este log para no saturar la consola
        // System.out.println("📝 Convirtiendo cliente a string: " + cliente.getNombre() + " -> " + result);
        return result;
    }
}