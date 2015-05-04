package com.unbxd.Entity;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
import com.unbxd.DAO.AbstractDAO;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by albin on 5/4/15.
 */
public class ProductEntity extends AbstractEntity
{

    public ProductEntity(String iSiteName) throws IOException {
        super(iSiteName);
        this.setUniqueField("uniqueId");
        this.setCollection("products_");
    }


}
