package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
public class ProductoTipoProductoCaracteristicaDAO extends InventarioDefaultDataAccess<ProductoTipoProductoCaracteristica> implements Serializable {

    public ProductoTipoProductoCaracteristicaDAO() {super(ProductoTipoProductoCaracteristica.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}
//ESPECIFICOS
public List<TipoProductoCaracteristica> findCaracteristicasDisponibles(UUID idProductoTipoProducto, Long idTipoProducto) {
    return em.createQuery("""
        SELECT tpc FROM TipoProductoCaracteristica tpc 
        WHERE tpc.idTipoProducto.id = :idTipoProducto
        AND tpc.id NOT IN (
            SELECT ptpc.idTipoProductoCaracteristica.id 
            FROM ProductoTipoProductoCaracteristica ptpc 
            WHERE ptpc.idProductoTipoProducto.id = :idProductoTipoProducto
        )
        """, TipoProductoCaracteristica.class)
            .setParameter("idProductoTipoProducto", idProductoTipoProducto)
            .setParameter("idTipoProducto", idTipoProducto)
            .getResultList();
}

    public List<TipoProductoCaracteristica> findCaracteristicasAsignadas(UUID idProductoTipoProducto) {
        return em.createQuery("""
        SELECT ptpc.idTipoProductoCaracteristica 
        FROM ProductoTipoProductoCaracteristica ptpc 
        WHERE ptpc.idProductoTipoProducto.id = :idProductoTipoProducto
        """, TipoProductoCaracteristica.class)
                .setParameter("idProductoTipoProducto", idProductoTipoProducto)
                .getResultList();
    }

    public void eliminarPorProductoYCaracteristica(UUID idProductoTipoProducto, Long idCaracteristica) {
        em.createQuery("DELETE FROM ProductoTipoProductoCaracteristica ptpc WHERE ptpc.idProductoTipoProducto.id = :idProductoTipoProducto AND ptpc.idTipoProductoCaracteristica.id = :idCaracteristica")
                .setParameter("idProductoTipoProducto", idProductoTipoProducto)
                .setParameter("idCaracteristica", idCaracteristica)
                .executeUpdate();
    }

    public List<TipoProductoCaracteristica> findCaracteristicasByTipoProducto(Long idTipoProducto) {
        return em.createQuery("""
        SELECT tpc FROM TipoProductoCaracteristica tpc 
        WHERE tpc.idTipoProducto.id = :idTipoProducto
        """, TipoProductoCaracteristica.class)
                .setParameter("idTipoProducto", idTipoProducto)
                .getResultList();
    }
    public List<ProductoTipoProductoCaracteristica> findProductoTipoProductoCaracteristicas(UUID idProductoTipoProducto) {
        return em.createQuery("""
        SELECT ptpc FROM ProductoTipoProductoCaracteristica ptpc 
        LEFT JOIN FETCH ptpc.idTipoProductoCaracteristica tpc
        LEFT JOIN FETCH tpc.idCaracteristica
        WHERE ptpc.idProductoTipoProducto.id = :idProductoTipoProducto
        """, ProductoTipoProductoCaracteristica.class)
                .setParameter("idProductoTipoProducto", idProductoTipoProducto)
                .getResultList();
    }

}
