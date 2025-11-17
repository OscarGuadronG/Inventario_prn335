package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.CompraDetalle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
public class CompraDetalleDAO extends InventarioDefaultDataAccess<CompraDetalle> implements Serializable {

    public CompraDetalleDAO() {super(CompraDetalle.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<CompraDetalle> buscarPorCompra(Integer idCompra) {
        return em.createQuery("SELECT cd FROM CompraDetalle cd WHERE cd.compra.id = :idCompra", CompraDetalle.class)
                .setParameter("idCompra", idCompra)
                .getResultList();
    }

    public BigDecimal calcularTotalCompra(Integer idCompra) {
        return em.createQuery("SELECT SUM(cd.cantidad * cd.precio) FROM CompraDetalle cd WHERE cd.compra.id = :idCompra", BigDecimal.class)
                .setParameter("idCompra", idCompra)
                .getSingleResult();
    }

    public BigDecimal calcularSubtotal(CompraDetalle detalle) {
        if (detalle.getCantidad() != null && detalle.getPrecio() != null) {
            return detalle.getCantidad().multiply(detalle.getPrecio());
        }
        return BigDecimal.ZERO;
    }

}
