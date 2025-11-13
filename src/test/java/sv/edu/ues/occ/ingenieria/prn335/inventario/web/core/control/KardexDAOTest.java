package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Kardex;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class KardexDAOTest {

    private List<Kardex> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Kardex e = new Kardex();
            e.setId(null);
            e.setObservaciones("Observaciones " + i);
            lista.add(e);
        }
    }

    @Test
    void crearKardex() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        KardexDAO cut = new KardexDAO();
        cut.em = mockedEm;

        Kardex al = lista.get(0);

        cut.crear(al);

        verify(mockedEm).persist(al);
    }
}
