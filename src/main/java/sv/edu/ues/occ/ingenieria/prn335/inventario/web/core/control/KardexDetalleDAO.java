package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Kardex;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.KardexDetalle;

import java.io.Serializable;
import java.util.List;

@Stateless
@LocalBean
public class KardexDetalleDAO extends InventarioDefaultDataAccess<KardexDetalle> implements Serializable {

    public KardexDetalleDAO() {super(KardexDetalle.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    //especificos

    public List<KardexDetalle> findByKardex(Kardex kardex) {
        return em.createQuery(
                        "SELECT kd FROM KardexDetalle kd WHERE kd.idKardex = :kardex AND kd.activo = true ORDER BY kd.lote",
                        KardexDetalle.class)
                .setParameter("kardex", kardex)
                .getResultList();
    }

}
