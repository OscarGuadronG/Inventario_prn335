package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.annotation.Resource;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.jms.*;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws.VentaEndpoint;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        try {
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(queue);
            TextMessage textMessage = session.createTextMessage(mensaje);
            connection.start();
            producer.send(textMessage);
            producer.close();
            session.close();
            connection.close();
        } catch (JMSException e) {
            Logger.getLogger(NotificadorCompra.class.getName()).log(Level.SEVERE, "Error al enviar mensaje JMS", e);
        }
    }
}