package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.UnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.UnidadMedidaDAO;

import java.io.Serializable;
import java.util.List;
@Named
@ViewScoped
public class UnidadMedidaFrm extends DefaultFrm<UnidadMedida> implements Serializable {

    @Inject
    private transient UnidadMedidaDAO UDao;
    @Inject
    private transient TipoUnidadMedidaFrm tipoUnidadMedidaFrm;


    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Unidad Medida";

    private List<UnidadMedida> listaUnidadMedida;

    @Override
    protected InventarioDefaultDataAccess<UnidadMedida> getDao() {
        return UDao;
    }

    @Override
    protected UnidadMedida buscarRegistroPorId(Object id) throws IllegalAccessException {
        return UDao.buscarPorId(id);
    }

    @Override
    protected UnidadMedida nuevoRegistro() {
        UnidadMedida unidadMedida= new UnidadMedida();
        unidadMedida.setIdTipoUnidadMedida(this.tipoUnidadMedidaFrm.getRegistro());
        if(unidadMedida.getIdTipoUnidadMedida()==null){
            System.out.println("ID tipo unidad medida nulo");
        }
        else {
            System.out.println("✅ Tipo asignado en crearInstanciaVacia: " + unidadMedida.getIdTipoUnidadMedida().getNombre());
        }
        return unidadMedida;
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        listaUnidadMedida = CargarDatos(0, 5);
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }
    public List<UnidadMedida> buscarPorTipo(Integer idTipoU){
        return UDao.findLikeConsulta(idTipoU);
    }

    public String getNombreBean() {
        return nombreBean;
    }

}
