package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.services.TipoProductoService;

import java.io.Serializable;
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
    public void inicializar(){
        this.arbol = getArbol();
        inicializarRegistro();
    }
    //Reescritura del manejador de selección
    public void oneSelectNodo(NodeSelectEvent event) {
        this.nodoSeleccionado = event.getTreeNode();
        this.registro = nodoSeleccionado.getData();
        this.estadoCrud = ESTADO_CRUD.Modificar;
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
}
