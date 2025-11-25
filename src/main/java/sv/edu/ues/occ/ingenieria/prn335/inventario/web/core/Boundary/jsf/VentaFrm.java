package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.ws.VentaEndpoint;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Venta;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.NotificadorVenta;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.VentaDAO;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class VentaFrm extends DefaultFrm<Venta> implements Serializable{

    @Inject
    private transient VentaDAO taDao;

    @Inject
    FacesContext facesContext;

    @Inject
    private transient VentaEndpoint ventaEndpoint;

    @Inject
    private transient NotificadorVenta notificadorVenta;

    protected String nombreBean = "Venta";

    @Override
    protected InventarioDefaultDataAccess<Venta> getDao() {
        return taDao;
    }

    @Override
    protected Venta buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }


    @Override
    protected Venta nuevoRegistro() {
        Venta venta = new Venta();
        venta.setId(UUID.randomUUID());
        venta.setFecha(OffsetDateTime.now());
        return venta;
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

    public void notificarCambioVenta() throws IllegalAccessException {
        if (this.registro != null && this.registro.getId() != null) {
            this.registro.setEstado("CERRADA");

            if (this.registro.getId() != null) {
                ventaEndpoint.notificarCierreVenta(this.registro.getId().toString());
                notificadorVenta.notificarCambioVenta("Venta Cerrada: " + this.registro.getId());
            }
        }
        else {
            System.out.println("No hay registro");
        }
        super.btnGuardar();
    }
}
