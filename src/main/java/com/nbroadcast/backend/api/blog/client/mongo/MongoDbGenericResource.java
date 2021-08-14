package com.nbroadcast.backend.api.blog.client.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.nbroadcast.backend.api.blog.api.response.ResponseStatus;
import com.nbroadcast.backend.api.blog.util.ResponseUtil;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

public abstract class MongoDbGenericResource<T> {
    private static final Logger LOGGER = LoggerFactory.getLogger(MongoDbGenericResource.class);
    private String database;
    private MongoClient mongoClient;
    private ObjectMapper objectMapper;

    public MongoDbGenericResource(String database, MongoClient mongoClient){
        this.database = database;
        this.mongoClient = mongoClient;
        this.objectMapper = new ObjectMapper();
    }

    @Path("create/{collectionName}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response insertDocument(@NotNull @PathParam("collectionName") String collectionName,
                                   @NotNull T object){
        try {
            Document document = new Document(Document.parse(objectMapper.writeValueAsString(object)));
            mongoClient.getDatabase(database).getCollection(collectionName).insertOne(document);
            LOGGER.debug("Inserted Document in the collection {}",collectionName);
            return Response.status(Response.Status.OK)
                    .entity(ResponseUtil.generateBlogResponse(ResponseStatus.SUCCESS, null, null))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(ResponseUtil.generateBlogResponse(ResponseStatus.FAILED, null, e.getMessage()))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @Path("update/{collectionName}/{postId}")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateDocument(@NotNull @PathParam("collectionName") String collectionName,
                                   @NotNull @PathParam("postId") ObjectId postId,
                                   @NotNull T object){
        try {
            Document document = new Document(Document.parse(objectMapper.writeValueAsString(object)));
            Bson filter = Filters.eq("_id", postId);
            UpdateResult result = mongoClient.getDatabase(database).getCollection(collectionName).updateOne(filter, document);
            LOGGER.debug("Updated Document {} in the collection {}. Number of documents matched {}",postId, collectionName, result.getMatchedCount());
            return Response.status(Response.Status.OK)
                    .entity(ResponseUtil.generateBlogResponse(ResponseStatus.SUCCESS, null, null))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @Path("read/{collectionName}/{postId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocument(@NotNull @PathParam("collectionName") String collectionName,
                                @NotNull @PathParam("postId") ObjectId postId) {
        try {
            Bson filter = Filters.eq("_id", postId);
            Document document = mongoClient.getDatabase(database).getCollection(collectionName).find(filter).first();
            LOGGER.debug("Fetched Document from the collection {}",collectionName);
            LOGGER.info("testing info logging");
            LOGGER.error("testing error logging ");
            return Response.status(Response.Status.OK)
                    .entity(ResponseUtil.generateBlogResponse(ResponseStatus.SUCCESS, document.toJson(), null))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @Path("read/allposts/{collectionName}/{skip}/{limit}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDocuments(@NotNull @PathParam("collectionName") String collectionName,
                                 @NotNull @PathParam("skip") Integer skip,
                                 @NotNull @PathParam("limit") Integer limit) {
        try{
            FindIterable<Document> documents = mongoClient.getDatabase(database).getCollection(collectionName).find().skip(skip).limit(limit);
            List<Document> documentsList =  new ArrayList<>();
            for (Document document: documents) {
                documentsList.add(document);
            }

            return Response.status(Response.Status.OK)
                    .entity(ResponseUtil.generateBlogResponse(ResponseStatus.SUCCESS, documentsList.toString(), null))
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .type(MediaType.APPLICATION_JSON)
                    .build();
        }
    }

    @Path("delete/{collectionName}/{postId}")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteDocument(@PathParam("collectionName") String collectionName,
                                   @PathParam("postId") ObjectId postId){
        try{
            Bson filter = Filters.eq("_id", postId);
            DeleteResult result = mongoClient.getDatabase(database).getCollection(collectionName).deleteOne(filter);
            LOGGER.debug("Deleted Document {} in the collection {}",postId, collectionName);
            return Response.status(Response.Status.OK)
                    .entity(ResponseUtil.generateBlogResponse(
                            result.wasAcknowledged() ? ResponseStatus.SUCCESS : ResponseStatus.FAILED, null, null))
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
