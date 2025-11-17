package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
public class VentaDetalleDAO extends InventarioDefaultDataAccess<VentaDetalle> implements Serializable {

    public VentaDetalleDAO() {super(VentaDetalle.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public BigDecimal calcularSubtotal(VentaDetalle detalle) {
        if (detalle == null || detalle.getCantidad() == null || detalle.getPrecio() == null) {
            return BigDecimal.ZERO;
        }
        return detalle.getCantidad().multiply(detalle.getPrecio());
    }

    public BigDecimal calcularTotalVenta(UUID idVenta) {
        List<VentaDetalle> detalles = buscarPorVenta(idVenta);

        BigDecimal total = BigDecimal.ZERO;
        for (VentaDetalle detalle : detalles) {
            total = total.add(calcularSubtotal(detalle));
        }

        return total;
    }

    public List<VentaDetalle> buscarPorVenta(UUID id) {
        return em.createQuery(
                        "SELECT t FROM VentaDetalle t WHERE t.idVenta.id = :id ORDER BY t.id",
                        VentaDetalle.class)
                .setParameter("id", id)
                .getResultList();
    }

}
