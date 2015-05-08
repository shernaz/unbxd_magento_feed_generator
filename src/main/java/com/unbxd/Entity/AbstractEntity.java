package com.unbxd.Entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.unbxd.DAO.AbstractDAO;
import com.unbxd.General;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by albin on 5/3/15.
 */
public class AbstractEntity
{
    protected String iSiteName;
    protected AbstractDAO dao;
    protected String collectionName;
    protected  String uniqueField;

    public AbstractEntity(String iSiteName) throws IOException {
        this.iSiteName = General.formatISiteName(iSiteName);
        this.dao = new AbstractDAO();
        this.collectionName = "";
        this.uniqueField = "";
    }

    protected void setCollection(String prefix) {
        this.collectionName = prefix + this.iSiteName;
        this.dao.setCollection(this.collectionName);
    }

    protected void setUniqueField(String uniqueField) {
        this.uniqueField = uniqueField;
    }

    public AbstractEntity upsert(JSONObject schema)  {
        DBObject dbObject = (DBObject) JSON.parse(schema.toString());
        this.dao.getCollection().update(
                new BasicDBObject(this.uniqueField, schema.getString(this.uniqueField)),
                dbObject,
                true,
                false
        );
        return this;
    }

    public String getAsString() {
        DBCursor cursor = this.dao.getCollection().find(new BasicDBObject(), new BasicDBObject("_id", 0));
        String serialize = JSON.serialize(cursor);
        return serialize;
    }

    public String getAsString(int limit) {
        DBCursor cursor = this.dao.getCollection().find(new BasicDBObject(), new BasicDBObject("_id", 0)).limit(limit);
        String serialize = JSON.serialize(cursor);
        return serialize;
    }

    public String getAsString(int limit, int pageNumber) {
        DBCursor cursor = this.dao.getCollection().find(new BasicDBObject(), new BasicDBObject("_id", 0)).skip((pageNumber - 1) * limit).limit(limit);
        String serialize = JSON.serialize(cursor);
        return serialize;
    }

    public long count() {
        return this.dao.getCollection().getCount();
    }



}