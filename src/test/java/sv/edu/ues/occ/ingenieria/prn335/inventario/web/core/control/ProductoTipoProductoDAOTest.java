package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProducto;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class ProductoTipoProductoDAOTest {

    private List<ProductoTipoProducto> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProductoTipoProducto e = new ProductoTipoProducto();
            e.setId(null);
            e.setActivo(true);
            list.add(e);
        }
    }

    @Test
    void crearProductoTipoProducto() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        ProductoTipoProductoDAO cut = new ProductoTipoProductoDAO();
        cut.em = mockedEm;

        ProductoTipoProducto ptp = list.get(0);

        cut.crear(ptp);

        verify(mockedEm).persist(ptp);
    }
}
