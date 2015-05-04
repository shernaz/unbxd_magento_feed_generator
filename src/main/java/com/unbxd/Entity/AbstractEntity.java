package com.unbxd.Entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.unbxd.DAO.AbstractDAO;
import com.unbxd.General;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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



}