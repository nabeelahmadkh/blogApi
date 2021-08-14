package com.nbroadcast.backend.api.blog.core;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.nbroadcast.backend.api.blog.api.response.ResponseStatus;
import com.nbroadcast.backend.api.blog.util.ResponseUtil;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

public class JacksonJsonExceptionMapper implements ExceptionMapper<JsonProcessingException> {
    @Override
    public Response toResponse(final JsonProcessingException exception) {
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(ResponseUtil.generateBlogResponse(ResponseStatus.FAILED, null, exception.getMessage()))
                .build();
    }
}
