package com.unbxd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * Created by albin on 4/30/15.
 */
public class GetProductsExecutor {
    ExecutorService executor;
    List<Future<Boolean>> list;
    int numberOfProducts;
    int numberOfProductsPerThread;
    int numberOfConcurrentThreads;
    int totalNumberOfThreadsRequired;
    String baseUrl;

    public GetProductsExecutor(int numberOfProducts, int productsPerThread, String baseUrl) throws IOException {
        this.baseUrl = baseUrl;
        UnbxdProperties propertiesObj = new UnbxdProperties();
        Properties properties = propertiesObj.getProperties();

        this.numberOfProducts = numberOfProducts;
        this.numberOfProductsPerThread = productsPerThread;
        this.numberOfConcurrentThreads = Integer.parseInt(properties.getProperty("NUMBER_OF_CUNCURRENT_THREADS"));
        this.totalNumberOfThreadsRequired = (int) Math.ceil((double) this.numberOfProducts / this.numberOfProductsPerThread);
        System.out.println("Total number of products: " + this.numberOfProducts);
        System.out.println("Total number of products per thread: " + this.numberOfProductsPerThread);
        System.out.println("Total number of threads: " + this.totalNumberOfThreadsRequired);
        this.executor = Executors.newFixedThreadPool(this.numberOfConcurrentThreads);
        this.list = new ArrayList<Future<Boolean>>();
    }
    public void getProducts() {
        for (int i = 0; i < this.totalNumberOfThreadsRequired; i++) {
            Callable<Boolean> worker = new PushToMongoCallable(i, this.baseUrl, this.numberOfProductsPerThread);
            Future<Boolean> submit = this.executor.submit(worker);
            this.list.add(submit);
        }
        for (Future<Boolean> future : list) {
            try {
                if(future.get()) {
                    System.out.println("Callable returned true");
                } else {
                    System.out.println("Callable returned false");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        executor.shutdown();
    }
}
