package org.wayis.restmongo.service;


import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.wayis.restmongo.mongo.DBConnection;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/blog")
@Produces(MediaType.APPLICATION_JSON)
public class BlogService {

    @Inject
    private DBConnection database;
    private DBCollection posts;

    @PostConstruct
    public void afterCreate() {
        posts = database.getCollection("posts");
    }

    @Path("/list")
    @GET
    public DBCursor list() {
        return posts.find();
    }

    @Path("/get/{postId}")
    @GET
    public DBObject findById(@PathParam("postId") final String postId) {
        return posts.findOne(new BasicDBObject("_id", new ObjectId(postId)));
    }

}
