package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Proveedor;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class ProveedorDAO extends InventarioDefaultDataAccess<Proveedor> implements Serializable {

    public ProveedorDAO() {super(Proveedor.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}
//Metodos Especificos
public List<Proveedor> findActivos() {
    return em.createQuery(
                    "SELECT t FROM Proveedor t WHERE  t.activo = true ORDER BY t.nombre",
                    Proveedor.class)
            .getResultList();
}
}
