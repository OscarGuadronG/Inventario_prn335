package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Venta;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class VentaDAOTest {

    private List<Venta> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Venta e = new Venta();
            e.setId(null);
            e.setObservaciones("Observaciones " + i);
            list.add(e);
        }
    }

    @Test
    void crearVenta() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        VentaDAO cut = new VentaDAO();
        cut.em = mockedEm;

        Venta v = list.get(0);

        cut.crear(v);

        verify(mockedEm).persist(v);
    }
}
