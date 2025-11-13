package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.UnidadMedida;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Stateless
@LocalBean
public class UnidadMedidaDAO extends InventarioDefaultDataAccess<UnidadMedida> implements Serializable {

    public UnidadMedidaDAO() {super(UnidadMedida.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}


    public List<UnidadMedida> buscarPorTipo(Integer idTipoU){
        if (idTipoU == null) {
            return new ArrayList<>(); // Retorna lista vacía en lugar de lanzar excepción
        }
        EntityManager em = getEntityManager();
        return em.createQuery("SELECT ud FROM UnidadMedida ud WHERE ud.idTipoUnidadMedida.id = :idTipoU", UnidadMedida.class)
                .setParameter("idTipoU", idTipoU)
                .getResultList();
    }

}
