package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;

import java.io.Serializable;
@Stateless
@LocalBean
public class ClienteDAO extends InventarioDefaultDataAccess<Cliente> implements Serializable {

    public ClienteDAO() { super(Cliente.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    //Metodos propios
    public boolean DocumentoUnico(String dui) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("dui"), dui));
        Long count = em.createQuery(cq).getSingleResult();
        return count == 0;
    }
    public boolean NitUnico(String nit) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        Root<Cliente> root = cq.from(Cliente.class);
        cq.select(cb.count(root)).where(cb.equal(root.get("nit"), nit));
        Long count = em.createQuery(cq).getSingleResult();
        return count == 0;
    }
}
