package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoProductoDAO extends InventarioDefaultDataAccess<TipoProducto> implements Serializable {

    public TipoProductoDAO() {super(TipoProducto.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<TipoProducto> findTiposPadre() {
        return em.createQuery(
                        "SELECT t FROM TipoProducto t WHERE t.idTipoProductoPadre IS NULL AND t.activo = true ORDER BY t.nombre",
                        TipoProducto.class)
                .getResultList();
    }

}
