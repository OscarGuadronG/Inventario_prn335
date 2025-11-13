package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;


import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public abstract class InventarioDefaultDataAccess<T> implements InventarioDAOinterface<T> {

    private final Class<T> entityClass;

    public InventarioDefaultDataAccess(Class<T> registro) {
        this.entityClass = registro;
    }

    public abstract EntityManager getEntityManager();

    public void crear(T registro) throws IllegalAccessException {
        if (registro == null) {
            throw new IllegalAccessException("dao.RegistroNulo");
        }
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        try {
            em.persist(registro);
        } catch (Exception ex) {
            throw new IllegalStateException("dao.GuardarError");
        }
    }

    public T modificar(T registro) throws IllegalArgumentException, IllegalAccessException {
        if (registro == null) {
            throw new IllegalAccessException("dao.RegistroNulo");
        }
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        Object id;
        try {
            id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(registro);
        } catch (Exception ex) {
            throw new IllegalAccessException("dao.IdNoValido");
        }
        T existing = em.find(entityClass, id);
        if (existing == null) {
            throw new IllegalStateException("dao.RegistroNoEncontrado");
        }
        try {
            return em.merge(registro);
        } catch (Exception ex) {
            throw new IllegalStateException("dao.ModificarError");
        }
    }

    public T buscarPorId(Object id) throws IllegalArgumentException, IllegalAccessException {
        if (id == null) {
            throw new IllegalAccessException("dao.IdNulo");
        }
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        try {
            return em.find(entityClass, id);
        } catch (Exception ex) {
            throw new IllegalStateException("dao.buscarError");
        }
    }

    public void eliminar(Object id) throws IllegalArgumentException, IllegalAccessException {
        if (id == null) {
            throw new IllegalArgumentException("dao.IdNulo");
        }
        EntityManager em = getEntityManager();
        if (em == null) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        T entidad;
        try {
            entidad = em.find(entityClass, id);
        } catch (Exception ex) {
            throw new IllegalStateException("dao.buscarError");
        }
        if (entidad == null) {
            throw new IllegalStateException("dao.eliminarNoEncontrado");
        }
        try {
            em.remove(entidad);
        } catch (Exception ex) {
            throw new IllegalStateException("dao.EliminarError");
        }
    }

    public List<T> findRange(int first, int max) throws IllegalArgumentException {
        if (first < 0 || max < 1) {
            throw new IllegalArgumentException("dao.FueraDeRango");
        }
        try {
            EntityManager em = getEntityManager();
            if (em != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<T> cp = cb.createQuery(entityClass);
                Root<T> rt = cp.from(entityClass);

                CriteriaQuery<T> all = cp.select(rt).orderBy(cb.asc(rt.get("id")));

                TypedQuery<T> allQuery = em.createQuery(all);
                allQuery.setFirstResult(first);
                allQuery.setMaxResults(max);
                return allQuery.getResultList();
            }
        } catch (Exception e) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        throw new IllegalStateException("dao.AccesoDB");
    }

    public int count() throws IllegalArgumentException {
        try {
            EntityManager em = getEntityManager();
            if (em != null) {
                CriteriaBuilder cb = em.getCriteriaBuilder();
                CriteriaQuery<Long> cp = cb.createQuery(Long.class);
                Root<T> rootEntry = cp.from(entityClass);
                cp.select(cb.count(rootEntry));
                TypedQuery<Long> allQuery = em.createQuery(cp);
                return ((Long) allQuery.getSingleResult()).intValue();
            }
        } catch (Exception e) {
            throw new IllegalStateException("dao.AccesoDB");
        }
        return -1;
    }

    public List<T> findLikeConsulta(String consulta){
        throw new UnsupportedOperationException("Implementar este método en los casos que aplica");
    }

}
