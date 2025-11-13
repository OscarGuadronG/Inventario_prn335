package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary;


import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;

@WebServlet(name = "TipoAlmacenServlet", urlPatterns = "web/tipoalmacen")
public class TipoAlmacenServlet extends HttpServlet {

    @Inject
    TipoAlmacenDAO taDAO;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String arriba= """
                <html>
                <head>
                <body>
                """;
        String abajo= """
                </body>
                </html>
                """;
        StringBuilder sb=new StringBuilder();
        String nombre = request.getParameter("nombre");
        if(request.getParameter("tipoAlmacen")!=null) {
            sb.append("<p>Debe ingresar un tipo de almacén</p>");
        }else{
            if(nombre!=null) {
                TipoAlmacen tipoAlmacen = new TipoAlmacen();
                tipoAlmacen.setNombre(nombre);
                try {
                    taDAO.crear(tipoAlmacen);
                    sb.append("<p>Tipo de almacén almacenado con éxito</p>");
                    sb.append("<p> Se le asigno el ID: ").append(tipoAlmacen.getId()).append("</p>");
                } catch (Exception ex) {
                    sb.append(ex.getMessage());
                }
            }
        }
    }
}
