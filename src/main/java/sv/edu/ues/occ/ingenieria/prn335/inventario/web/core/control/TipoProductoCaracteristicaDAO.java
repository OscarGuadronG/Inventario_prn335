package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class TipoProductoCaracteristicaDAO extends InventarioDefaultDataAccess<TipoProductoCaracteristica> implements Serializable {

    public TipoProductoCaracteristicaDAO() {super(TipoProductoCaracteristica.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<TipoProductoCaracteristica> findByTipoProductoId(Long idTipoProducto) {
        return em.createQuery(
                        "SELECT tpc FROM TipoProductoCaracteristica tpc WHERE tpc.idTipoProducto.id = :idTipoProducto ORDER BY tpc.idCaracteristica.nombre",
                        TipoProductoCaracteristica.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }

}
