package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Proveedor;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.*;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Almacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO;

import java.io.Serializable;
import java.util.List;

@Named
@ViewScoped
public class ProveedorFrm extends DefaultFrm<Proveedor> implements Serializable{


    @Inject
    private transient sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.AlmacenDAO taDao;

    @Inject
    private transient ProveedorDAO ProveedorDAO;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Proveedor";

    private List<Proveedor> listaProveedor;

    @Override
    protected FacesContext getFacesContext() {return facesContext;}

    @Override
    protected InventarioDefaultDataAccess<Proveedor> getDao() {return ProveedorDAO;}

    @Override
    protected Proveedor nuevoRegistro() {return new Proveedor();}

    @Override
    protected Proveedor buscarRegistroPorId(Object id) {
        try{
            return ProveedorDAO.buscarPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
    }

  //metodos especificos
  public List<Proveedor> findActivos() {
      return ProveedorDAO.findActivos();
  }

    public String getNombreBean() {return nombreBean;}

    public void setNombreBean(String nombreBean) {this.nombreBean = nombreBean;}
}
