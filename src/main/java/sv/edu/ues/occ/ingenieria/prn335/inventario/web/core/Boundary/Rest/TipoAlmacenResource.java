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
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoAlmacen;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoAlmacenDAO;

@Path("tipo_almacen")
public class TipoAlmacenResource {

    @Inject
    private TipoAlmacenDAO taDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoAlmacen(@Min(0) @DefaultValue("0") @QueryParam("first") int first, @Max(100) @DefaultValue("50") @QueryParam("max") int max ){
        if (first >=0 && max <=100 && max > first) {
            try{
                Integer count = taDao.count();
                return Response.ok(taDao.findRange(first, max)).header("Total-records",count).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Missing-parameter", "first or max out of range").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id) {
        if (id != null && id > 0){
            try{
                var tipoAlmacen = taDao.buscarPorId(id);
                if (tipoAlmacen != null){
                    return Response.ok(tipoAlmacen).header("TipoAlmacen-Found",id).build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoAlmacen not found").build();
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
    public Response create(@Valid TipoAlmacen entity, @Context UriInfo uriInfo) {
        if (entity != null && entity.getId() == null) {
            try {
                taDao.crear(entity);
                return Response.created(uriInfo.getAbsolutePathBuilder().path(String.valueOf(entity.getId())).build()).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid Object").build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@Valid TipoAlmacen entity) {
        if (entity != null && entity.getId() != null) {
            try {
                var existing = taDao.buscarPorId(entity.getId());
                if (existing != null) {
                    taDao.modificar(entity);
                    return Response.ok().header("TipoAlmacen-Updated", entity.getId()).build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoAlmacen not found").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid Object").build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id) {
        if (id != null && id > 0) {
            try {
                var existing = taDao.buscarPorId(id);
                if (existing != null) {
                    taDao.eliminar(existing);
                    return Response.noContent().build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoAlmacen not found").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid id").build();
    }

}
