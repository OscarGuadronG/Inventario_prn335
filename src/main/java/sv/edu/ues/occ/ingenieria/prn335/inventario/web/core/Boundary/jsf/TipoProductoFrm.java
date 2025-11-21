package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.TreeNode;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.CaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoCaracteristicaDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.services.TipoProductoService;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@ViewScoped
public class TipoProductoFrm extends DefaultFrm<TipoProducto> implements Serializable {
    @Inject
    private transient TipoProductoDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Tipo de producto";

    @Inject
    private TipoProductoService servicio;

    private TreeNode<TipoProducto> nodoSeleccionado;

    private TreeNode<TipoProducto> arbol;

    @Override
    protected InventarioDefaultDataAccess<TipoProducto> getDao() {
        return taDao;
    }

    @Override
    protected TipoProducto buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }

    @Override
    protected TipoProducto nuevoRegistro() {
        return new TipoProducto();
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar() {
        this.arbol = getArbol();
        inicializarRegistro();
        detalle = new TipoProductoCaracteristica();
        listaDetalle = new ArrayList<>();
    }

    //Reescritura del manejador de selección
    public void oneSelectNodo(NodeSelectEvent event) {
        this.nodoSeleccionado = event.getTreeNode();
        this.registro = nodoSeleccionado.getData();
        this.estadoCrud = ESTADO_CRUD.Modificar;
        cargarCaracteristicas();
    }

    public TreeNode<TipoProducto> getArbol() {
        return servicio.construirArbolDesdePadres();
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public String getNombreBean() {
        return nombreBean;
    }

    public TreeNode<TipoProducto> getNodoSeleccionado() {
        return nodoSeleccionado;
    }

    public void setNodoSeleccionado(TreeNode<TipoProducto> nodoSeleccionado) {
        this.nodoSeleccionado = nodoSeleccionado;
    }

    public List<TipoProducto> completarTipoProducto(String consulta) throws Exception {
        return taDao.findLikeConsulta(consulta);
    }

    public String armarJerarquia(TipoProducto tipo) {
        if (tipo == null) return "";
        String nombre = tipo.getNombre();

        TipoProducto padre = tipo.getIdTipoProductoPadre();
        while (padre != null) {
            nombre = padre.getNombre() + " > " + nombre;
            padre = padre.getIdTipoProductoPadre();
        }

        return nombre;
    }

    //--------------------limpiar subFormulario---------------------------
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
        listaDetalle = new ArrayList<>();
        super.btnEliminar();
    }

    //------------------------Elementos de subFormulario ------------------------------
    protected String nombreSubFormulario = "Característica de Tipo de Producto";
    protected TipoProductoCaracteristica detalle;
    protected ESTADO_CRUD estadoSubFormulario = ESTADO_CRUD.Nada;
    protected List<TipoProductoCaracteristica> listaDetalle;
    @Inject
    protected TipoProductoCaracteristicaDAO detalleDao;
    @Inject
    protected CaracteristicaDAO caracteristicaDao;

    public void cargarCaracteristicas() {
        if (registro != null && registro.getId() != null) {
            this.listaDetalle = detalleDao.findByConsulta(registro.getId());
        } else {
            this.listaDetalle = new ArrayList<>();
        }
    }

    //SubBotones
    public void subSelectionHandler(SelectEvent<TipoProductoCaracteristica> event) {
        this.detalle = event.getObject();
        this.estadoSubFormulario = ESTADO_CRUD.Modificar;
    }

    public void subBtnGuardar() throws IllegalAccessException {
        String texto;
        try {
            if (estadoSubFormulario == ESTADO_CRUD.Crear) {
                detalle.setIdTipoProducto(registro);
                detalleDao.crear(detalle);
                texto = getMessage("btn.guardar.msg1");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            } else if (estadoSubFormulario == ESTADO_CRUD.Modificar) {
                detalleDao.modificar(detalle);
                texto = getMessage("btn.guardar.msg2");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            }
            cargarCaracteristicas();
            estadoSubFormulario = ESTADO_CRUD.Nada;
            detalle = new TipoProductoCaracteristica();
            PrimeFaces.current().resetInputs(":panelEdicionC");
        } catch (Exception e) {
            texto = getMessage("btn.guardar.msg3") + " " + getMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
        }
    }

    public void SubBtnNuevo() {
        detalle = new TipoProductoCaracteristica();
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
            detalle = new TipoProductoCaracteristica();
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
        detalle = new TipoProductoCaracteristica();
        String texto = getMessage("btn.cancelar.msg");
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    public List<Caracteristica> autocompletarCaracteristica(String texto) {
        List<Integer> idsOmitir = listaDetalle.stream()
                .filter(d -> d.getIdCaracteristica() != null)
                .map(d -> d.getIdCaracteristica().getId())
                .toList();

        try {
            return caracteristicaDao.findLikeConsulta(texto, idsOmitir);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    // SubGetters and Setters
    public TipoProductoCaracteristicaDAO getDetalleDao() {
        return detalleDao;
    }

    public void setDetalleDao(TipoProductoCaracteristicaDAO detalleDao) {
        this.detalleDao = detalleDao;
    }

    public List<TipoProductoCaracteristica> getListaDetalle() {
        return listaDetalle;
    }

    public void setListaDetalle(List<TipoProductoCaracteristica> listaDetalle) {
        this.listaDetalle = listaDetalle;
    }

    public ESTADO_CRUD getEstadoSubFormulario() {
        return estadoSubFormulario;
    }

    public void setEstadoSubFormulario(ESTADO_CRUD estadoSubFormulario) {
        this.estadoSubFormulario = estadoSubFormulario;
    }

    public TipoProductoCaracteristica getDetalle() {
        return detalle;
    }

    public void setDetalle(TipoProductoCaracteristica detalle) {
        this.detalle = detalle;
    }

    public String getNombreSubFormulario() {
        return nombreSubFormulario;
    }

    public void setNombreSubFormulario(String nombreSubFormulario) {
        this.nombreSubFormulario = nombreSubFormulario;
    }
}
