package com.nbroadcast.backend.api.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nbroadcast.backend.api.blog.api.response.BlogResponse;
import com.nbroadcast.backend.api.blog.api.response.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResponseUtil {
    public static ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(ResponseUtil.class);

    public static BlogResponse generateBlogResponse(ResponseStatus status, String response, String error) {
        try {
            return BlogResponse.blogResponseBuilder()
                    .status(status)
                    .data(response != null ? objectMapper.readTree(response) : null)
                    .error(error)
                    .build();
        } catch (JsonProcessingException e){
            LOGGER.error("Error occured while mapping response to JsonNode. Message: {}",e.getMessage());
            return BlogResponse.blogResponseBuilder()
                    .status(ResponseStatus.FAILED)
                    .data(null)
                    .error(e.getMessage())
                    .build();
        }
    }
}
