package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ProductoTipoProductoDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class ProductoTipoProductoFrm extends DefaultFrm<ProductoTipoProducto> implements Serializable{


    @Inject
    private transient ProductoTipoProductoDAO taDao;

    @Inject
    private transient ProductoFrm productoFrm;

    @Inject
    private transient ProductoTipoProductoCaracteristicaFrm productoTipoProductoCaracteristicaFrm;

    @Inject
    FacesContext facesContext;

    @PersistenceContext(unitName = "inventarioPU")
    private transient EntityManager em;

    protected String nombreBean = "ProductoTipoProducto";

    @Override
    protected InventarioDefaultDataAccess<ProductoTipoProducto> getDao() {
        return taDao;
    }

    @Override
    protected ProductoTipoProducto buscarRegistroPorId(Object id) throws IllegalAccessException {
        return taDao.buscarPorId(id);
    }


    @Override
    protected ProductoTipoProducto nuevoRegistro() {
        ProductoTipoProducto productoTipoProducto = new ProductoTipoProducto();
        productoTipoProducto.setId(UUID.randomUUID());
        if (productoFrm != null && productoFrm.getRegistro() != null && productoFrm.getRegistro().getId() != null) {
            productoTipoProducto.setIdProducto(productoFrm.getRegistro());
            System.out.println("Producto asignado: " + productoFrm.getRegistro().getId());
        }
        productoTipoProducto.setFechaCreacion(OffsetDateTime.now());
        productoTipoProducto.setActivo(true);
        return productoTipoProducto;
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
    //ESPECIFICO
    public List<ProductoTipoProducto> findByProducto(UUID idProducto) {
        return taDao.findByProducto(idProducto);
    }


    @Override
    public void btnGuardar() throws IllegalAccessException {
        System.out.println("=== INICIANDO btnGuardar ProductoTipoProducto ===");
        String texto;
        boolean isNuevo = (estadoCrud == ESTADO_CRUD.Crear);

        try {
            // 1. Primero guardar el ProductoTipoProducto
            if (isNuevo) {
                System.out.println("Creando ProductoTipoProducto...");
                getDao().crear(registro);
                texto = getMessage("btn.guardar.msg1");
                getFacesContext().addMessage(null, new FacesMessage(texto));

            } else if (estadoCrud == ESTADO_CRUD.Modificar) {
                System.out.println("Modificando ProductoTipoProducto...");
                getDao().modificar(registro);
                texto = getMessage("btn.guardar.msg2");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            }

            // 2. Luego guardar las características - SOLO para modificación
            // Para creación, las características se guardarán en una operación separada
            if (estadoCrud == ESTADO_CRUD.Modificar && registro != null && registro.getId() != null) {
                System.out.println("Llamando a guardarCaracteristicas para modificación...");
                productoTipoProductoCaracteristicaFrm.guardarCaracteristicas();
                System.out.println("guardarCaracteristicas completado");
            }

            // 3. Resetear estado
            estadoCrud = ESTADO_CRUD.Nada;

            // Si es creación, recargar el registro para asegurar que tenemos todas las relaciones
            if (isNuevo) {
                registro = getDao().buscarPorId(registro.getId());
            }

            PrimeFaces.current().resetInputs(":frmFormulario");

        } catch (Exception e) {
            System.out.println("ERROR en btnGuardar: " + e.getMessage());
            e.printStackTrace();
            texto = getMessage("btn.guardar.msg3") + " " + getMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
        }
    }
    public void onTipoProductoChange() {
        if (registro.getIdTipoProducto() != null) {
            productoTipoProductoCaracteristicaFrm.inicializarPickList();
        }
    }

}
