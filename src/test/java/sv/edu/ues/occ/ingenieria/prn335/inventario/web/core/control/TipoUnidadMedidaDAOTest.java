package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class TipoUnidadMedidaDAOTest {

    private List<TipoUnidadMedida> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TipoUnidadMedida e = new TipoUnidadMedida();
            e.setId(i);
            e.setNombre("Tipo Unidad Medida " + i);
            list.add(e);
        }
    }

    @Test
    void crearTipoUnidadMedida() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        TipoUnidadMedidaDAO cut = new TipoUnidadMedidaDAO();
        cut.em = mockedEm;

        TipoUnidadMedida tum = list.get(0);

        cut.crear(tum);

        verify(mockedEm).persist(tum);
    }
}
