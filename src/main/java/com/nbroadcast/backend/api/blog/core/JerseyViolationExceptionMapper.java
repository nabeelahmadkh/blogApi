package com.nbroadcast.backend.api.blog.core;

import com.nbroadcast.backend.api.blog.api.response.ResponseStatus;
import com.nbroadcast.backend.api.blog.util.ResponseUtil;
import io.dropwizard.jersey.validation.ConstraintMessage;
import io.dropwizard.jersey.validation.JerseyViolationException;
import org.glassfish.jersey.server.model.Invocable;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.Set;

public class JerseyViolationExceptionMapper implements ExceptionMapper<JerseyViolationException> {
    @Override
    public Response toResponse(final JerseyViolationException exception) {
        final Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        final Invocable invocable = exception.getInvocable();
        final int status = ConstraintMessage.determineStatus(violations, invocable);
        final String message = exception.getMessage();
        return Response.status(status)
                .type(MediaType.TEXT_PLAIN_TYPE)
                .entity(ResponseUtil.generateBlogResponse(ResponseStatus.FAILED, null, message))
                .build();
    }
}
