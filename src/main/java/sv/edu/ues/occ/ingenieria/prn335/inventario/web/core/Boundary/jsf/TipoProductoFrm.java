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
@ViewScoped
@Named
public class TipoProductoFrm extends DefaultFrm<TipoProducto> implements Serializable {
    @Inject
    private transient TipoProductoDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de Almacen";

    @Override
    protected InventarioDefaultDataAccess<TipoProducto> getDao() {
        return taDao;
    }

    @Override
    protected TipoProducto buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
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
    public List<TipoProducto> findTiposPadre() {
        return taDao.findTiposPadre();
    }
    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }


}
