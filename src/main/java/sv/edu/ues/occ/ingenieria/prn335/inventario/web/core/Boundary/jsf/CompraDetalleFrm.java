package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.CompraDetalle;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CompraDetalleDAO;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class CompraDetalleFrm extends DefaultFrm<CompraDetalle> implements Serializable {

    @Inject
    private transient CompraDetalleDAO taDao;

    @Inject
    private transient CompraFrm compraFrm;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Compra Detalle";

    @Override
    protected InventarioDefaultDataAccess<CompraDetalle> getDao() {
        return taDao;
    }

    @Override
    protected CompraDetalle buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected CompraDetalle nuevoRegistro() {
        CompraDetalle detalle = new CompraDetalle();
        detalle.setId(UUID.randomUUID());
        if (compraFrm.getRegistro() != null && compraFrm.getRegistro().getId() != null) {
            detalle.setCompra(compraFrm.getRegistro());
            System.out.println(" Compra asignada: " + compraFrm.getRegistro().getId());
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

    public List<CompraDetalle> buscarPorCompra(Integer idCompra) {
        return taDao.buscarPorCompra(idCompra);
    }

    public BigDecimal calcularTotalCompra(Integer idCompra) {
        return taDao.calcularTotalCompra(idCompra);
    }

    public BigDecimal calcularSubtotal(CompraDetalle detalle) {
        return taDao.calcularSubtotal(detalle);
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }

    public List<CompraDetalle> autocompleteCompraDetalle(String consulta) {
        return taDao.findLikeConsulta(consulta);
    }
}