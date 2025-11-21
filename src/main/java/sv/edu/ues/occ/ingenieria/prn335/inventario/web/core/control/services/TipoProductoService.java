package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.services;

import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

import java.io.Serializable;
import java.util.List;

@Stateless
public class TipoProductoService implements Serializable {

    @Inject
    private TipoProductoDAO dao;


    public TreeNode<TipoProducto> construirArbolDesdePadres(){
        TreeNode<TipoProducto> raiz = new DefaultTreeNode<>();
        List<TipoProducto> tiposPadre = dao.findTiposPadre();
        for (TipoProducto padre : tiposPadre) {
            TreeNode<TipoProducto> nodoPadre = new DefaultTreeNode<>(padre, raiz);
            construirRecursivo(nodoPadre, padre);
        }
        return raiz;
    }

    private void construirRecursivo(TreeNode<TipoProducto> tipoPadre,TipoProducto nodo) {
        List<TipoProducto> hijos = dao.findHijos(nodo);
        for (TipoProducto hijo : hijos) {
            TreeNode<TipoProducto> nodoHijo = new DefaultTreeNode<>(hijo, tipoPadre);
            construirRecursivo(nodoHijo, hijo);
        }
    }


}
