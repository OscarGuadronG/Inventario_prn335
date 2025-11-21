package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.KardexDetalle;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Kardex;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.KardexDetalleDAO;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class KardexDetalleFrm extends DefaultFrm<KardexDetalle> implements Serializable {

    @Inject
    private transient KardexDetalleDAO kardexDetalleDAO;

    @Inject
    FacesContext facesContext;

    private Kardex kardexSeleccionado;


    protected String nombreBean = "KardexDetalle";

    @Override
    protected InventarioDefaultDataAccess<KardexDetalle> getDao() {
        return kardexDetalleDAO;
    }

    @Override
    protected KardexDetalle buscarRegistroPorId(Object id) throws IllegalAccessException {
        return kardexDetalleDAO.buscarPorId(id);
    }

    @Override
    protected KardexDetalle nuevoRegistro() {
        KardexDetalle detalle = new KardexDetalle();
        detalle.setId(UUID.randomUUID());
        detalle.setActivo(true);
        if (kardexSeleccionado != null) {
            detalle.setIdKardex(kardexSeleccionado);
        }
        return detalle;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
    }

//ESPECIFICOS
    public List<KardexDetalle> cargarDetallesPorKardex(Kardex kardex) {
        this.kardexSeleccionado = kardex;
        if (kardex != null) {
           return  kardexDetalleDAO.findByKardex(kardex);
        } else {
            System.err.println("ERROR: Kardex no encontrado");
            return null;
        }
    }



    public Kardex getKardexSeleccionado() {
        return kardexSeleccionado;
    }

    public void setKardexSeleccionado(Kardex kardexSeleccionado) {
        this.kardexSeleccionado = kardexSeleccionado;
    }

    public String getNombreBean() {
        return nombreBean;
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }
}