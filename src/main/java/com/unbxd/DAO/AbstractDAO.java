package com.unbxd.DAO;

/**
 * Created by albin on 5/2/15.
 */

import com.mongodb.*;
import com.unbxd.General;
import com.unbxd.MongoConnector;

import java.io.IOException;
import java.util.List;

public class AbstractDAO {
    private String collectionName;
    private static DBCollection collection;
    private MongoConnector connector;

    public AbstractDAO() throws IOException {
        connector = MongoConnector.getInstance();
        this.collectionName = "";
    }

    public AbstractDAO setCollection(String collectionName) {
        this.collectionName = collectionName;
        this.collection = this.connector.db.getCollection(collectionName);
        return this;
    }
    public DB getDb() {
        return this.connector.db;
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
        if (!this.collectionName.equals("")) {
            this.collection.insert(document);
        }
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

    public void clearData(String iSiteName) {

        iSiteName = General.formatISiteName(iSiteName);
        this.connector.db.getCollection("products_" + iSiteName).drop();
        this.connector.db.getCollection("schema_" + iSiteName).drop();
    }

}

