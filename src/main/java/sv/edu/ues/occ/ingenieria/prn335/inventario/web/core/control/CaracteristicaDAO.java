package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class CaracteristicaDAO extends InventarioDefaultDataAccess<Caracteristica> implements Serializable {

    public CaracteristicaDAO() {super(Caracteristica.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<Caracteristica> findLikeConsulta(){
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Caracteristica> cq = cb.createQuery(Caracteristica.class);
        Root<Caracteristica> root = cq.from(Caracteristica.class);
        cq.select(root);
        return em.createQuery(cq).getResultList();
    }

}
