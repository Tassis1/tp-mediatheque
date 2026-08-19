package fr.tp.mediatheque.rest;

import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.util.Map;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Response.Status status;

        if (exception instanceof IllegalArgumentException) {
            status = Response.Status.NOT_FOUND;
        } else if (exception instanceof IllegalStateException) {
            status = Response.Status.CONFLICT;
        } else if (exception instanceof SecurityException) {
            status = Response.Status.UNAUTHORIZED;
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR;
        }

        return Response.status(status)
                .entity(Map.of("erreur", exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}