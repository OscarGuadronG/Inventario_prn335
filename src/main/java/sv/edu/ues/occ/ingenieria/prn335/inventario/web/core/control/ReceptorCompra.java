package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.ejb.ActivationConfigProperty;
import jakarta.ejb.MessageDriven;
import jakarta.jms.Message;
import jakarta.jms.MessageListener;
import jakarta.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "destinationLookup", propertyValue = "jms/JmsQueue"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "jakarta.jms.Queue"),
        @ActivationConfigProperty(propertyName = "connectionFactoryLookup", propertyValue = "jms/JmsFactory")
})
public class ReceptorCompra implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            System.out.println("Mensaje recibido en ReceptorCompra: " + textMessage.getText());
        } catch (Exception e) {
            Logger.getLogger(ReceptorCompra.class.getName()).log(Level.SEVERE, "Error en ReceptorCompra", e);
        }
    }
}