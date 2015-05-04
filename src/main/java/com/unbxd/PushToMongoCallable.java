package com.unbxd;

/**
 * Created by albin on 4/29/15.
 */
import com.unbxd.Entity.ProductEntity;
import com.unbxd.Entity.SchemaEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class PushToMongoCallable implements Callable<Boolean> {

    private int pageNumber;
    private int productsPerThread;
    private String baseUrl;
    private String url;
    private String iSiteName;

    public PushToMongoCallable(int pageNumber, String baseUrl, int productsPerThread, String iSiteName) {
        this.baseUrl = baseUrl;
        this.pageNumber = pageNumber;
        this.productsPerThread = productsPerThread;
        this.url = this.generateUrl();
        this.iSiteName = iSiteName;
    }

    private String generateUrl() {
        return this.baseUrl + "&limit=" + this.productsPerThread + "&start=" + this.pageNumber;
    }

    public Boolean call() throws Exception {
        System.out.println("URL: " + this.url);
        // Call the API and push products to mongo
        GetFromURL getFromURL = new GetFromURL(this.url);
        JSONObject json = getFromURL.getJSONResponse();

        JSONArray products = json.getJSONObject("feed").getJSONObject("catalog").getJSONObject("items").getJSONArray("add");
        ProductEntity productEntity = new ProductEntity(this.iSiteName);

        System.out.println("Inserting products for page: " + this.pageNumber);
        for (int i = 0; i< products.length(); i++) {
            productEntity.upsert(products.getJSONObject(i));
        }
        return true;
    }
}
