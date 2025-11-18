package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.jsf.converters;

import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;

import java.util.ArrayList;
import java.util.List;

public class NodoTipoProducto {

    private TipoProducto tipo;
    private List<NodoTipoProducto> hijos;

    public NodoTipoProducto (TipoProducto tipo){
        this.tipo = tipo;
    }

    public TipoProducto getTipo() {
        return tipo;
    }
    public List<NodoTipoProducto> getHijos() {
        if (hijos == null) {
            hijos = new ArrayList<>();
        }
        return hijos;
    }

    public void setHijos(List<NodoTipoProducto> hijos) {
        this.hijos = hijos;
    }
}
