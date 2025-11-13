package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class TipoProductoDAOTest {

    private List<TipoProducto> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TipoProducto e = new TipoProducto();
            e.setId(null);
            e.setNombre("TipoProducto " + i);
            list.add(e);
        }
    }

    @Test
    void crearTipoProducto() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        TipoProductoDAO cut = new TipoProductoDAO();
        cut.em = mockedEm;

        TipoProducto tp = list.get(0);

        cut.crear(tp);

        verify(mockedEm).persist(tp);
    }
}
