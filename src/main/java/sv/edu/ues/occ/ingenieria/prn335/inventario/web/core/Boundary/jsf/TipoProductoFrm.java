package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

import java.io.Serializable;
import java.util.List;
@Named
@ViewScoped
public class TipoProductoFrm extends DefaultFrm<TipoProducto> implements Serializable {
    @Inject
    private transient TipoProductoDAO taDao;


    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de producto";

    @Override
    protected InventarioDefaultDataAccess<TipoProducto> getDao() {
        return taDao;
    }

    @Override
    protected TipoProducto buscarRegistroPorId(Object id) throws IllegalAccessException {
        Long longId;
        if (id instanceof Integer) {
            longId = ((Integer) id).longValue();
        } else if (id instanceof Long) {
            longId = (Long) id;
        } else {
            throw new IllegalAccessException("Tipo de ID no soportado: " + (id != null ? id.getClass().getName() : "null"));
        }
        return taDao.buscarPorId(longId);
    }


    @Override
    protected TipoProducto nuevoRegistro() {
        return new TipoProducto();
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
//ESPECIFICOS
    public List<TipoProducto> findActivos() {
        return taDao.findActivos();
    }

    public List<TipoProducto> findTiposPadre() {
        return taDao.findTiposPadre();
    }

}
