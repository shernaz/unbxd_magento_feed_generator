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
            String baseUrl = "http://magento-sandbox.cloudapp.net/magento/index.php/recscore/catalog/products?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW";
//            int productsPerThread = arguments.productsPerThread;
//            GetFromURL numberOfProductsResponse = new GetFromURL("http://magento-sandbox.cloudapp.net/magento/index.php/recscore/catalog/size?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW");
//            int numberOfProducts = numberOfProductsResponse.getJSONResponse().getInt("size");

            // Process Schema separately
            GetFromURL getSchemaFromUrl = new GetFromURL(baseUrl + "&limit=1");
            JSONObject schemaObject = getSchemaFromUrl.getJSONResponse();
            JSONArray schemas = schemaObject.getJSONObject("feed").getJSONObject("catalog").getJSONArray("schema");
            SchemaEntity schemaEntity = new SchemaEntity(arguments.iSiteName);
            for (int i = 0; i< schemas.length(); i++) {
                schemaEntity.insert(schemas.getJSONObject(i));
            }
            schemaEntity.push();

//            GetProductsExecutor productsExecutor = new GetProductsExecutor(numberOfProducts, productsPerThread, baseUrl);
        } else {
            System.out.println("Invalid arguments");
        }

    }
}
