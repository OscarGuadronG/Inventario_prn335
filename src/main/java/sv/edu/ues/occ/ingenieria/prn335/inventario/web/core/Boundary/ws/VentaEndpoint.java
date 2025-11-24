package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.Serializable;

@ApplicationScoped
@ServerEndpoint("/ventaRecepcion")
public class VentaEndpoint implements Serializable {

    @Inject
    SessionHandler sessionHandler;

    @OnOpen
    public void abrirConexion(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void cerrarConexion(Session session) {
        sessionHandler.removeSession(session);
    }

    public void enviarMensajeBroadcast(String mensaje) {
        for (Session session : sessionHandler.getSessions()) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(mensaje);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void notificarCierreVenta(String idVenta) {
        System.out.println("🔔 Notificando cierre de venta: " + idVenta);
        enviarMensajeBroadcast("VENTA_CERRADA:" + idVenta);
    }
}