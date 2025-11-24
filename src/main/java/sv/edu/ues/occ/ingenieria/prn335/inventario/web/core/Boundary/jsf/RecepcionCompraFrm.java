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
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class RecepcionCompraFrm extends DefaultFrm<Compra> implements Serializable {

    @Inject
    private transient CompraDAO compraDAO;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "RecepcionCompra";

    @Override
    protected InventarioDefaultDataAccess<Compra> getDao() {
        return compraDAO;
    }

    @Override
    protected Compra buscarRegistroPorId(Object id) {
        try {
            return compraDAO.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected Compra nuevoRegistro() {
        return new Compra();
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
    protected List<Compra> CargarDatos(int first, int pageSize) {
        return compraDAO.buscarPagadasParaRecepcion(first, pageSize);
    }

    @Override
    protected int ContarDatos() {
        return compraDAO.contarPagadasParaRecepcion().intValue();
    }

    public String getNumeroRandom() {
        return UUID.randomUUID().toString();
    }

    public void actualizarTabla() {
        System.out.println("Actualizando tabla de recepcion de productos...");
    }
}