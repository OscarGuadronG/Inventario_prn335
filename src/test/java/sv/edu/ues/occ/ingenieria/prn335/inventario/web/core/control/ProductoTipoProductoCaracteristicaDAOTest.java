package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.ProductoTipoProductoCaracteristica;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
public class ProductoTipoProductoCaracteristicaDAOTest {

    private List<ProductoTipoProductoCaracteristica> list;

    @BeforeEach
    public void setUp() throws Exception {
        list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ProductoTipoProductoCaracteristica e = new ProductoTipoProductoCaracteristica();
            e.setId(null);
            e.setValor("Valor " + i);
            list.add(e);
        }
    }

    @Test
    void crearProductoTipoProductoCaracteristica() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        ProductoTipoProductoCaracteristicaDAO cut = new ProductoTipoProductoCaracteristicaDAO();
        cut.em = mockedEm;

        ProductoTipoProductoCaracteristica ptpc = list.get(0);

        cut.crear(ptpc);

        verify(mockedEm).persist(ptpc);
    }
}
