package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Producto;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class ProductoDAO extends InventarioDefaultDataAccess<Producto> implements Serializable {

    public ProductoDAO() {super(Producto.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    //Metodos especificos

    public List<Producto> findProductosActivos() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
        Root<Producto> root = cq.from(Producto.class);
        cq.select(root).where(cb.isTrue(root.get("activo"))).orderBy(cb.asc(root.get("nombreProducto")));
        return em.createQuery(cq).getResultList();
    }

    public List<Producto> findLikeConsulta(String consulta) {
        if (consulta == null || consulta.isBlank()) {
            return Collections.emptyList();
        }
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Producto> cq = cb.createQuery(Producto.class);
        Root<Producto> root = cq.from(Producto.class);
        // Crear predicado para buscar por nombre y filtrar por activo
        Predicate predicadoNombre = cb.like(
                cb.lower(root.get("nombreProducto")),
                "%" + consulta.toLowerCase() + "%"
        );

        Predicate predicadoActivo = cb.equal(root.get("activo"), true);

        cq.select(root)
                .where(cb.and(predicadoNombre, predicadoActivo))
                .orderBy(cb.asc(root.get("nombreProducto")));

        return em.createQuery(cq)
                .setMaxResults(20) // Limitar resultados
                .getResultList();
    }

}
