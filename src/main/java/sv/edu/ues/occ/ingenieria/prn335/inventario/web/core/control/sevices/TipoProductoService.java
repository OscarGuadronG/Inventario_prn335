package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.sevices;

import jakarta.inject.Inject;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters.NodoTipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

import java.util.ArrayList;
import java.util.List;

public class TipoProductoService {

    @Inject
    TipoProductoDAO dao;

    public List<NodoTipoProducto> construirArbolDesdePadres() {
        List<TipoProducto> padres = dao.findTiposPadre();
        List<NodoTipoProducto> nodosPadre = new ArrayList<>();

        for (TipoProducto padre : padres) {
            NodoTipoProducto nodo = new NodoTipoProducto(padre);
            construirRecursivo(nodo);
            nodosPadre.add(nodo);
        }

        return nodosPadre;
    }
    private void construirRecursivo(NodoTipoProducto nodo) {
        List<TipoProducto> hijos = dao.findHijos(nodo.getTipo());
        if (hijos == null || hijos.isEmpty()) {
            return;
        }

        for (TipoProducto hijo : hijos) {
            NodoTipoProducto nodoHijo = new NodoTipoProducto(hijo);
            nodo.getHijos().add(nodoHijo);
            construirRecursivo(nodoHijo);
        }
    }
}
