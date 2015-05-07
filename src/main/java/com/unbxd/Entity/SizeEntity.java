package com.unbxd.Entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;

import java.io.IOException;

/**
 * Created by albin on 07/05/15.
 */
public class SizeEntity extends AbstractEntity {

    public SizeEntity(String iSiteName) throws IOException {
        super(iSiteName);
        this.dao.setCollection("size");
    }

    public boolean setSize(int size) {
        DBObject dbObject = (DBObject) JSON.parse("{\"iSiteName\": \"" + this.iSiteName + "\", \"size\": \"" + Integer.toString(size) + "\"}");
        this.dao.getCollection().update(
                new BasicDBObject("iSiteName", this.iSiteName),
                dbObject,
                true,
                false
        );
        return true;
    }
}
