package com.nbroadcast.backend.api.blog.resources;


import com.codahale.metrics.annotation.Timed;
import com.mongodb.MongoClient;
import com.nbroadcast.backend.api.blog.client.mongo.MongoDbGenericResource;
import com.nbroadcast.backend.api.blog.api.model.Blogpost;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Timed
@Path("/blogpost")
@Produces(MediaType.APPLICATION_JSON)
public class Blogposts extends MongoDbGenericResource<Blogpost> {
    public Blogposts(String databaseName, MongoClient mongoClient){
        super(databaseName, mongoClient);
    }
}
