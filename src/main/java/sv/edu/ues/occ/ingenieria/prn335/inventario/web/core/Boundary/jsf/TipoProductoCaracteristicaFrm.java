package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;

import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoCaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
@Named
@ViewScoped
public class TipoProductoCaracteristicaFrm extends DefaultFrm<TipoProductoCaracteristica> implements Serializable {
    @Inject
    private transient TipoProductoCaracteristicaDAO taDao;
    @Inject
    private transient TipoProductoFrm tipoProductoFrm;
    @Inject
    private transient TipoProductoDAO tipoProductoDAO;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo Producto Caracteristica";

    @Override
    protected InventarioDefaultDataAccess<TipoProductoCaracteristica> getDao() {
        return taDao;
    }

    @Override
    protected TipoProductoCaracteristica buscarRegistroPorId(Object id) throws IllegalAccessException {
        Long longId;
        if (id instanceof Integer) {
            longId = ((Integer) id).longValue();
        } else if (id instanceof Long) {
            longId = (Long) id;
        } else {
            throw new IllegalAccessException("Tipo de ID no soportado: " + (id != null ? id.getClass().getName() : "null"));
        }
        return taDao.buscarPorId(longId);
    }
    public List<TipoProductoCaracteristica> findByTipoProductoId(Long idTipoProducto) {
        return taDao.findByTipoProductoId(idTipoProducto);
    }

    @Override
    protected TipoProductoCaracteristica nuevoRegistro() {
        TipoProductoCaracteristica nuevaRelacion = new TipoProductoCaracteristica();
        nuevaRelacion.setIdCaracteristica(new Caracteristica());
        nuevaRelacion.setFechaCreacion(OffsetDateTime.now());

        if (tipoProductoFrm.getRegistro() != null) {

            if (tipoProductoFrm.getRegistro().getId() != null) {
                try {
                    TipoProducto tipoPersistido = tipoProductoDAO.buscarPorId(tipoProductoFrm.getRegistro().getId());
                    nuevaRelacion.setIdTipoProducto(tipoPersistido);
                    System.out.println("Tipo asignado : " + tipoPersistido.getNombre());
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            } else {
                nuevaRelacion.setIdTipoProducto(tipoProductoFrm.getRegistro());
               }
        }
        return nuevaRelacion;
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
