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
    private String feedFileName;

    public FeedFileManager(String iSiteName, String secretKey) {
        this.iSiteName = iSiteName;
        this.secretKey = secretKey;
        this.feedFileName = iSiteName + "_feed.json";
    }

    public FeedFileManager generateFeedFile() throws IOException {
        SchemaEntity schemaEntity = new SchemaEntity(this.iSiteName);
        String schemaString = schemaEntity.getAsString();

        String startString = "{\n" +
                "\"feed\": {\n" +
                "\"catalog\": {\n" +
                "\"schema\":" + schemaString + ",\n" +
                "\"add\": {\n" +
                "\"items\": ";
        String endString = "}\n" +
                "}\n" +
                "}\n" +
                "}";
        PrintWriter out = new PrintWriter(this.feedFileName);
        out.println(startString + "[");
        this.printProductsToFile(out);
        out.println("]" + endString);
        out.close();
        return this;
    }

    private void printProductsToFile(PrintWriter out) throws IOException {
        ProductEntity productEntity = new ProductEntity(this.iSiteName);
        long count = productEntity.count();
        int numberOfPages = (int) Math.ceil((double) count / 10000);
        for (int i = 1; i <= numberOfPages; i++) {
            System.out.println("printing page " + i);
            String tempString = productEntity.getAsString(10000, i).trim();
            tempString = tempString.substring(1, tempString.length() - 1);
            String productString = "\n" + tempString + (i == numberOfPages ? "" : ",");
            out.println(productString);
        }
    }

    public void pushFeed() throws IOException {
        String url = URLManager.getFeedUploadAPI(this.iSiteName, this.secretKey);
        File file = new File(this.feedFileName);
        MultipartEntity entity = new MultipartEntity();
        entity.addPart("file", new FileBody(file));
        HttpResponse returnResponse = Request.Post(url)
                .body(entity)
                .execute().returnResponse();

        System.out.println("Response status: " + returnResponse.getStatusLine().getStatusCode());
        System.out.println(EntityUtils.toString(returnResponse.getEntity()));
    }
}
