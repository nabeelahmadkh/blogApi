package com.nbroadcast.backend.api.blog;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import com.nbroadcast.backend.api.blog.health.MongoDbHealthCheck;
import com.nbroadcast.backend.api.blog.model.Server;
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
        MongoDbHealthCheck mongoHeathCheck = new MongoDbHealthCheck(generateMongoClient(configuration.getMongoDbServers()));
        environment.healthChecks().register("mongo", mongoHeathCheck);

        for (Map.Entry<String, HealthCheck.Result> entry : environment.healthChecks().runHealthChecks().entrySet()) {
            if (!entry.getValue().isHealthy()) {
                throw new Exception(String.format("Check health for %s failed!", entry.getKey()));
            }
        }
    }

    private MongoClient generateMongoClient(List<Server> mongoServers){
        List<ServerAddress> serverAddressList = new ArrayList<ServerAddress>();
        for (Server server : mongoServers) {
            serverAddressList.add(new ServerAddress(server.getHost(), server.getPort()));
        }
        return new MongoClient(serverAddressList);
    }

}
