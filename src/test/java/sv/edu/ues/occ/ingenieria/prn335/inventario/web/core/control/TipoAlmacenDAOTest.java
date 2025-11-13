package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnitUtil;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

class TipoAlmacenDAOTest {

    List<TipoAlmacen> listaTipoAlmacen;

    @BeforeEach
    public void setUp() throws Exception {
        this.listaTipoAlmacen = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TipoAlmacen ta = new TipoAlmacen();
            ta.setId(i);
            ta.setNombre("Tipo Almacen " + i);
            ta.setActivo(i % 2 == 0);
            this.listaTipoAlmacen.add(ta);
        }
    }

    @Test
    void crear() throws IllegalAccessException {
        //Simulamos un objeto EmtityManager usando Mockito
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        //Creamos un objeto DAO
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        //Inyectamos el EntityManager simulado en el DAO
        cut.em = mockedEm;

        //Creamos un TipoAlmacen con unos datos
        System.out.println("crear tipo almacen");
        TipoAlmacen nuevo = new TipoAlmacen();
        nuevo.setNombre("Almacen de prueba");
        nuevo.setActivo(true);

        //Probamos el metodo crear
        cut.crear(nuevo);
        //Verificamos que se haya llamado al metodo persist del EntityManager
        Mockito.verify(mockedEm).persist(nuevo);
        //Simulamos que el metodo persist devuelve el objeto creado
        when(mockedEm.merge(nuevo)).thenReturn(nuevo);
        TipoAlmacen resultado = nuevo;
        assertEquals("Almacen de prueba", resultado.getNombre());

        //Probamos los casos en que se lanzan las excepciones
        //Caso 1: El objeto a crear es nulo
        assertThrows(IllegalAccessException.class, () -> cut.crear(null));

        //Caso 2: El EntityManager es nulo
        cut.em = null;
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> cut.crear(nuevo));
        assertEquals("dao.AccesoDB", thrown.getMessage());

        //Caso 3: El EntityManager lanza una excepcion al llamar a persist
        cut.em = mockedEm;
        doThrow(new RuntimeException("dao.GuardarError")).when(mockedEm).persist(nuevo);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.crear(nuevo));
        assertEquals("dao.GuardarError", thrown2.getMessage());

    }

    @Test
    void modificar() throws IllegalArgumentException, IllegalAccessException {
        //Crear un EntityManager simulado con mockito
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;
        //Crear dos TipoAlmacen con la lista y uno para modificar
        System.out.println("modificar tipo almacen");
        TipoAlmacen n1 = this.listaTipoAlmacen.get(0);
        TipoAlmacen n2 = this.listaTipoAlmacen.get(1);
        TipoAlmacen modificado = new TipoAlmacen();
        modificado.setId(1);
        modificado.setNombre("Tipo almacen modificado");
        modificado.setActivo(true);
        cut.crear(n1);
        cut.crear(n2);
        //Verificar que se haya llamado al metodo persist del EntityManager
        Mockito.verify(mockedEm).persist(n1);
        Mockito.verify(mockedEm).persist(n2);
        when(mockedEm.merge(n1)).thenReturn(n1);
        assertEquals("Tipo Almacen 0", n1.getNombre());
        //Probar el metodo modificar en un objeto con id que existe
        when(mockedEm.getEntityManagerFactory()).thenReturn(Mockito.mock(EntityManagerFactory.class));
        when(mockedEm.getEntityManagerFactory().getPersistenceUnitUtil()).thenReturn(Mockito.mock(PersistenceUnitUtil.class));
        when(mockedEm.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(modificado)).thenReturn(modificado.getId());
        // Simulamos que el registro con ese id EXISTE en la BD
        when(mockedEm.find(TipoAlmacen.class, modificado.getId())).thenReturn(modificado);
        when(mockedEm.merge(modificado)).thenReturn(modificado);
        when(mockedEm.merge(modificado)).thenReturn(modificado);
        TipoAlmacen resultado = cut.modificar(modificado);
        assertEquals("Tipo almacen modificado", resultado.getNombre());
        //Probar el metodo modificar en un objeto con id que no existe
        TipoAlmacen noExiste = new TipoAlmacen();
        noExiste.setId(100);
        noExiste.setNombre("No existe");
        noExiste.setActivo(false);
        when(mockedEm.find(TipoAlmacen.class, 100)).thenReturn(null);
        IllegalStateException thrown3 = assertThrows(IllegalStateException.class, () -> cut.modificar(noExiste));
        assertEquals("dao.RegistroNoEncontrado", thrown3.getMessage());
        //Execpciones
        //Caso 1: El objeto a modificar es nulo
        assertThrows(IllegalAccessException.class, () -> cut.modificar(null));
        //Caso 2: El EntityManager es nulo
        cut.em = null;
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> cut.modificar(modificado));
        assertEquals("dao.AccesoDB", thrown.getMessage());
        //Caso 3: El EntityManager lanza una excepcion al llamar a merge
        cut.em = mockedEm;
        doThrow(new RuntimeException("dao.ModificarError")).when(mockedEm).merge(modificado);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.modificar(modificado));
        assertEquals("dao.ModificarError", thrown2.getMessage());
        //Caso 4: El EntityManager lanza una excepcion al llamar a getIdentifier
        when(mockedEm.getEntityManagerFactory()).thenThrow(new RuntimeException("Error interno"));
        IllegalAccessException thrown4 = assertThrows(IllegalAccessException.class, () -> cut.modificar(modificado));
        assertEquals("dao.IdNoValido", thrown4.getMessage());
    }

    @Test
    void buscarPorId() throws IllegalArgumentException, IllegalAccessException {
        //Crear un EntityManager simulado con mockito
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;
        //Crear dos TipoAlmacen con la lista
        System.out.println("buscarPorId tipo almacen");
        TipoAlmacen n1 = this.listaTipoAlmacen.get(0);
        TipoAlmacen n2 = this.listaTipoAlmacen.get(1);
        //Probar el metodo buscarPorId
        cut.buscarPorId(1);
        //Verificar que se haya llamado al metodo find del EntityManager
        Mockito.verify(mockedEm).find(TipoAlmacen.class, 1);
        when(mockedEm.find(TipoAlmacen.class, 1)).thenReturn(n2);
        TipoAlmacen resultado = cut.buscarPorId(1);
        assertEquals("Tipo Almacen 1", resultado.getNombre());
        //Execpciones
        //Caso 1: El id a buscar es nulo
        assertThrows(IllegalAccessException.class, () -> cut.buscarPorId(null));
        //Caso 2: El EntityManager es nulo
        cut.em = null;
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> cut.buscarPorId(1));
        assertEquals("dao.AccesoDB", thrown.getMessage());
        //Caso 3: El EntityManager lanza una excepcion al llamar a find
        cut.em = mockedEm;
        doThrow(new RuntimeException("dao.buscarError")).when(mockedEm).find(TipoAlmacen.class, 1);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.buscarPorId(1));
        assertEquals("dao.buscarError", thrown2.getMessage());
    }

    @Test
    void eliminar() throws IllegalAccessException {
        //Crear un EntityManager simulado con mockito
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;
        //Crear dos TipoAlmacen con la lista
        TipoAlmacen n1 = this.listaTipoAlmacen.get(0);
        TipoAlmacen n2 = this.listaTipoAlmacen.get(1);
        cut.crear(n1);
        cut.crear(n2);
        //Verificar que se haya llamado al metodo persist del EntityManager
        Mockito.verify(mockedEm).persist(n1);
        Mockito.verify(mockedEm).persist(n2);
        when(mockedEm.merge(n1)).thenReturn(n1);
        assertEquals("Tipo Almacen 0", n1.getNombre());
        //Probar el metodo eliminar en un objeto con id que existe
        when(mockedEm.find(TipoAlmacen.class, 0)).thenReturn(n1);
        cut.eliminar(0);
        Mockito.verify(mockedEm).remove(n1);
        //Probar el metodo eliminar en un objeto con id que no existe
        when(mockedEm.find(TipoAlmacen.class, 100)).thenReturn(null);
        IllegalStateException thrown3 = assertThrows(IllegalStateException.class, () -> cut.eliminar(100));
        assertEquals("dao.eliminarNoEncontrado", thrown3.getMessage());
        //Execpciones
        //Caso 1: El id a eliminar es nulo
        assertThrows(IllegalArgumentException.class, () -> cut.eliminar(null));
        //Caso 2: El EntityManager es nulo
        cut.em = null;
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> cut.eliminar(1));
        assertEquals("dao.AccesoDB", thrown.getMessage());
        //Caso 3: El EntityManager lanza una excepcion al llamar a find
        cut.em = mockedEm;
        doThrow(new RuntimeException("dao.buscarError")).when(mockedEm).find(TipoAlmacen.class, 1);
        IllegalStateException thrown2 = assertThrows(IllegalStateException.class, () -> cut.eliminar(1));
        assertEquals("dao.buscarError", thrown2.getMessage());
        //Caso 4: El EntityManager lanza una excepcion al llamar a remove
        when(mockedEm.find(TipoAlmacen.class, 0)).thenReturn(n1);
        doThrow(new RuntimeException("dao.EliminarError")).when(mockedEm).remove(n1);
        IllegalStateException thrown4 = assertThrows(IllegalStateException.class, () -> cut.eliminar(0));
        assertEquals("dao.EliminarError", thrown4.getMessage());
    }

    @Test
    void findRange() {
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<TipoAlmacen> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(TipoAlmacen.class)).thenReturn(mockCq);

        Root mockroot = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(TipoAlmacen.class)).thenReturn(mockroot);

        CriteriaQuery<TipoAlmacen> mockAll = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(TipoAlmacen.class)).thenReturn(mockAll);

        TypedQuery<TipoAlmacen> mockTq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockAll)).thenReturn(mockTq);

        Mockito.when(mockTq.getResultList()).thenReturn(this.listaTipoAlmacen);

        assertThrows(IllegalArgumentException.class, () -> {
            cut.findRange(-1, 12);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            cut.findRange(1, -1);
        });
        assertThrows(IllegalStateException.class, () -> {
            cut.findRange(1, 12);
        });
        cut.em = null;
        IllegalStateException throw1 = assertThrows(IllegalStateException.class, () -> {
            List<TipoAlmacen> resultado3 = cut.findRange(1, 5);
        });
        assertEquals("dao.AccesoDB", throw1.getMessage());

        cut.em = mockedEm;
    }


    @Test
    void count() throws IllegalArgumentException {

        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<Long> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(Long.class)).thenReturn(mockCq);

        Root<TipoAlmacen> mockroot = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(TipoAlmacen.class)).thenReturn(mockroot);

        TypedQuery<Long> mockTq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockCq)).thenReturn(mockTq);

        Mockito.when(mockTq.getSingleResult()).thenReturn(10L);

        int resultado = cut.count();
        assertEquals(10, resultado);

        Mockito.when(mockedEm.getCriteriaBuilder()).thenThrow(new RuntimeException("DB error"));
        IllegalStateException thrown = assertThrows(IllegalStateException.class, () -> cut.count());
        assertEquals("dao.AccesoDB", thrown.getMessage());

        cut.em = null;
        int resultado3 = cut.count();
        assertEquals(-1, resultado3);


    }

    @Test
    void findAll(){
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<TipoAlmacen> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(TipoAlmacen.class)).thenReturn(mockCq);

        Root mockroot = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(TipoAlmacen.class)).thenReturn(mockroot);

        Mockito.when(mockCq.select(Mockito.any())).thenReturn(mockCq);

        CriteriaQuery<TipoAlmacen> mockAll = Mockito.mock(CriteriaQuery.class);
        Mockito.when(mockCq.select(mockroot).orderBy(cb.asc(mockroot.get("id")))).thenReturn(mockAll);

        TypedQuery<TipoAlmacen> mockTq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockAll)).thenReturn(mockTq);

        Mockito.when(mockTq.getResultList()).thenReturn(this.listaTipoAlmacen);

        List<TipoAlmacen> resultado = cut.findRange(0, 10);
        assertEquals(10, resultado.size());
    }

    @Test
    void findLikeConsulta() {
        EntityManager mockedEm = Mockito.mock(EntityManager.class);
        TipoAlmacenDAO cut = new TipoAlmacenDAO();
        cut.em = mockedEm;

        CriteriaBuilder cb = Mockito.mock(CriteriaBuilder.class);
        Mockito.when(mockedEm.getCriteriaBuilder()).thenReturn(cb);

        CriteriaQuery<TipoAlmacen> mockCq = Mockito.mock(CriteriaQuery.class);
        Mockito.when(cb.createQuery(TipoAlmacen.class)).thenReturn(mockCq);

        Root<TipoAlmacen> mockroot = Mockito.mock(Root.class);
        Mockito.when(mockCq.from(TipoAlmacen.class)).thenReturn(mockroot);

        Mockito.when(mockCq.select(mockroot)).thenReturn(mockCq);

        Path mockPath = Mockito.mock(Path.class);
        Mockito.when(mockroot.get("nombre")).thenReturn(mockPath);
        Mockito.when(cb.lower(mockPath)).thenReturn(mockPath);
        Predicate mockPredicate = Mockito.mock(Predicate.class);
        Mockito.when(cb.like(Mockito.any(), Mockito.anyString())).thenReturn(mockPredicate);
        Mockito.when(mockCq.where(mockPredicate)).thenReturn(mockCq);

        TypedQuery<TipoAlmacen> mockTq = Mockito.mock(TypedQuery.class);
        Mockito.when(mockedEm.createQuery(mockCq)).thenReturn(mockTq);
        Mockito.when(mockTq.getResultList()).thenReturn(Collections.emptyList());

        List<TipoAlmacen> resultado = cut.findLikeConsulta("algo");

        assertNotNull(resultado);
        Mockito.verify(mockedEm).getCriteriaBuilder();
        Mockito.verify(cb).createQuery(TipoAlmacen.class);
        Mockito.verify(mockCq).from(TipoAlmacen.class);

        //Caso consulta nula o vacia
        List<TipoAlmacen> resultado2 = cut.findLikeConsulta(null);
        assertTrue(resultado2.isEmpty());
        List<TipoAlmacen> resultado3 = cut.findLikeConsulta("   ");
        assertTrue(resultado3.isEmpty());
    }
}