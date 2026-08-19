package fr.tp.mediatheque.rest;

import fr.tp.mediatheque.entity.Emprunt;
import fr.tp.mediatheque.rest.dto.EmpruntDTO;
import fr.tp.mediatheque.service.EmpruntService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/emprunts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmpruntResource {

    @Inject
    private EmpruntService empruntService;

    public static class CreationEmpruntRequest {
        public Long utilisateurId;
        public Long livreId;
    }

    @POST
    public Response creer(CreationEmpruntRequest req) {
        Emprunt e = empruntService.creerEmprunt(req.utilisateurId, req.livreId);
        return Response.status(Response.Status.CREATED).entity(EmpruntDTO.depuis(e)).build();
    }

    @PUT
    @Path("/{id}/retour")
    public EmpruntDTO retourner(@PathParam("id") Long id) {
        Emprunt e = empruntService.retournerLivre(id);
        return EmpruntDTO.depuis(e);
    }

    @GET
    @Path("/utilisateur/{utilisateurId}/en-cours")
    public List<EmpruntDTO> empruntsEnCours(@PathParam("utilisateurId") Long utilisateurId) {
        return empruntService.empruntsEnCoursDe(utilisateurId)
                .stream()
                .map(EmpruntDTO::depuis)
                .collect(Collectors.toList());
    }
}