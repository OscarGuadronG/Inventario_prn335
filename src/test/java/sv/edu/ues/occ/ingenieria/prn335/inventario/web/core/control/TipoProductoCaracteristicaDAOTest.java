package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProductoCaracteristica;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class TipoProductoCaracteristicaDAOTest {

    private List<TipoProductoCaracteristica> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TipoProductoCaracteristica e = new TipoProductoCaracteristica();
            e.setId(null);
            e.setObligatorio(true);
            list.add(e);
        }
    }

    @Test
    void crearTipoProductoCaracteristica() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        TipoProductoCaracteristicaDAO cut = new TipoProductoCaracteristicaDAO();
        cut.em = mockedEm;

        TipoProductoCaracteristica tpc = list.get(0);

        cut.crear(tpc);

        verify(mockedEm).persist(tpc);
    }
}
