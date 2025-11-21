package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Producto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class ProductoFrm extends DefaultFrm<Producto> implements Serializable {
    @Inject
    private transient ProductoDAO taDao;


    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Producto";

    @Override
    protected InventarioDefaultDataAccess<Producto> getDao() {
        return taDao;
    }

    @Override
    protected Producto buscarRegistroPorId(Object id) throws IllegalAccessException {
        if (id instanceof UUID) {
            return taDao.buscarPorId(id);
        } else if (id instanceof String) {
            return taDao.buscarPorId(UUID.fromString((String) id));
        } else {
            throw new IllegalAccessException("Tipo de ID no soportado: " + (id != null ? id.getClass().getName() : "null"));
        }
    }


    @Override
    protected Producto nuevoRegistro() {
        Producto producto = new Producto();
        producto.setId(UUID.randomUUID());
        return producto;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();

    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }

    public List<Producto> findProductosActivos() {
        return taDao.findProductosActivos();
    }
}
