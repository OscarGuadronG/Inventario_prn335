package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control;

import java.util.List;

/**
 * Utiliza un patron DAO para manejar las operaciones CRUD
 * @param <T>
 */
public interface InventarioDAOinterface<T> {

    public void crear(T registro) throws IllegalArgumentException, IllegalAccessException;

    public void eliminar(Object id) throws IllegalAccessException;

    public T modificar(T registro) throws IllegalArgumentException, IllegalAccessException;

    public T buscarPorId(Object id)throws IllegalArgumentException, IllegalAccessException;

    public List<T> findRange(int first, int max) throws IllegalArgumentException, IllegalAccessException;

    public int count()throws IllegalArgumentException;

}
