package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.KardexDetalle;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class KardexDetalleDAOTest {

    private List<KardexDetalle> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            KardexDetalle e = new KardexDetalle();
            e.setId(null);
            e.setActivo(true);
            lista.add(e);
        }
    }

    @Test
    void crearKardexDetalle() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        KardexDetalleDAO cut = new KardexDetalleDAO();
        cut.em = mockedEm;
        KardexDetalle al = lista.get(0);
        cut.crear(al);
        verify(mockedEm).persist(al);
    }
}
