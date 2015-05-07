package com.unbxd;

import com.unbxd.Entity.ProductEntity;
import com.unbxd.Entity.SchemaEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.util.EntityUtils;

import java.io.File;
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

    public void pushFeed() throws IOException {
        String filePath = "samplefeed.json";
        String url = URLManager.getFeedUploadAPI(this.iSiteName, this.secretKey);
        File file = new File(filePath);
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file", new FileBody(file));
        HttpResponse returnResponse = Request.Post(url)
                .body(entity)
                .execute().returnResponse();

        System.out.println("Response status: " + returnResponse.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(returnResponse.getEntity()));
    }
}
