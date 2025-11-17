package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Venta;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.VentaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.VentaDetalleDAO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class VentaDetalleFrm extends DefaultFrm<VentaDetalle> implements Serializable{

    @Inject
    private transient VentaDetalleDAO taDao;

    @Inject
    private transient VentaFrm ventaFrm;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Venta";

    @Override
    protected InventarioDefaultDataAccess<VentaDetalle> getDao() {
        return taDao;
    }

    @Override
    protected VentaDetalle buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }


    @Override
    protected VentaDetalle nuevoRegistro() {
        VentaDetalle detalle = new VentaDetalle();
        detalle.setId(UUID.randomUUID());
        if (ventaFrm.getRegistro() != null && ventaFrm.getRegistro().getId() != null) {
            detalle.setIdVenta(ventaFrm.getRegistro());
            System.out.println(" Venta asignada: " + ventaFrm.getRegistro().getId());
        }

        detalle.setCantidad(BigDecimal.ZERO);
        detalle.setPrecio(BigDecimal.ZERO);
        detalle.setEstado("ACTIVO");
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
    public List<VentaDetalle> buscarPorVenta(UUID id) {
        return taDao.buscarPorVenta(id);
    }
    public BigDecimal calcularTotalVenta(UUID idVenta) {
        return taDao.calcularTotalVenta(idVenta);
    }

    public BigDecimal calcularSubtotal(VentaDetalle detalle) {
        return taDao.calcularSubtotal(detalle);
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }


}
