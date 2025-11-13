package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.CompraDetalle;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class CompraDetalleDAOTest {

    private List<CompraDetalle> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            CompraDetalle e = new CompraDetalle();
            e.setId(null);
            e.setCantidad(null);
            lista.add(e);
        }
    }

    @Test
    void crearCompraDetalle() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        CompraDetalleDAO cut = new CompraDetalleDAO();
        cut.em = mockedEm;

        CompraDetalle cd = lista.get(0);

        cut.crear(cd);

        verify(mockedEm).persist(cd);
    }
}
