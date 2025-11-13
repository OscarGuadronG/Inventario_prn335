package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Compra;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CompraDAOTest {

    private List<Compra> listaCompra;
    @BeforeEach
    public void setUp() throws Exception {
        listaCompra = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Compra e = new Compra();
            e.setObservaciones("Observaciones " + i);
            listaCompra.add(e);
        }
    }

    @Test
    void crearCompra() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        CompraDAO cut = new CompraDAO();
        cut.em = mockedEm;
        Compra c = listaCompra.get(0);
        cut.crear(c);
        verify(mockedEm).persist(c);
    }
}
