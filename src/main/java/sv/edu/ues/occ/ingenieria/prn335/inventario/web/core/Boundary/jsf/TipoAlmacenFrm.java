package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class TipoAlmacenFrm extends DefaultFrm<TipoAlmacen> implements Serializable{
    private static final long serialVersionUID = 1L;

    //Objeto DAO para manejar la entidad TipoAlmacen
    @Inject
    private transient TipoAlmacenDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de Almacen";

    @Override
    protected InventarioDefaultDataAccess<TipoAlmacen> getDao() {
        return taDao;
    }

    @Override
    protected TipoAlmacen buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected TipoAlmacen nuevoRegistro() {
        return new TipoAlmacen();
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


}
