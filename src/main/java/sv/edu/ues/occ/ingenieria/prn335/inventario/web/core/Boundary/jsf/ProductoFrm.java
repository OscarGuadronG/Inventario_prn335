package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.*;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class ProductoFrm extends DefaultFrm<Producto> implements Serializable {
    @Inject
    private transient ProductoDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Producto";

    @Override
    protected InventarioDefaultDataAccess<Producto> getDao() {
        return taDao;
    }

    @Override
    protected Producto buscarRegistroPorId(Object id){
        try {
            return taDao.buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    protected Producto nuevoRegistro() {
        Producto producto = new Producto();
        producto.setId(UUID.randomUUID());
        return producto;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    public void selectionHandler(SelectEvent<Producto> event) {
        cargarDetalles();
        super.selectionHandler(event);
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        detalle = new ProductoTipoProducto();
        listaDetalle = new ArrayList<>();
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }
//especificos
    public List<Producto> findProductosActivos() {
        return taDao.findProductosActivos();
    }
    public List<Producto> autocompleteProducto(String consulta) {
        return taDao.findLikeConsulta(consulta);
    }
    //-------------------------------Limpiar SubFormulario-------------------------------------
    public void resetSubFormulario() {
        PrimeFaces.current().resetInputs(":panelEdicionC");
        estadoSubFormulario = ESTADO_CRUD.Nada;
        detalle = null;
    }
    @Override
    public void btnGuardar() throws IllegalAccessException {
        resetSubFormulario();
        listaDetalle = new ArrayList<>();
        super.btnGuardar();
    }
    @Override
    public void btnCancelar() {
        resetSubFormulario();
        listaDetalle = new ArrayList<>();
        super.btnCancelar();
    }
    @Override
    public void btnEliminar(){
        resetSubFormulario();
        cargarDetalles();
        listaDetalle = new ArrayList<>();
        super.btnEliminar();
    }

    //-----------------------------Elementos subFormularios------------------------------------
    protected String detalleBean = "Tipos de Producto Perteneciente";
    protected ProductoTipoProducto detalle;
    protected List<ProductoTipoProducto> listaDetalle;
    protected ESTADO_CRUD estadoSubFormulario = ESTADO_CRUD.Nada;
    @Inject
    protected ProductoTipoProductoDAO detalleDao;
    @Inject
    protected TipoProductoDAO tpDao;

    public void cargarDetalles() {
        if (registro != null && registro.getId() != null) {
            this.listaDetalle = detalleDao.findByProducto(registro.getId());
        } else {
            this.listaDetalle = new ArrayList<>();
        }
    }
    public List<TipoProducto> completarTipoProducto(String consulta) throws Exception {
        return tpDao.findLikeConsulta(consulta);
    }
    //---------------------------SubBotones CRUD-----------------------------
    public void subSelectionHandler(SelectEvent<ProductoTipoProducto> event) {
        this.detalle = event.getObject();
        this.estadoSubFormulario = ESTADO_CRUD.Modificar;
    }

    public void subBtnGuardar() throws IllegalAccessException {
        String texto;
        try {
            if (estadoSubFormulario == ESTADO_CRUD.Crear) {
                detalle.setIdProducto(registro);
                detalle.setId(UUID.randomUUID());
                detalleDao.crear(detalle);
                texto = getMessage("btn.guardar.msg1");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            } else if (estadoSubFormulario == ESTADO_CRUD.Modificar) {
                detalleDao.modificar(detalle);
                texto = getMessage("btn.guardar.msg2");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            }
            cargarDetalles();
            estadoSubFormulario = ESTADO_CRUD.Nada;
            detalle = new ProductoTipoProducto();
            PrimeFaces.current().resetInputs(":panelEdicionC");
        } catch (Exception e) {
            texto = getMessage("btn.guardar.msg3") + " " + getMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
        }
    }

    public void SubBtnNuevo() {
        detalle = new ProductoTipoProducto();
        estadoSubFormulario = ESTADO_CRUD.Crear;
        String texto = getMessage("btn.nuevo.msg");
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    public void subBtnEliminar() {
        String texto;
        try {
            Object id = detalle.getClass().getMethod("getId").invoke(detalle);
            detalleDao.eliminar(id);
            texto = getMessage("btn.eliminar.msg");
            getFacesContext().addMessage(null, new FacesMessage(texto));
            detalle = new ProductoTipoProducto();
            estadoSubFormulario = ESTADO_CRUD.Nada;
        } catch (Exception e) {
            texto = getMessage("btn.eliminar.error") + " " + getMessage(e.getMessage());
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    texto, null));
        }
    }

    public void subBtnCancelar() {
        PrimeFaces.current().resetInputs(":panelEdicionC");
        estadoSubFormulario = ESTADO_CRUD.Nada;
        detalle = new ProductoTipoProducto();
        String texto = getMessage("btn.cancelar.msg");
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    //----------------------Sub Getters and setters---------------------------
    public String getDetalleBean() {
        return detalleBean;
    }

    public void setDetalleBean(String detalleBean) {
        this.detalleBean = detalleBean;
    }

    public ProductoTipoProducto getDetalle() {
        return detalle;
    }

    public void setDetalle(ProductoTipoProducto detalle) {
        this.detalle = detalle;
    }

    public List<ProductoTipoProducto> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<ProductoTipoProducto> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public ESTADO_CRUD getEstadoSubFormulario() {
        return estadoSubFormulario;
    }

    public void setEstadoSubFormulario(ESTADO_CRUD estadoSubFormulario) {
        this.estadoSubFormulario = estadoSubFormulario;
    }
}
