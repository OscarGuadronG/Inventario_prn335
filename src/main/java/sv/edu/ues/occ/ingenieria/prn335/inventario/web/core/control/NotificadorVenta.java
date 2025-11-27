package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.*;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws.VentaEndpoint;

import java.io.Serializable;

@Stateless
@LocalBean
public class NotificadorVenta implements Serializable {
    @Resource(lookup = "jms/JmsFactory")
    ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/JmsQueue")
    Queue queue;

    @Inject
    private VentaEndpoint ventaEndpoint;

    public void notificarCambioVenta(String mensaje) {
        try {
            ventaEndpoint.enviarMensajeBroadcast(mensaje);
        } catch (Exception e) {
            System.out.println("Error WebSocket venta: " + e.getMessage());
        }
    }
}