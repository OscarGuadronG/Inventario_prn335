package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.VentaDetalle;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class VentaDetalleDAOTest {

    private List<VentaDetalle> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            VentaDetalle e = new VentaDetalle();
            e.setId(null);
            e.setCantidad(null);
            list.add(e);
        }
    }

    @Test
    void crearVentaDetalle() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        VentaDetalleDAO cut = new VentaDetalleDAO();
        cut.em = mockedEm;

        VentaDetalle vd = list.get(0);

        cut.crear(vd);

        verify(mockedEm).persist(vd);
    }
}
