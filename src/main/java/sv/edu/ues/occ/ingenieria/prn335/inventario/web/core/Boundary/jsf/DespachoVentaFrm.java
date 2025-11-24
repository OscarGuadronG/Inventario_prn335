package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Venta;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.VentaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class DespachoVentaFrm extends DefaultFrm<Venta> implements Serializable {

    @Inject
    private transient VentaDAO ventaDAO;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "RecepcionVenta";

    @Override
    protected InventarioDefaultDataAccess<Venta> getDao() {
        return ventaDAO;
    }

    @Override
    protected Venta buscarRegistroPorId(Object id) {
        try {
            return ventaDAO.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Venta nuevoRegistro() {
        return new Venta();
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar() {
        inicializarRegistro();
    }

    @Override
    protected List<Venta> CargarDatos(int first, int pageSize) {
        return ventaDAO.buscarPagadasParaRecepcion(first, pageSize);
    }

    @Override
    protected int ContarDatos() {
        return ventaDAO.contarPagadasParaRecepcion().intValue();
    }

    public String getNumeroRandom() {
        return UUID.randomUUID().toString();
    }

    public void actualizarTabla() {
        System.out.println("Actualizando tabla de recepcion de ventas...");
    }
}