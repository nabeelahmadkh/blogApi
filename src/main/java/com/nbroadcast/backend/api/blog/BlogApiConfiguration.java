package com.nbroadcast.backend.api.blog;

import com.nbroadcast.backend.api.blog.api.Server;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class BlogApiConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private List<Server> mongoDbServers;

}
