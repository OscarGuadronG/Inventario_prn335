package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;

import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoUnidadMedidaDAO;


import java.io.Serializable;
import java.util.List;
@ViewScoped
@Named
public class CaracteristicaFrm extends DefaultFrm<Caracteristica> implements Serializable {



    @Inject
    private transient CaracteristicaDAO taDao;
    @Inject
    private transient TipoUnidadMedidaDAO tipoUnidadMedidaDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Caracteristica";

    @Override
    protected InventarioDefaultDataAccess<Caracteristica> getDao() {
        return taDao;
    }

    @Override
    protected Caracteristica buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected Caracteristica nuevoRegistro() {
        return new Caracteristica();
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();

    }

    public List<TipoUnidadMedida> completarTipounidadMedida(String consulta) throws Exception {
        return tipoUnidadMedidaDao.findLikeConsulta(consulta);
    }
    public List<Caracteristica> getListaCompleta() {
        return taDao.getListaCompleta();
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }
}
