package com.nbroadcast.backend.api.blog;

import com.nbroadcast.backend.api.blog.model.Server;
import io.dropwizard.Configuration;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;
import java.util.List;

@Data
public class BlogApiConfiguration extends Configuration {

    @NotNull
    @JsonProperty
    private List<Server> mongoDbServers;

}
