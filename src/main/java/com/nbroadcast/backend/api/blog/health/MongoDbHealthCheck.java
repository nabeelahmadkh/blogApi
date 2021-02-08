package com.nbroadcast.backend.api.blog.health;

import com.codahale.metrics.health.HealthCheck;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

public class MongoDbHealthCheck extends HealthCheck {

    private final MongoClient mongoClient;

    public MongoDbHealthCheck(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
    }

    @Override
    protected Result check() throws Exception {
        try {
            System.out.println("in the mongo heath checks");
            MongoIterable<String> databases = mongoClient.listDatabaseNames();
            MongoDatabase database = mongoClient.getDatabase("blog");
            database.listCollections();
            System.out.println("mongo heath check complete");
        } catch (Throwable t) {
            System.out.println("mongo in unhealthy state");
            return Result.unhealthy(t);
        }
        System.out.println("mongo in healthy state");
        return Result.healthy();
    }
}
