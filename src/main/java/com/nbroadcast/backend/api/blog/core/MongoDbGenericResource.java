package com.nbroadcast.backend.api.blog.core;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class MongoDbGenericResource<T> {
    private String database;
    private MongoClient mongoClient;
    private ObjectMapper objectMapper;

    public MongoDbGenericResource(String database, MongoClient mongoClient){
        this.database = database;
        this.mongoClient = mongoClient;
        this.objectMapper = new ObjectMapper();
    }

    @POST
    @Path("/{collectionName}")
    public Response insertDocument(@PathParam("collectionName") String collectionName,
                                   T object){
        try {
            Document document = new Document(Document.parse(objectMapper.writeValueAsString(object)));
            mongoClient.getDatabase(database).getCollection(collectionName).insertOne(document);
            return Response.status(Response.Status.OK)
                    .entity("Document Successfully Inserted.")
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @GET
    @Path("/{collectionName}/{postId}")
    public Response getDocument(@PathParam("collectionName") String collectionName,
                                @PathParam("postId") String postId){
        try {
            Bson filter = Filters.eq("_id", postId);
            Document document = mongoClient.getDatabase(database).getCollection(collectionName).find(filter).first();
            return Response.status(Response.Status.OK)
                    .entity(document.toJson())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @DELETE
    @Path("/{collectionName}/{postId}")
    public Response deleteDocument(@PathParam("collectionName") String collectionName,
                                   @PathParam("postId") String postId){
        try{
            Bson filter = Filters.eq("_id", postId);
            DeleteResult result = mongoClient.getDatabase(database).getCollection(collectionName).deleteOne(filter);
            return Response.status(Response.Status.OK)
                    .entity(result.wasAcknowledged())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }
}
