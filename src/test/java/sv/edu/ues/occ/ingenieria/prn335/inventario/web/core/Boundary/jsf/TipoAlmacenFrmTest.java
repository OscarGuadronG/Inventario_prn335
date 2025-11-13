package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf;

import jakarta.faces.context.FacesContext;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.InventarioDefaultDataAccess;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TipoAlmacenFrmTest {

    @Test
    void metodosAbstractos() throws IllegalAccessException {
        InventarioDefaultDataAccess<TipoAlmacen> dao = mock(InventarioDefaultDataAccess.class);
        FacesContext facesContextMock = mock(FacesContext.class);
        TipoAlmacenFrm frm = new TipoAlmacenFrm(){
            @Override
            protected InventarioDefaultDataAccess<TipoAlmacen> getDao() {
                return dao;
            }

            @Override
            protected TipoAlmacen buscarRegistroPorId(Object id) { return new TipoAlmacen(); }

            @Override
            protected TipoAlmacen nuevoRegistro() { return new TipoAlmacen(); }

            @Override
            protected FacesContext getFacesContext() {
                return facesContextMock;
            }
        };

        assertNotNull(frm.getDao(), "El DAO no debe ser nulo");
        assertEquals(dao, frm.getDao(), "El DAO devuelto debe ser el mock configurado");

        assertNotNull(frm.getFacesContext(), "FacesContext no debe ser nulo");
        assertEquals(facesContextMock, frm.getFacesContext(), "Debe devolver el FacesContext mockeado");

        TipoAlmacen a = frm.nuevoRegistro();
        TipoAlmacen b = frm.nuevoRegistro();
        assertNotNull(a);
        assertNotSame(a, b, "Cada nuevo registro debe ser una instancia distinta");

        TipoAlmacen encontrado = frm.buscarRegistroPorId(1L);
        assertNotNull(encontrado, "Debe devolver un TipoAlmacen válido");
    }


    @Test
    void cargarYcontarDatos() {
        EntityManager mockEm = mock(EntityManager.class);
        InventarioDefaultDataAccess<TipoAlmacen> dao = mock(InventarioDefaultDataAccess.class);
        TipoAlmacenFrm frm = new TipoAlmacenFrm(){
            @Override
            protected InventarioDefaultDataAccess<TipoAlmacen> getDao() {
                return dao;
            }

            @Override
            protected TipoAlmacen buscarRegistroPorId(Object id) {
                return null;
            }

            @Override
            protected TipoAlmacen nuevoRegistro() {
                return null;
            }

            @Override
            protected jakarta.faces.context.FacesContext getFacesContext() {
                return null;
            }
        };
        List<TipoAlmacen> lista = List.of(new TipoAlmacen(), new TipoAlmacen());
        when(dao.findRange(0, 10)).thenReturn(lista);
        when(dao.count()).thenReturn(2);

        assertEquals(lista, frm.CargarDatos(0, 10));
        assertEquals(2, frm.ContarDatos());


    }
}
