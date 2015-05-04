package com.unbxd.DAO;

/**
 * Created by albin on 5/2/15.
 */
import com.mongodb.*;
import com.unbxd.UnbxdProperties;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;


import java.util.List;

public class AbstractDAO {
    private UnbxdProperties unbxdProperties;
    private Properties properties;
    private String dbName;
    private String collectionName;
    private MongoClient mongoClient;
    private DB db;
    private DBCollection collection;

    public AbstractDAO() throws IOException {
        if (null == this.db || null == this.collection){

            unbxdProperties = UnbxdProperties.getInstance();
            dbName = unbxdProperties.getProperty("MONGO_DB");
            MongoClientOptions options = MongoClientOptions.builder()
                    .connectionsPerHost(100)
                    .autoConnectRetry(true)
                    .socketKeepAlive(true)
                    .build();
            this.mongoClient = new MongoClient(new ServerAddress("localhost"), options);
            this.db = mongoClient.getDB(dbName);
            this.collectionName = "";
        }
    }

    public AbstractDAO setCollection(String collectionName) {
        this.collectionName = collectionName;
        this.collection = this.db.getCollection(collectionName);
        return this;
    }
    public DB getDb() {
        return this.db;
    }

    public DBCollection getCollection() {
        return this.collection;
    }

    public AbstractDAO insertDocuments(List<DBObject> documents) {
        try {
            this.collection.insert(documents);
        } catch (Exception ex) {
            System.out.println("UNBXD_EXCEPTION: " + ex.getMessage());
        }

        return this;
    }

    public AbstractDAO insertDocument(DBObject document) {
        this.collection.insert(document);
        return this;
    }

    public DBObject getDocument(BasicDBObject query) {
        DBObject object = this.collection.findOne(query);
        return object;
    }

    public DBCursor getDocuments(BasicDBObject query) {
        DBCursor cursor = this.collection.find(query);
        return cursor;
    }

    public AbstractDAO updateDocument(DBObject oldDocument, DBObject newDocument) {
        this.collection.update(oldDocument, newDocument);
        return this;
    }

    public AbstractDAO removeDocument(DBObject document) {
        this.collection.remove(document);
        return this;
    }

}

