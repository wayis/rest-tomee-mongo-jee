package org.wayis.restmongo.mongo;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import org.wayis.restmongo.property.annotation.Property;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.inject.Inject;
import java.net.UnknownHostException;

@Singleton
public class DBConnection {

    private DB database;
    @Inject
    @Property(key = "mongodb.host", defaultValue = "localhost")
    private String host;
    @Inject
    @Property(key = "mongodb.port", defaultValue = "27017")
    private Integer port;
    @Inject
    @Property(key = "mongodb.dbname", mandatory = true)
    private String dbName;

    @PostConstruct
    public void afterCreate() throws UnknownHostException {
        MongoClient client = new MongoClient(host, port);
        database = client.getDB(dbName);
    }

    public DBCollection getCollection(String name) {
        return database.getCollection(name);
    }

}
