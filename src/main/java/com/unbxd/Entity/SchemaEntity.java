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
public class SchemaEntity extends AbstractEntity
{
    public SchemaEntity(String iSiteName) throws IOException {
        super(iSiteName);
        this.setUniqueField("fieldName");
        this.setCollection("schema_");
    }



}