package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProducto;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Stateless
@LocalBean
public class ProductoTipoProductoDAO extends InventarioDefaultDataAccess<ProductoTipoProducto> implements Serializable {

    public ProductoTipoProductoDAO() {super(ProductoTipoProducto.class);}

    @PersistenceContext(unitName = "inventarioPU")
    EntityManager em;

    @Override
    public EntityManager getEntityManager() {return em;}
    //ESPECIFICOS
    public List<ProductoTipoProducto> findByProducto(UUID idProducto) {
        if (idProducto == null) {
            throw new IllegalArgumentException("ID Producto no puede ser nulo");
        }

        return em.createQuery(
                        "SELECT ptp FROM ProductoTipoProducto ptp WHERE ptp.idProducto.id = :idProducto",
                        ProductoTipoProducto.class)
                .setParameter("idProducto", idProducto)
                .getResultList();
    }

}
