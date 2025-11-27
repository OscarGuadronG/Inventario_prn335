package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.*;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws.CompraEndpoint;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

@Stateless
@LocalBean
public class NotificadorCompra implements Serializable {
    @Resource(lookup = "jms/JmsFactory")
    ConnectionFactory connectionFactory;

    @Resource(lookup = "jms/JmsQueue")
    Queue queue;

    @Inject
    private CompraEndpoint compraEndpoint;

    public void notificarCambioCompra(String mensaje) {
        try {
            compraEndpoint.enviarMensajeBroadcast(mensaje);
        } catch (Exception e) {
            System.out.println("Error WebSocket: " + e.getMessage());
        }
    }
}