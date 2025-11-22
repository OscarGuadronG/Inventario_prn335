package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

@Stateless
@LocalBean
public class AlmacenDAO extends InventarioDefaultDataAccess<Almacen> implements Serializable {

    @Inject
    private TipoAlmacenDAO tipoAlmacenDAO;

    public AlmacenDAO() { super(Almacen.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}

    public List<Almacen> findLikeConsulta(String consulta) {
        if (consulta == null || consulta.isBlank()) {
            return Collections.emptyList();
        }

        try {
            // Buscar por ID numérico si es posible
            try {
                Integer idConsulta = Integer.parseInt(consulta.trim());
                Almacen almacen = buscarPorId(idConsulta);
                return almacen != null ? List.of(almacen) : Collections.emptyList();
            } catch (NumberFormatException e) {
                // Buscar por tipo de almacén u otros campos
                return em.createQuery(
                                "SELECT a FROM Almacen a WHERE " +
                                        "LOWER(a.idTipoAlmacen.nombre) LIKE LOWER(:consulta) OR " +
                                        "LOWER(a.observaciones) LIKE LOWER(:consulta)",
                                Almacen.class)
                        .setParameter("consulta", "%" + consulta + "%")
                        .setMaxResults(20)
                        .getResultList();
            }
        } catch (Exception e) {

            return Collections.emptyList();
        }
    }

}
