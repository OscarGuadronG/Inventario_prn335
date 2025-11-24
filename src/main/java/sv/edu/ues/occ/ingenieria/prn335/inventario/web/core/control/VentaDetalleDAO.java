package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
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

    public List<VentaDetalle> findLikeConsulta(String consulta) {
        if (consulta == null || consulta.isBlank()) {
            return Collections.emptyList();
        }

        try {

            try {
                UUID uuidConsulta = UUID.fromString(consulta.trim());
                return em.createQuery(
                                "SELECT vd FROM VentaDetalle vd WHERE vd.id = :uuid",
                                VentaDetalle.class)
                        .setParameter("uuid", uuidConsulta)
                        .getResultList();
            } catch (IllegalArgumentException e) {

                return em.createQuery(
                                "SELECT vd FROM VentaDetalle vd WHERE LOWER(vd.idProducto.nombreProducto) LIKE LOWER(:consulta)",
                                VentaDetalle.class)
                        .setParameter("consulta", "%" + consulta + "%")
                        .getResultList();
            }
        } catch (Exception e) {

            return Collections.emptyList();
        }
    }
    }


