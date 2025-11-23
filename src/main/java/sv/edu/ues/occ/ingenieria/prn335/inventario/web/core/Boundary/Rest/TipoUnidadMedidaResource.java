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
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.Entity.TipoUnidadMedida;
import sv.edu.ues.occ.ingenieria.prn335.inventario.web.core.control.TipoUnidadMedidaDAO;

@Path("tipo_unidad_medida")
public class TipoUnidadMedidaResource {

    @Inject
    private TipoUnidadMedidaDAO tumDAO;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTipoUnidadMedida(@Min(0) @DefaultValue("0") @QueryParam("first") int first,
                                        @Max(100) @DefaultValue("50") @QueryParam("max") int max){
        if (first >=0 && max <=100 && max > first){
            try{
                Integer count = tumDAO.count();
                return Response.ok(tumDAO.findRange(first, max)).header("Total-records",count).build();
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Missing-parameter", "first or max out of range").build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findById(@PathParam("id") Integer id){
        if (id != null && id > 0){
            try{
                var tipoUnidadMedida = tumDAO.buscarPorId(id);
                if (tipoUnidadMedida != null){
                    return Response.ok(tipoUnidadMedida).header("TipoUnidadMedida-Found",id).build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoUnidadMedida not found").build();
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
    public Response create(@Valid TipoUnidadMedida entity, @Context UriInfo uriInfo) {
        if (entity != null && entity.getId() == null) {
            try {
                tumDAO.crear(entity);
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
    public Response update(@Valid TipoUnidadMedida entity){
        if (entity != null && entity.getId() != null) {
            try {
                var existing = tumDAO.buscarPorId(entity.getId());
                if (existing != null) {
                    tumDAO.modificar(entity);
                    return Response.ok().header("TipoUnidadMedida-Updated", entity.getId()).build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoUnidadMedida not found").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid Object").build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Integer id){
        if (id != null && id > 0) {
            try {
                var existing = tumDAO.buscarPorId(id);
                if (existing != null) {
                    tumDAO.eliminar(existing);
                    return Response.noContent().build();
                } else {
                    return Response.status(404).header("Not-Found", "TipoUnidadMedida not found").build();
                }
            } catch (Exception e) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Server-Exception", "Cannot access db").build();
            }
        }
        return Response.status(422).header("Invalid-Parameters", "Invalid id").build();
    }
}
