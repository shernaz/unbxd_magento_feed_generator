package com.unbxd;

import com.unbxd.DAO.AbstractDAO;
import com.unbxd.Entity.SchemaEntity;
import com.unbxd.Entity.SizeEntity;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main(String[] args) throws Exception {
        ArgumentManager arguments = new ArgumentManager(args);
        if(arguments.processArguments()) {
            int productsPerThread = arguments.productsPerThread;
            GetFromURL numberOfProductsResponse;
            if(arguments.username.equals("")) {
                numberOfProductsResponse = new GetFromURL(arguments.baseUrl);
            } else {
                numberOfProductsResponse = new GetFromURL(arguments.baseUrl, arguments.username, arguments.password);
            }

            int numberOfProducts = numberOfProductsResponse.getJSONResponse().getInt("size");
            SizeEntity sizeEntity = new SizeEntity(arguments.iSiteName);
            sizeEntity.setSize(numberOfProducts);

            System.out.println("NUM PRODUCTS: " + numberOfProducts);

            //Flush mongo collection
            if (arguments.flushMongo) {
                System.out.println("Flusing mongo");
                AbstractDAO abstractDAO = new AbstractDAO();
                abstractDAO.clearData(arguments.iSiteName);
            }

            // Process Schema separately
            System.out.println("Processing schema");
            URLManager urlManager = new URLManager(arguments.baseUrl);
            GetFromURL getSchemaFromUrl = new GetFromURL(urlManager.getProductsUrl() + "&limit=1", arguments.username, arguments.password);
            JSONObject schemaObject = getSchemaFromUrl.getJSONResponse();
            JSONArray schemas = schemaObject.getJSONObject("feed").getJSONObject("catalog").getJSONArray("schema");
            SchemaEntity schemaEntity = new SchemaEntity(arguments.iSiteName);
            for (int i = 0; i < schemas.length(); i++) {
                schemaEntity.upsert(schemas.getJSONObject(i));
            }
            System.out.println("Schema inserted into mongo");
            // Schema processed

            System.out.println("Start processing products");

            GetProductsExecutor productsExecutor = new GetProductsExecutor(numberOfProducts, productsPerThread, urlManager.getProductsUrl(), arguments.username, arguments.password);
            productsExecutor.pushProductsToMongo(arguments.iSiteName);
            System.out.println("All products pushed to mongo");


            // Generate feed file
            FeedFileManager fileManager = new FeedFileManager(arguments.iSiteName, arguments.secretKey);
            fileManager.generateFeedFile().pushFeed();
        } else {
            System.out.println("Invalid arguments");
        }

    }
}
