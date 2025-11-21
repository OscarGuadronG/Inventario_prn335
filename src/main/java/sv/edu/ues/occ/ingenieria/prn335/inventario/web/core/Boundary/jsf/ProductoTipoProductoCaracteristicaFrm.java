package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.primefaces.model.DualListModel;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.ProductoTipoProductoCaracteristicaDAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Named
@ViewScoped
public class ProductoTipoProductoCaracteristicaFrm extends DefaultFrm<ProductoTipoProductoCaracteristica> implements Serializable {

    @Inject
    private transient ProductoTipoProductoCaracteristicaDAO taDao;

    @Inject
    private transient ProductoTipoProductoFrm productoTipoProductoFrm;

    @Inject
    private transient FacesContext facesContext;

    private DualListModel<TipoProductoCaracteristica> caracteristicasModel;

    @PostConstruct
    public void inicializar(){
        inicializarRegistro();
        inicializarPickList();
    }

    public void inicializarPickList() {
        System.out.println("=== INICIALIZANDO PICKLIST ===");

        List<TipoProductoCaracteristica> disponibles = new ArrayList<>();
        List<TipoProductoCaracteristica> asignadas = new ArrayList<>();

        if (productoTipoProductoFrm.getRegistro() != null) {
            // Si es un registro nuevo (sin ID), mostrar todas las características del tipo de producto
            if (productoTipoProductoFrm.getRegistro().getId() == null) {
                if (productoTipoProductoFrm.getRegistro().getIdTipoProducto() != null) {
                    Long idTipoProducto = productoTipoProductoFrm.getRegistro().getIdTipoProducto().getId();
                    System.out.println("Nuevo registro - ID TipoProducto: " + idTipoProducto);

                    // Obtener todas las características del tipo de producto
                    disponibles = taDao.findCaracteristicasByTipoProducto(idTipoProducto);
                    asignadas = new ArrayList<>(); // Para nuevo registro, no hay características asignadas
                }
            } else {
                // Registro existente - usar la lógica original
                UUID idProductoTipoProducto = productoTipoProductoFrm.getRegistro().getId();
                System.out.println("Registro existente - ID ProductoTipoProducto: " + idProductoTipoProducto);

                if (productoTipoProductoFrm.getRegistro().getIdTipoProducto() != null) {
                    Long idTipoProducto = productoTipoProductoFrm.getRegistro().getIdTipoProducto().getId();
                    disponibles = taDao.findCaracteristicasDisponibles(idProductoTipoProducto, idTipoProducto);
                    asignadas = taDao.findCaracteristicasAsignadas(idProductoTipoProducto);
                }
            }
        }

        System.out.println("Disponibles: " + disponibles.size());
        System.out.println("Asignadas: " + asignadas.size());

        caracteristicasModel = new DualListModel<>(disponibles, asignadas);
    }

    public void guardarCaracteristicas() {
        System.out.println("=== GUARDANDO CARACTERISTICAS ===");
        try {
            if (productoTipoProductoFrm.getRegistro() != null && productoTipoProductoFrm.getRegistro().getId() != null) {
                UUID idProductoTipoProducto = productoTipoProductoFrm.getRegistro().getId();
                System.out.println("ID ProductoTipoProducto: " + idProductoTipoProducto);

                // Eliminar características desasignadas
                List<TipoProductoCaracteristica> anteriores = taDao.findCaracteristicasAsignadas(idProductoTipoProducto);
                System.out.println("Características anteriores: " + anteriores.size());

                for (TipoProductoCaracteristica caracteristica : anteriores) {
                    if (!caracteristicasModel.getTarget().contains(caracteristica)) {
                        System.out.println("Eliminando característica: " + caracteristica.getId());
                        taDao.eliminarPorProductoYCaracteristica(idProductoTipoProducto, caracteristica.getId());
                    }
                }

                // Agregar nuevas características asignadas
                for (TipoProductoCaracteristica caracteristica : caracteristicasModel.getTarget()) {
                    if (!anteriores.contains(caracteristica)) {
                        System.out.println("Creando nueva relación para: " + caracteristica.getId());
                        ProductoTipoProductoCaracteristica nuevaRelacion = new ProductoTipoProductoCaracteristica();
                        nuevaRelacion.setId(UUID.randomUUID());
                        nuevaRelacion.setIdProductoTipoProducto(productoTipoProductoFrm.getRegistro());
                        nuevaRelacion.setIdTipoProductoCaracteristica(caracteristica);
                        taDao.crear(nuevaRelacion);
                    }
                }

                System.out.println("Características guardadas exitosamente");
            }
        } catch (Exception e) {
            System.out.println("ERROR en guardarCaracteristicas: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    protected FacesContext getFacesContext() {
        return facesContext;
    }

    @Override
    protected InventarioDefaultDataAccess<ProductoTipoProductoCaracteristica> getDao() {
        return taDao;
    }

    @Override
    protected ProductoTipoProductoCaracteristica nuevoRegistro() {
        ProductoTipoProductoCaracteristica relacion = new ProductoTipoProductoCaracteristica();
        relacion.setId(UUID.randomUUID());
        if (productoTipoProductoFrm.getRegistro() != null) {
            relacion.setIdProductoTipoProducto(productoTipoProductoFrm.getRegistro());
        }
        return relacion;
    }

    @Override
    protected ProductoTipoProductoCaracteristica buscarRegistroPorId(Object id) throws IllegalAccessException {
        if (id instanceof UUID) {
            return taDao.buscarPorId(id);
        } else if (id instanceof String) {
            return taDao.buscarPorId(UUID.fromString((String) id));
        } else {
            throw new IllegalAccessException("Tipo de ID no soportado: " + (id != null ? id.getClass().getName() : "null"));
        }
    }

    public DualListModel<TipoProductoCaracteristica> getCaracteristicasModel() {
        return caracteristicasModel;
    }

    public void setCaracteristicasModel(DualListModel<TipoProductoCaracteristica> caracteristicasModel) {
        this.caracteristicasModel = caracteristicasModel;
    }
}