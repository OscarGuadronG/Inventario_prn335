package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Caracteristica;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CaracteristicaDAOTest {

    private List<Caracteristica> listaCaracteristica;
    @BeforeEach
    public void setUp() throws Exception {
        listaCaracteristica = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Caracteristica c = new Caracteristica();
            c.setId(i);
            c.setNombre("Caracteristica " + i);
            c.setActivo(true);
            listaCaracteristica.add(c);
        }
    }
    @Test
    void crearCaracteristica() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        CaracteristicaDAO cut = new CaracteristicaDAO();
        cut.em = mockedEm;

        Caracteristica caracteristica = new Caracteristica();
        caracteristica.setId(1);
        caracteristica.setNombre("Caracteristica 1");

        cut.crear(caracteristica);

        verify(mockedEm).persist(caracteristica);

    }

}
