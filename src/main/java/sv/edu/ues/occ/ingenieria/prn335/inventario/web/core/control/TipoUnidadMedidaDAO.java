package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class TipoUnidadMedidaDAO extends InventarioDefaultDataAccess<TipoUnidadMedida> implements Serializable {

    public TipoUnidadMedidaDAO() {super(TipoUnidadMedida.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<TipoUnidadMedida> findLikeConsulta(String consulta){
        if (consulta == null || consulta.isBlank()) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<TipoUnidadMedida> cq = cb.createQuery(TipoUnidadMedida.class);
        Root<TipoUnidadMedida> root = cq.from(TipoUnidadMedida.class);
        cq.select(root).where(cb.like(cb.lower(root.get("nombre")), "%" + consulta.toLowerCase() + "%"));
        return em.createQuery(cq).getResultList();
    }
}
