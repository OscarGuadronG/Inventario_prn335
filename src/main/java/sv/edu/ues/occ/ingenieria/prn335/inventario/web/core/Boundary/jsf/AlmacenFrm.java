package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class AlmacenFrm extends DefaultFrm<Almacen> implements Serializable{
    private static final long serialVersionUID = 1L;

    @Inject
    private transient sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO taDao;

    @Inject
    private transient TipoAlmacenDAO tipoAlmacenDAO;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Almacen";

    private List<Almacen> listaAlmacenes;

    @Override
    protected FacesContext getFacesContext() {return facesContext;}

    @Override
    protected InventarioDefaultDataAccess<Almacen> getDao() {return taDao;}

    @Override
    protected Almacen nuevoRegistro() {return new Almacen();}

    @Override
    protected Almacen buscarRegistroPorId(Object id) {
        try{
            return taDao.buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        listaAlmacenes = CargarDatos(0, 5);
    }

    //Metodos extras
    public List<TipoAlmacen> completarTipoAlmacen(String consulta) throws Exception {
        return tipoAlmacenDAO.findLikeConsulta(consulta);
    }

    public List<Almacen> getListaAlmacenes() {return listaAlmacenes;}

    public void setListaAlmacenes(List<Almacen> listaAlmacenes) {this.listaAlmacenes = listaAlmacenes;}

    public String getNombreBean() {return nombreBean;}

    public void setNombreBean(String nombreBean) {this.nombreBean = nombreBean;}

    public List<Almacen> findLikeConsulta(String consulta) {
        return taDao.findLikeConsulta(consulta);
    }
}
