package com.nbroadcast.backend.api.blog.resources;


import com.mongodb.MongoClient;
import com.nbroadcast.backend.api.blog.core.MongoDbGenericResource;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/blogpost")
@Produces(MediaType.APPLICATION_JSON)
public class Blogposts extends MongoDbGenericResource<Blogposts> {
    public Blogposts(String databaseName, MongoClient mongoClient){
        super(databaseName, mongoClient);
    }
}
