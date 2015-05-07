package com.unbxd;

import com.unbxd.Entity.ProductEntity;
import com.unbxd.Entity.SchemaEntity;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by albin on 07/05/15.
 */
public class FeedFileManager {
    protected String iSiteName;
    protected String secretKey;
    protected String finalString;

    public FeedFileManager(String iSiteName, String secretKey) {
        this.iSiteName = iSiteName;
        this.secretKey = secretKey;
        this.finalString = "";
    }

    public FeedFileManager generateFeedFile() throws IOException {
        SchemaEntity schemaEntity = new SchemaEntity(this.iSiteName);
        String schemaString = schemaEntity.getAllAsString();
        ProductEntity productEntity = new ProductEntity(this.iSiteName);
        String productString = productEntity.getAllAsString();
        this.finalString = "{\n" +
                "\"feed\": {\n" +
                "\"catalog\": {\n" +
                "\"schema\":" + schemaString + ",\n" +
                "\"add\": {\n" +
                "\"items\": " + productString + "}\n" +
                "}\n" +
                "}\n" +
                "}";
        PrintWriter out = new PrintWriter("samplefeed.json");
        out.println(this.finalString);
        out.close();
        return this;
    }

    public void pushFeed() {

    }
}
