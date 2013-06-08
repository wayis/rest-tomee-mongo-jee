package org.wayis.restmongo.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import java.net.UnknownHostException;

@Singleton
public class DBConnection {

    private DB database;

    @PostConstruct
    public void afterCreate() throws UnknownHostException {
        MongoClient client = new MongoClient();
        database = client.getDB("blog");
    }

    public DBCollection getCollection(String name) {
        return database.getCollection(name);
    }

}
