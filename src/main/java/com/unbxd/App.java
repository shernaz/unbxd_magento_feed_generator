package com.unbxd;

import com.unbxd.Entity.SchemaEntity;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main(String[] args) throws Exception {
        ArgumentManager arguments = new ArgumentManager(args);
        if(arguments.processArguments()) {
//            String baseUrl = "http://magento-sandbox.cloudapp.net/magento/index.php/recscore/catalog/products?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW";
            int productsPerThread = arguments.productsPerThread;
            GetFromURL numberOfProductsResponse;
            if(arguments.username.equals("")) {
                System.out.println("Without auth");
                numberOfProductsResponse = new GetFromURL(arguments.baseUrl);
            } else {
                System.out.println("With auth");
                numberOfProductsResponse = new GetFromURL(arguments.baseUrl, arguments.username, arguments.password);
            }

            int numberOfProducts = numberOfProductsResponse.getJSONResponse().getInt("size");

            System.out.println("NUM PRODUCTS: " + numberOfProducts);

            // Process Schema separately
            System.out.println("Processing schema");
            GetFromURL getSchemaFromUrl = new GetFromURL(arguments.baseUrl + "&limit=1");
            JSONObject schemaObject = getSchemaFromUrl.getJSONResponse();
            JSONArray schemas = schemaObject.getJSONObject("feed").getJSONObject("catalog").getJSONArray("schema");
            SchemaEntity schemaEntity = new SchemaEntity(arguments.iSiteName);
            for (int i = 0; i< schemas.length(); i++) {
                schemaEntity.upsert(schemas.getJSONObject(i));
            }
            System.out.println("Schema inserted into mongo");
            // Schema processed

            System.out.println("Start processing products");
            URLManager urlManager = new URLManager(arguments.baseUrl);
            GetProductsExecutor productsExecutor = new GetProductsExecutor(numberOfProducts, productsPerThread, urlManager.getProductsUrl());
            productsExecutor.pushProductsToMongo(arguments.iSiteName);
            System.out.println("All products pushed to mongo");
        } else {
            System.out.println("Invalid arguments");
        }

    }
}
