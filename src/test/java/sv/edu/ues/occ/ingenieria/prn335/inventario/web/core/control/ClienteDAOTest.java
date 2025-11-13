package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.Cliente;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ClienteDAOTest {

    private List<Cliente> listaCliente;

    @BeforeEach
    public void setUp() throws Exception {
        listaCliente = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            Cliente e = new Cliente();
            e.setNombre("Cliente " + i);
            listaCliente.add(e);
        }
    }

    @Test
    void crearCliente() throws IllegalAccessException {
        EntityManager mockedEm = mock(EntityManager.class);
        ClienteDAO cut = new ClienteDAO();
        cut.em = mockedEm;
        Cliente cl = listaCliente.get(0);
        cut.crear(cl);
        verify(mockedEm).persist(cl);
    }

    @Test
    void duiDuplicado(){
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        ClienteDAO cut = new ClienteDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Long> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(Long.class)).thenReturn(mockCq);

        Root<Cliente> root = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(Cliente.class)).thenReturn(root);

        Mockito.when(mockCq.select(Mockito.any())).thenReturn(mockCq);

        Mockito.when(cb.count(Mockito.any())).thenReturn(Mockito.mock(Expression.class));
        Mockito.when(cb.equal(Mockito.any(), Mockito.any())).thenReturn(Mockito.mock(Predicate.class));

        TypedQuery<Long> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockCq)).thenReturn(tq);
        Mockito.when(tq.getSingleResult()).thenReturn(1L);
        boolean resultado = cut.DocumentoUnico("123456789");

        assertFalse(resultado);
        Mockito.verify(mockedEm).createQuery(mockCq);
        Mockito.verify(tq).getSingleResult();
    }

    @Test
    void testNitUnico() {
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        ClienteDAO cut = new ClienteDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Long> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(Long.class)).thenReturn(mockCq);

        Root<Cliente> root = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(Cliente.class)).thenReturn(root);

        Mockito.when(mockCq.select(Mockito.any())).thenReturn(mockCq);

        Mockito.when(cb.count(Mockito.any())).thenReturn(Mockito.mock(Expression.class));
        Mockito.when(cb.equal(Mockito.any(), Mockito.any())).thenReturn(Mockito.mock(Predicate.class));

        TypedQuery<Long> tq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockCq)).thenReturn(tq);
        Mockito.when(tq.getSingleResult()).thenReturn(0L);

        boolean resultado = cut.NitUnico("06140505901023");

        assertTrue(resultado);
        Mockito.verify(mockedEm).createQuery(mockCq);
        Mockito.verify(tq).getSingleResult();
    }
}
