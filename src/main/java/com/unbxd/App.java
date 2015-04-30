package com.unbxd;

/**
 * Hello world!
 *
 */

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class App 
{
    public static void main( String[] args )

    {
        GetFromURL numberOfProductsResponse = new GetFromURL("http://magento-sandbox.cloudapp.net/magento/index.php/datafeeder/config/size?site=Main%20Website");
        int numberOfProducts = Integer.parseInt(numberOfProductsResponse.callURL().getResponse());

    }
}
