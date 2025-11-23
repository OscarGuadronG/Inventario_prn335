package sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Boundary.Rest;

import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoProducto;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoProductoDAO;

@Path("tipo_producto")
public class TipoProductoResource {

    @Inject
    private TipoProductoDAO tpDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoProducto(@Min(0) @DefaultValue("0") @QueryParam("first") int first,@Max(100) @DefaultValue("50") @QueryParam("max") int max) {
        if (first >=0 && max <=100 && max > first) {
            try{
                Integer count = tpDao.count();
                return Response.ok(tpDao.findRange(first, max)).header("Total-records",count).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Missing-parameter", "first or max out of range").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Long id) {
        if (id != null && id > 0){
            try{
                var tipoProducto = tpDao.buscarPorId(id);
                if (tipoProducto != null){
                    return Response.ok(tipoProducto).header("TipoProducto-Found",id).build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoProducto not found").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(404).header("parameter-null", "id is required").build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(@Valid TipoProducto entity, @Context UriInfo uriInfo){
        if (entity != null && entity.getId()== null){
            try{
                if (entity.getIdTipoProductoPadre() != null && entity.getIdTipoProductoPadre().getId() != null){
                    var padre = tpDao.buscarPorId(entity.getIdTipoProductoPadre().getId());
                    if (padre == null){
                        return Response.status(422).header("Invalid-parameter", "idTipoProductoPadre not found").build();
                    }
                    entity.setIdTipoProductoPadre(padre);
                }
                tpDao.crear(entity);
                return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(entity.getId())).build()).build();
            } catch (Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid Object").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid TipoProducto entity) {
        if (entity != null && entity.getId() != null && entity.getId() > 0) {
            try{
                var existing = tpDao.buscarPorId(entity.getId());
                if (existing == null) {
                    return Response.status(404).header("Not-Found", "TipoProducto not found").build();
                }
                if (entity.getIdTipoProductoPadre() != null && entity.getIdTipoProductoPadre().getId() != null) {
                    var padre = tpDao.buscarPorId(entity.getIdTipoProductoPadre().getId());
                    if (padre == null) {
                        return Response.status(422).header("Invalid-parameter", "idTipoProductoPadre not found").build();
                    }
                    entity.setIdTipoProductoPadre(padre);
                }
                tpDao.modificar(entity);
                return Response.ok().header("TipoProducto-Updated", entity.getId()).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid Object").build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id){
        if (id != null && id > 0){
            try{
                TipoProducto tipoProducto = tpDao.buscarPorId(id);
                if (tipoProducto != null){
                    tpDao.eliminar(id);
                    return Response.noContent().build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoProducto not found").build();
                }
            } catch (Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("parameter-null", "id is required").build();
    }
}
