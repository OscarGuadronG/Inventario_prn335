package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoUnidadMedidaDAO;

import java.io.Serializable;
import java.util.List;
@Named
@ViewScoped
public class TipoUnidadMedidaFrm extends DefaultFrm<TipoUnidadMedida> implements Serializable {



    @Inject
    private transient TipoUnidadMedidaDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de Unidad Medida";

    private List<TipoUnidadMedida> listaTipoUnidadMedida;

    @Override
    protected InventarioDefaultDataAccess<TipoUnidadMedida> getDao() {
        return taDao;
    }

    @Override
    protected TipoUnidadMedida buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected TipoUnidadMedida nuevoRegistro() {
        TipoUnidadMedida TipoUnidadMedida= new TipoUnidadMedida();
        TipoUnidadMedida.setActivo(true);
        return TipoUnidadMedida;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        listaTipoUnidadMedida = CargarDatos(0, 5);
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }



}
