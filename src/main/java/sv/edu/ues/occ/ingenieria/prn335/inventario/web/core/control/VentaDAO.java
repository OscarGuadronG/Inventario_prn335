package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Venta;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class VentaDAO extends InventarioDefaultDataAccess<Venta> implements Serializable {

    public VentaDAO() {super(Venta.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    //ESPECIFICOS
    public List<Venta> buscarPagadasParaRecepcion(int first, int max) {
        return em.createQuery(
                        "SELECT v FROM Venta v WHERE v.estado = 'CERRADA' ORDER BY v.fecha",
                        Venta.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPagadasParaRecepcion() {
        return em.createQuery(
                        "SELECT COUNT(v) FROM Venta v WHERE v.estado = 'CERRADA'",
                        Long.class)
                .getSingleResult();
    }
}
