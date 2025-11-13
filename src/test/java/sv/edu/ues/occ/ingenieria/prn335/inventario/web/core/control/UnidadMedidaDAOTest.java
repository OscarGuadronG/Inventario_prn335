package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.UnidadMedida;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class UnidadMedidaDAOTest {

    private List<UnidadMedida> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            UnidadMedida e = new UnidadMedida();
            e.setId(i);
            e.setActivo(true);
            list.add(e);
        }
    }

    @Test
    void crearUnidadMedida() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        UnidadMedidaDAO cut = new UnidadMedidaDAO();
        cut.em = mockedEm;

        UnidadMedida um = list.get(0);

        cut.crear(um);

        verify(mockedEm).persist(um);
    }
}
