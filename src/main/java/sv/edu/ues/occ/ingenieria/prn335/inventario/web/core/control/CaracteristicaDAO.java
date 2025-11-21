package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
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

    public List<Caracteristica> findLikeConsulta(String filtro, List<Integer> idsOmitir) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Caracteristica> cq = cb.createQuery(Caracteristica.class);
        Root<Caracteristica> root = cq.from(Caracteristica.class);

        Predicate predicado = cb.conjunction();

        // Filtro por nombre
        if (filtro != null && !filtro.isBlank()) {
            predicado = cb.and(predicado,
                    cb.like(cb.lower(root.get("nombre")), "%" + filtro.toLowerCase() + "%")
            );
        }

        // Omitir las ya asignadas
        if (idsOmitir != null && !idsOmitir.isEmpty()) {
            predicado = cb.and(predicado,
                    cb.not(root.get("id").in(idsOmitir))
            );
        }

        cq.select(root).where(predicado).orderBy(cb.asc(root.get("nombre")));
        return em.createQuery(cq).setMaxResults(15).getResultList();
    }

}
