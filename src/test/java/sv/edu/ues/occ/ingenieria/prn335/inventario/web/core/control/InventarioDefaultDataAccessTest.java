package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InventarioDefaultDataAccessTest {

    @Test
    void findLikeConsultaDebeLanzarExcepcion() {
        InventarioDefaultDataAccess<Object> dao = new InventarioDefaultDataAccess<>(Object.class) {
            @Override
            public EntityManager getEntityManager() {
                return null;
            }
        };

        assertThrows(UnsupportedOperationException.class, () -> dao.findLikeConsulta("test"));
    }
}
