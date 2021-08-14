package com.nbroadcast.backend.api.blog;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.nbroadcast.backend.api.blog.core.JacksonJsonExceptionMapper;
import com.nbroadcast.backend.api.blog.core.JerseyViolationExceptionMapper;
import com.nbroadcast.backend.api.blog.health.MongoDbHealthCheck;
import com.nbroadcast.backend.api.blog.api.Server;
import com.nbroadcast.backend.api.blog.resources.Blogposts;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlogApiApplication extends Application<BlogApiConfiguration> {

    public static void main(final String[] args) throws Exception {
        new BlogApiApplication().run(args);
    }

    @Override
    public String getName() {
        return "BlogApi";
    }

    @Override
    public void initialize(final Bootstrap<BlogApiConfiguration> bootstrap) {
        // TODO: application initialization
    }

    @Override
    public void run(final BlogApiConfiguration configuration,
                    final Environment environment) throws Exception {

        MongoClient mongoClient = generateMongoClient(configuration.getMongoDbServers());
        MongoDbHealthCheck mongoHeathCheck = new MongoDbHealthCheck(mongoClient);
        environment.healthChecks().register("mongo", mongoHeathCheck);

        //TODO: Check executor service for healthchecks
        for (Map.Entry<String, HealthCheck.Result> entry : environment.healthChecks().runHealthChecks().entrySet()) {
            if (!entry.getValue().isHealthy()) {
                throw new Exception(String.format("Check health for %s failed!", entry.getKey()));
            }
        }

        // Exception Mapper for Jersey Validation Exception
        environment.jersey().register(new JerseyViolationExceptionMapper());
        // Exception Mapper for Jackson Json Mapper Exception
        environment.jersey().register(new JacksonJsonExceptionMapper());

        // Registering Blog Api Endpoint.
        environment.jersey().register(new Blogposts("blog", mongoClient));
    }

    private MongoClient generateMongoClient(List<Server> mongoServers){
        List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
        for (Server server : mongoServers) {
            serverAddressList.add(new ServerAddress(server.getHost(), server.getPort()));
        }
        return new MongoClient(serverAddressList);
    }

}
