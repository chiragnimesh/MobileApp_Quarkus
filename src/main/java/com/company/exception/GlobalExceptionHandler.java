package com.company.exception;


import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import java.time.LocalDateTime;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        Response.Status status;

        if (exception instanceof ResourceNotFoundException) {
            status = Response.Status.NOT_FOUND; // 404
        } else if (exception instanceof ResourceAlreadyPresentException) {
            status = Response.Status.BAD_REQUEST; // 400
        } else {
            status = Response.Status.INTERNAL_SERVER_ERROR; // 500
        }

        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                exception.getMessage(),
                "Unexpected error occurred"
        );

        return Response.status(status)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
}
