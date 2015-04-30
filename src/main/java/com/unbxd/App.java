package com.unbxd;

import org.json.JSONException;

import java.io.IOException;

/**
 * Hello world!
 *
 */

public class App
{
    public static void main(String[] args) throws JSONException, IOException
    {
        String baseUrl = "http://magento-sandbox.cloudapp.net/magento/index.php/recscore/catalog/products?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW";
        int productsPerThread = 100;
        GetFromURL numberOfProductsResponse = new GetFromURL("http://magento-sandbox.cloudapp.net/magento/index.php/recscore/catalog/size?site=Main%20Website&auth=aHR0cDovL21hZ2VudG8tc2FuZGJveC5jbG91ZGFwcC5uZXQvbW");
        int numberOfProducts = numberOfProductsResponse.callURL().getJSONResponse().getInt("size");
        System.out.println(numberOfProducts);
        GetProductsExecutor productsExecutor = new GetProductsExecutor(numberOfProducts, productsPerThread, baseUrl);
    }
}
