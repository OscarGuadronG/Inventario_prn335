package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.UnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoUnidadMedidaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.UnidadMedidaDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
@Named
@ViewScoped
public class TipoUnidadMedidaFrm extends DefaultFrm<TipoUnidadMedida> implements Serializable {

    @Inject
    private transient TipoUnidadMedidaDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de Unidad Medida";

    private List<TipoUnidadMedida> listaTipoUnidadMedida;

    @Override
    protected InventarioDefaultDataAccess<TipoUnidadMedida> getDao() {
        return taDao;
    }

    @Override
    protected TipoUnidadMedida buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected TipoUnidadMedida nuevoRegistro() {
        TipoUnidadMedida TipoUnidadMedida= new TipoUnidadMedida();
        TipoUnidadMedida.setActivo(true);
        return TipoUnidadMedida;
    }

    @Override
    public void selectionHandler(SelectEvent<TipoUnidadMedida> event) {
        cargarDetalles();
        super.selectionHandler(event);
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        cargarDetalles();
        detalle = new UnidadMedida();
        listaDetalle = new ArrayList<>();
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }

    //--------------------Limpiar SubFormulario-----------------------------//
    public void resetSubFormulario() {
        PrimeFaces.current().resetInputs(":frmFormulario:panelEdicionC");
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
        listaDetalle = new ArrayList<>();
        super.btnEliminar();
    }
    //--------------------Elementos SubFormularios-----------------------------//

    protected String detalleBean = "Unidad de Medida";
    protected UnidadMedida detalle;
    protected List<UnidadMedida> listaDetalle;
    protected ESTADO_CRUD estadoSubFormulario = ESTADO_CRUD.Nada;
    @Inject
    protected UnidadMedidaDAO detalleDao;

    public void cargarDetalles() {
        if (registro != null && registro.getId() != null) {
            this.listaDetalle = detalleDao.findLikeConsulta(registro.getId());
        } else {
            this.listaDetalle = new ArrayList<>();
        }
    }

    //---------------------------SubBotones CRUD-------------------------------//
    public void subSelectionHandler(SelectEvent<UnidadMedida> event) {
        this.detalle = event.getObject();
        this.estadoSubFormulario = ESTADO_CRUD.Modificar;
    }

    public void subBtnGuardar() throws IllegalAccessException {
        String texto;
        try {
            if (estadoSubFormulario == ESTADO_CRUD.Crear) {
                detalle.setIdTipoUnidadMedida(registro);
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
            detalle = new UnidadMedida();
            PrimeFaces.current().resetInputs(":panelEdicionC");
        } catch (Exception e) {
            texto = getMessage("btn.guardar.msg3") + " " + getMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
        }
    }

    public void SubBtnNuevo() {
        detalle = new UnidadMedida();
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
            detalle = new UnidadMedida();
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
        detalle = new UnidadMedida();
        String texto = getMessage("btn.cancelar.msg");
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    //sub Getters and Setters
    public String getDetalleBean() {
        return detalleBean;
    }

    public void setDetalleBean(String detalleBean) {
        this.detalleBean = detalleBean;
    }

    public UnidadMedida getDetalle() {
        return detalle;
    }

    public void setDetalle(UnidadMedida detalle) {
        this.detalle = detalle;
    }

    public List<UnidadMedida> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<UnidadMedida> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public ESTADO_CRUD getEstadoSubFormulario() {
        return estadoSubFormulario;
    }

    public void setEstadoSubFormulario(ESTADO_CRUD estadoSubFormulario) {
        this.estadoSubFormulario = estadoSubFormulario;
    }
}
