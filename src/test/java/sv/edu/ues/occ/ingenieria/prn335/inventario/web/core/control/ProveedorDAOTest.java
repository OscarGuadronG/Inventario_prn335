package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Proveedor;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class ProveedorDAOTest {

    private List<Proveedor> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Proveedor p = new Proveedor();
            p.setId(i);
            p.setNombre("Proveedor " + i);
            list.add(p);
        }
    }

    @Test
    void crearProveedor() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        ProveedorDAO cut = new ProveedorDAO();
        cut.em = mockedEm;

        Proveedor p = list.get(0);

        cut.crear(p);

        verify(mockedEm).persist(p);
    }
}
