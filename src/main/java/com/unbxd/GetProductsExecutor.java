package com.unbxd;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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
    String username;
    String password;

    public GetProductsExecutor(int numberOfProducts, int productsPerThread, String baseUrl, String username, String password) throws IOException {
        this.baseUrl = baseUrl;
        UnbxdProperties propertiesObj = new UnbxdProperties();

        this.username = username;
        this.password = password;
        this.numberOfProducts = numberOfProducts;
        this.numberOfProductsPerThread = productsPerThread;
        this.numberOfConcurrentThreads = Integer.parseInt(propertiesObj.getProperty("NUMBER_OF_CUNCURRENT_THREADS"));
        this.totalNumberOfThreadsRequired = (int) Math.ceil((double) this.numberOfProducts / this.numberOfProductsPerThread);
        this.executor = Executors.newFixedThreadPool(this.numberOfConcurrentThreads);
        this.list = new ArrayList<Future<Boolean>>();
    }
    public void pushProductsToMongo(String iSiteName) {
        for (int i = 0; i < this.totalNumberOfThreadsRequired; i++) {
            Callable<Boolean> worker = new PushToMongoCallable(i, this.baseUrl, this.numberOfProductsPerThread, iSiteName, this.username, this.password);
            Future<Boolean> submit = this.executor.submit(worker);
            this.list.add(submit);
        }
        int i = 0;
        for (Future<Boolean> future : list) {
            try {
                if (future.get()) {
                    System.out.println("Products inserted for page: " + i);

                } else {
                    System.out.println("ERROR : Products not inserted for page: " + i);
                }

                i++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
    }
}
