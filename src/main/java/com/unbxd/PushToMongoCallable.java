package com.unbxd;

/**
 * Created by albin on 4/29/15.
 */

import com.unbxd.Entity.ProductEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class PushToMongoCallable implements Callable<Boolean> {

    private int pageNumber;
    private int productsPerThread;
    private String baseUrl;
    private String url;
    private String iSiteName;
    private String username;
    private String password;

    public PushToMongoCallable(int pageNumber, String baseUrl, int productsPerThread, String iSiteName, String username, String password) {
        this.baseUrl = baseUrl;
        this.pageNumber = pageNumber;
        this.productsPerThread = productsPerThread;
        this.url = this.generateUrl();
        this.iSiteName = iSiteName;
        this.username = username;
        this.password = password;
    }

    private String generateUrl() {
        return this.baseUrl + "&limit=" + this.productsPerThread + "&start=" + this.pageNumber;
    }

    public Boolean call() throws Exception {
        // Call the API and push products to mongo
        GetFromURL getFromURL;
        if (this.username.equals("")) {
            getFromURL = new GetFromURL(this.url);
        } else {
            getFromURL = new GetFromURL(this.url, this.username, this.password);
        }

        JSONObject json = getFromURL.getJSONResponse();
        System.out.println("Got JSON response for " + this.url);
        try {
            if (json.getJSONObject("feed").getJSONObject("catalog").has("add")) {
                System.out.println("JSON response has products");
                JSONArray products = json.getJSONObject("feed").getJSONObject("catalog").getJSONObject("add").getJSONArray("items");
                ProductEntity productEntity = new ProductEntity(this.iSiteName);

                System.out.println("Inserting products for page: " + this.pageNumber);
                for (int i = 0; i < products.length(); i++) {
                    productEntity.upsert(products.getJSONObject(i));
                }
            } else {
                System.out.println("JSON response does not have productss");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return true;
    }
}
