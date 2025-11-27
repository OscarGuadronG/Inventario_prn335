package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.websocket.Session;
import java.util.HashSet;
import java.util.Set;

@Named
@ApplicationScoped
public class SessionHandler {

    @Inject
    SessionHandler sessionHandler;
    private final Set<Session> sessions = new HashSet<>();

    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }

    public Set<Session> getSessions() {
        return sessions;
    }

    public void enviarMensajeBroadcast(String mensaje) {
        System.out.println("Enviando mensaje a " + sessions.size() + " clientes: " + mensaje);
        for (Session session : sessionHandler.getSessions()) {
            if (session.isOpen()) {
                try {
                    session.getBasicRemote().sendText(mensaje);
                } catch (Exception e) {
                    System.out.println("Error enviando mensaje: " + e.getMessage());
                }
            }
        }
    }}