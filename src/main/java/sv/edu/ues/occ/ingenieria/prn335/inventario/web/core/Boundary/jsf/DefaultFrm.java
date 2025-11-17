package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.annotation.PostConstruct;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.FilterMeta;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;

import java.io.Serializable;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class DefaultFrm<T> implements Serializable {
    //Elementos
    protected LazyDataModel<T> modelo;
    protected ESTADO_CRUD estadoCrud = ESTADO_CRUD.Nada;
    protected String nombreBean;
    protected T registro;
    protected List<T> lista;
    protected int pageSize = 5;

    //Métodos abstractos
    protected abstract FacesContext getFacesContext();

    protected abstract InventarioDefaultDataAccess<T> getDao();

    protected abstract T nuevoRegistro();

    protected abstract T buscarRegistroPorId(Object id) throws IllegalAccessException;

    public String getNombreBean() {
        return nombreBean;
    }

    //Métodos de conteo y recopilación
    protected List<T> CargarDatos(int first, int pageSize) {
        return getDao().findRange(first, pageSize);
    }

    protected int ContarDatos() {
        return getDao().count();
    }

    @PostConstruct
    public void inicializarRegistro() {
        this.registro = nuevoRegistro();
        this.modelo = new LazyDataModel<T>() {
            @Override
            public T getRowData(String rowKey) {
                try {
                    //Parseo del rowKey al tipo adecuado
                    Object parsedKey = parseRowKey(rowKey);
                    return buscarRegistroPorId(parsedKey);
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public String getRowKey(T entity) {
                try {
                    Object id = entity.getClass().getMethod("getId").invoke(entity);
                    return id != null ? id.toString() : null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public int count(Map<String, FilterMeta> map) {
                try {
                    return ContarDatos();
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error en count()", e);
                    return 0;
                }
            }

            @Override
            public List<T> load(int first, int pageSize, Map<String, SortMeta> sortBy, Map<String, FilterMeta> filterBy) {
                try {
                    if (pageSize <= 0) {
                        pageSize = 5;
                    }
                    List<T> resultados = CargarDatos(first, pageSize);
                    int total = ContarDatos();
                    this.setRowCount(total);
                    // Guardar los datos cargados en el modelo
                    this.setWrappedData(resultados != null ? resultados : List.of());
                    return resultados != null ? resultados : List.of();
                } catch (Exception e) {
                    Logger.getLogger(getClass().getName()).log(Level.SEVERE, "Error en load()", e);
                    this.setRowCount(0);
                    return List.of();
                }
            }

            private Object parseRowKey(String rowKey) throws Exception {
                if (rowKey.matches("\\d+")) {
                    return Integer.valueOf(rowKey);
                } else {
                    try {
                        return UUID.fromString(rowKey);
                    } catch (IllegalArgumentException e) {
                        // No es un UUID, retornar como String
                        return rowKey;
                    }
                }
            }
        };
        try {
            this.modelo.setRowCount(ContarDatos());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void inicializarLista() {
    }

    //Metodo de Primefaces para seleccionar un registro
    public void selectionHandler(SelectEvent<T> event) {
        this.registro = event.getObject();
        this.estadoCrud = ESTADO_CRUD.Modificar;
        if(this.registro==null) {
            System.out.println(" REGISTRO SELECIONADO VACIO" );
        }
    }

    //Botones de CRUD
    public void btnGuardar() throws IllegalAccessException {
        String texto;
        try {
            if (estadoCrud == ESTADO_CRUD.Crear) {
                getDao().crear(registro);
                texto = getMessage("btn.guardar.msg1");
                getFacesContext().addMessage(null, new FacesMessage(texto));

            } else if (estadoCrud == ESTADO_CRUD.Modificar) {
                getDao().modificar(registro);
                texto = getMessage("btn.guardar.msg2");
                getFacesContext().addMessage(null, new FacesMessage(texto));
            }
            estadoCrud = ESTADO_CRUD.Nada;
            registro = nuevoRegistro();
            PrimeFaces.current().resetInputs(":frmFormulario");
        } catch (Exception e) {
            texto = getMessage("btn.guardar.msg3") + " " + getMessage(e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, texto, null));
            e.printStackTrace();
        }
    }


    public void btnNuevo() {
        registro = nuevoRegistro();
        estadoCrud = ESTADO_CRUD.Crear;
        if(this.registro != null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Nuevo", "Formulario listo"));
        }
    }

    public void btnEliminar() {
        String texto;
        try {
            Object id = registro.getClass().getMethod("getId").invoke(registro);
            getDao().eliminar(id);
            texto = getMessage("btn.eliminar.msg");
            getFacesContext().addMessage(null, new FacesMessage(texto));
            registro = nuevoRegistro();
            estadoCrud = ESTADO_CRUD.Nada;
        } catch (Exception e) {
            texto = getMessage("btn.eliminar.error") + " " + getMessage(e.getMessage());
            getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    texto, null));
            e.printStackTrace();
        }
    }

    public void btnCancelar() {
        PrimeFaces.current().resetInputs(":frmFormulario:panelEdicion");
        estadoCrud = ESTADO_CRUD.Nada;
        registro = nuevoRegistro();
        String texto = getMessage("btn.cancelar.msg");
        getFacesContext().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, texto, null));
    }

    //Metodo de traduccion de mensajes
    protected String getMessage(String clave) {
        FacesContext context = getFacesContext();
        if (context == null) {
            return clave + "??"; // Retorna la clave si el contexto no está disponible
        }
        Locale locale = context.getViewRoot().getLocale();
        try {
            ResourceBundle bundle = context.getApplication().getResourceBundle(context, "msg");
            return bundle.getString(clave);

        } catch (Exception e) {
            return clave + "??";
        }
    }

    //Getters y Setters
    public LazyDataModel<T> getModelo() {
        return modelo;
    }

    public void setModelo(LazyDataModel<T> modelo) {
        this.modelo = modelo;
    }

    public ESTADO_CRUD getEstadoCrud() {
        return estadoCrud;
    }

    public void setEstadoCrud(ESTADO_CRUD estadoCrud) {
        this.estadoCrud = estadoCrud;
    }

    public void setNombreBean(String nombreBean) {
        this.nombreBean = nombreBean;
    }

    public T getRegistro() {
        return registro;
    }

    public void setRegistro(T registro) {
        this.registro = registro;
    }

    public List<T> getLista() {
        return lista;
    }

    public void setLista(List<T> lista) {
        this.lista = lista;
    }

    //Metodos propios

}
