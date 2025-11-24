package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Compra;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class CompraDAO extends InventarioDefaultDataAccess<Compra> implements Serializable {

    public CompraDAO() {super(Compra.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}


    //ESPECIFICOS
    public List<Compra> buscarPagadasParaRecepcion(int first, int max) {
        return em.createQuery(
                        "SELECT c FROM Compra c WHERE c.estado = 'PAGADA' ORDER BY c.fecha",
                        Compra.class)
                .setFirstResult(first)
                .setMaxResults(max)
                .getResultList();
    }

    public Long contarPagadasParaRecepcion() {
        return em.createQuery(
                        "SELECT COUNT(c) FROM Compra c WHERE c.estado = 'PAGADA'",
                        Long.class)
                .getSingleResult();
    }
}
