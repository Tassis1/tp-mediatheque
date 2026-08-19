package fr.tp.mediatheque.rest;

import fr.tp.mediatheque.entity.Utilisateur;
import fr.tp.mediatheque.service.UtilisateurService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.Map;

@Path("/utilisateurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UtilisateurResource {

    @Inject
    private UtilisateurService utilisateurService;

    public static class InscriptionRequest {
        public String nom;
        public String email;
        public String motDePasse;
    }

    public static class LoginRequest {
        public String email;
        public String motDePasse;
    }

    @POST
    @Path("/inscription")
    public Response inscrire(InscriptionRequest req) {
        Utilisateur u = utilisateurService.creer(req.nom, req.email, req.motDePasse);
        return Response.status(Response.Status.CREATED).entity(Map.of("id", u.getId(), "nom", u.getNom(), "email", u.getEmail(), "role", u.getRole())).build();
    }

    @POST
    @Path("/login")
    public Response login(LoginRequest req) {
        Utilisateur u = utilisateurService.authentifier(req.email, req.motDePasse);
        return Response.ok(Map.of(
                "id", u.getId(),
                "nom", u.getNom(),
                "role", u.getRole()
        )).build();
    }
}