package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Producto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ProductoDAOTest {

    private List<Producto> lista;

    @BeforeEach
    public void setUp() throws Exception {
        lista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Producto p = new Producto();
            p.setId(null);
            p.setActivo(true);
            lista.add(p);
        }
    }

    @Test
    void crearProducto() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        ProductoDAO cut = new ProductoDAO();
        cut.em = mockedEm;

        Producto p = lista.get(0);

        cut.crear(p);

        verify(mockedEm).persist(p);
    }
}
