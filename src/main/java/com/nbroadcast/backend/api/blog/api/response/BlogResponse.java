package com.nbroadcast.backend.api.blog.api.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(builderMethodName = "blogResponseBuilder")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BlogResponse {
    ResponseStatus status;
    JsonNode data;
    String error;
}
