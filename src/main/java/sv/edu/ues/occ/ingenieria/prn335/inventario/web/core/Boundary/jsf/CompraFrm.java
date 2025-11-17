package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Compra;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CompraDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;

@Named
@ViewScoped
public class CompraFrm extends DefaultFrm<Compra> implements Serializable{



    @Inject
    private transient CompraDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Compra";

    @Override
    protected InventarioDefaultDataAccess<Compra> getDao() {
        return taDao;
    }

    @Override
    protected Compra buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected Compra nuevoRegistro() {
        Compra compra = new Compra();
        compra.setFecha(OffsetDateTime.now());
        return compra;
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
