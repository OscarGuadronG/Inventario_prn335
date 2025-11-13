package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Almacen;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class AlmacenDAOTest {

    private List<Almacen> listaAlmacen;

    @BeforeEach
    public void setUp() throws Exception {
        listaAlmacen = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Almacen e = new Almacen();
            e.setId(i);
            e.setObservaciones("Observaciones " + i);
            listaAlmacen.add(e);
        }
    }

    @Test
    void crearAlmacen() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        AlmacenDAO cut = new AlmacenDAO();
        cut.em = mockedEm;

        Almacen al = listaAlmacen.get(0);

        cut.crear(al);

        verify(mockedEm).persist(al);

    }
}
