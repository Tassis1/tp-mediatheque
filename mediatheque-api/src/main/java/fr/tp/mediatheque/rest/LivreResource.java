package fr.tp.mediatheque.rest;

import fr.tp.mediatheque.entity.Livre;
import fr.tp.mediatheque.service.LivreService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

@Path("/livres")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LivreResource {

    @Inject
    private LivreService livreService;

    @GET
    public List<Livre> listerTous() {
        return livreService.listerTous();
    }

    @GET
    @Path("/{id}")
    public Livre obtenirParId(@PathParam("id") Long id) {
        return livreService.obtenirParId(id);
    }

    @POST
    public Response creer(Livre livre) {
        Livre cree = livreService.creer(livre);
        return Response.status(Response.Status.CREATED).entity(cree).build();
    }

    @PUT
    @Path("/{id}")
    public Livre mettreAJour(@PathParam("id") Long id, Livre donnees) {
        return livreService.mettreAJour(id, donnees);
    }

    @DELETE
    @Path("/{id}")
    public Response supprimer(@PathParam("id") Long id) {
        livreService.supprimer(id);
        return Response.noContent().build();
    }
}