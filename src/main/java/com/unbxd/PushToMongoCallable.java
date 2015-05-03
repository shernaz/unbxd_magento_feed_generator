package com.unbxd;

/**
 * Created by albin on 4/29/15.
 */
import org.json.JSONObject;

import java.util.concurrent.Callable;

public class PushToMongoCallable implements Callable<Boolean> {

    private int pageNumber;
    private int productsPerThread;
    private String baseUrl;
    private String url;

    public PushToMongoCallable(int pageNumber, String baseUrl, int productsPerThread) {
        this.baseUrl = baseUrl;
        this.pageNumber = pageNumber;
        this.productsPerThread = productsPerThread;
        this.url = this.generateUrl();
    }

    private String generateUrl() {
        return this.baseUrl + "&limit=" + this.productsPerThread + "&start=" + this.pageNumber;
    }

    public Boolean call() throws Exception {
        System.out.println("URL: " + this.url);
        // Call the API and push products to mongo
        GetFromURL getFromURL = new GetFromURL(this.url);
        JSONObject json = getFromURL.getJSONResponse();
        return (this.pageNumber % 2 == 0) ? true : false;
    }
}
