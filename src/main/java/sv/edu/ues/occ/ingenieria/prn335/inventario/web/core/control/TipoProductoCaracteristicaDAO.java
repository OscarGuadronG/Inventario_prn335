package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoProductoCaracteristicaDAO extends InventarioDefaultDataAccess<TipoProductoCaracteristica> implements Serializable {

    public TipoProductoCaracteristicaDAO() {
        super(TipoProductoCaracteristica.class);
    }

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    //ESPECIFICOS
    public List<TipoProductoCaracteristica> findByConsulta(Long idTipoProducto) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoProductoCaracteristica> cq = cb.createQuery(TipoProductoCaracteristica.class);
        Root<TipoProductoCaracteristica> root = cq.from(TipoProductoCaracteristica.class);
        cq.select(root).where(cb.equal(root.get("idTipoProducto").get("id"), idTipoProducto));
        return em.createQuery(cq).getResultList();
    }

}
