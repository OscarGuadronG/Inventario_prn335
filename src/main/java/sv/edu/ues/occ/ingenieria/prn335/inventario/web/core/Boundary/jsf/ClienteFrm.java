package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ClienteDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class ClienteFrm extends DefaultFrm<Cliente> implements Serializable {
    private static final long serialVersionUID = 1L;
    //Objeto DAO para manejar la entidad TipoAlmacen
    @Inject
    private transient ClienteDAO taDao;

    @Inject
    FacesContext facesContext;

    protected String nombreBean = "Cliente";

    private List<Cliente> listaCliente;

    @Override
    protected FacesContext getFacesContext() {return facesContext;}

    @Override
    protected InventarioDefaultDataAccess<Cliente> getDao() {return taDao;}

    @Override
    protected Cliente nuevoRegistro() {
        Cliente c = new Cliente();
        c.setId(UUID.randomUUID());
        return c;
    }

    @Override
    protected Cliente buscarRegistroPorId(Object id) {
        try {
            return taDao.buscarPorId(id);
        } catch (Exception e) {
            return null;
        }
    }

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        listaCliente = CargarDatos(0, 5);
    }

    public String getNombreBean() {return nombreBean;}

    public void setNombreBean(String nombreBean) {this.nombreBean = nombreBean;}

    public List<Cliente> getListaCliente() {return listaCliente;}

    public void setListaCliente(List<Cliente> listaCliente) {this.listaCliente = listaCliente;}

    //Metodos adicionales o sobreescritos

    @Override
    public void btnGuardar() throws IllegalAccessException {
        String texto;
        if (estadoCrud == ESTADO_CRUD.Crear) {
            if (!taDao.DocumentoUnico(registro.getDui())){
                texto = getMessage("msg.dui.duplicado");
                facesContext.addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, texto, null));
                return;
            }
            if (!taDao.NitUnico(registro.getNit())){
                texto = getMessage("msg.nit.duplicado");
                facesContext.addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, texto, null));
                return;
            }
        } else {
            Cliente original = taDao.buscarPorId(registro.getId());

            if (registro.getDui() != null && !registro.getDui().equals(original.getDui()) && !taDao.DocumentoUnico(registro.getDui())) {
                texto = getMessage("msg.dui.duplicado");
                facesContext.addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR, texto, null));
                return;
            }

            if (registro.getNit() != null && !registro.getNit().equals(original.getNit()) && !taDao.NitUnico(registro.getNit())) {
                texto = getMessage("msg.nit.duplicado");
                facesContext.addMessage(null,
                        new jakarta.faces.application.FacesMessage(jakarta.faces.application.FacesMessage.SEVERITY_ERROR,
                                texto, null));
                return;
            }
        }
        super.btnGuardar();
    }

    public List<Cliente> findActivos() {
        return taDao.findActivos();
    }
}
