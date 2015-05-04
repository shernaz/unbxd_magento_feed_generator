package com.unbxd.Entity;

import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.unbxd.DAO.AbstractDAO;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by albin on 5/3/15.
 */
public class SchemaEntity
{
    private String iSiteName;
    private List<DBObject> schemas;
    public SchemaEntity(String iSiteName) {
        this.iSiteName = iSiteName;
        this.schemas = new ArrayList<DBObject>();
    }

    public SchemaEntity insert(JSONObject schema) {
        DBObject dbObject = (DBObject) JSON.parse(schema.toString());
        this.schemas.add(dbObject);
        return this;
    }

    public SchemaEntity clear() {
        this.schemas = new ArrayList<DBObject>();
        return this;
    }

    public boolean push() throws IOException {
        AbstractDAO schemaDAO = new AbstractDAO();
        schemaDAO.setCollection("schema_" + this.iSiteName);
        schemaDAO.insertDocuments(this.schemas);
        return true;
    }
}
