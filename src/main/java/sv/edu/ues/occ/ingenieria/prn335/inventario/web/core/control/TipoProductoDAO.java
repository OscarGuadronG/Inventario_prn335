package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class TipoProductoDAO extends InventarioDefaultDataAccess<TipoProducto> implements Serializable {

    public TipoProductoDAO() {super(TipoProducto.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<TipoProducto> findHijos(TipoProducto padre) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoProducto> cq = cb.createQuery(TipoProducto.class);
        Root<TipoProducto> root = cq.from(TipoProducto.class);
        cq.select(root).where(cb.equal(root.get("idTipoProductoPadre"), padre));
        return em.createQuery(cq).getResultList();
    }

    public List<TipoProducto> findTiposPadre() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoProducto> cq = cb.createQuery(TipoProducto.class);
        Root<TipoProducto> root = cq.from(TipoProducto.class);

        cq.select(root).where(cb.isNull(root.get("idTipoProductoPadre")));

        return em.createQuery(cq).getResultList();
    }
    public List<TipoProducto> findLikeConsulta(String consulta){
        if (consulta == null || consulta.isBlank()) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoProducto> cq = cb.createQuery(TipoProducto.class);
        Root<TipoProducto> root = cq.from(TipoProducto.class);
        cq.select(root).where(cb.like(cb.lower(root.get("nombre")), "%" + consulta.toLowerCase() + "%"));
        return em.createQuery(cq).getResultList();
    }
}
