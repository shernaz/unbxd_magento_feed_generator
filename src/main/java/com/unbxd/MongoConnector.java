package com.unbxd;

import com.mongodb.*;

import java.io.IOException;

/**
 * Created by albin on 05/05/15.
 */

public class MongoConnector {

    private static MongoConnector connector;
    private static MongoClient mongoClient;
    public static DB db;
    private static DBCollection collection;
    private UnbxdProperties unbxdProperties;

    protected MongoConnector() throws IOException {

        unbxdProperties = UnbxdProperties.getInstance();
        String dbName = unbxdProperties.getProperty("MONGO_DB");
        MongoClientOptions options = MongoClientOptions.builder()
                .connectionsPerHost(100)
                .autoConnectRetry(true)
                .socketKeepAlive(true)
                .build();
        this.mongoClient = new MongoClient(new ServerAddress("localhost"), options);
        this.db = mongoClient.getDB(dbName);
    }

    public static MongoConnector getInstance() throws IOException {
        if (connector == null) {
            System.out.println("Creating a new connector");
            connector = new MongoConnector();
        }
        return connector;
    }

    public void close() throws IOException {
        if (this.mongoClient != null) {
            this.mongoClient.close();
            this.mongoClient = null;
        }
    }

}