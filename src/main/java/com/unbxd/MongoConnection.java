package com.unbxd;

import com.mongodb.DB;
import com.mongodb.MongoClient;

import java.net.UnknownHostException;

/**
 * Created by albin on 5/1/15.
 */

//import org.;
public class MongoConnection {
    MongoClient mongoClient;
    DB db;
    public MongoConnection() throws UnknownHostException {
        this.mongoClient = new MongoClient();
        this.db = mongoClient.getDB("unbxd_magento_db");
    }


}
