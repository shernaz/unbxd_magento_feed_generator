package com.unbxd;

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

    public GetProductsExecutor(int numberOfProducts) {
        this.executor = Executors.newFixedThreadPool(5);
        this.list = new ArrayList<Future<Boolean>>();
        this.numberOfProducts = numberOfProducts;
        this.numberOfProductsPerThread = 10;
    }
    public void getProducts() {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        List<Future<Boolean>> list = new ArrayList<Future<Boolean>>();
        for (int i = 0; i < 10; i++) {
            Callable<Boolean> worker = new PushToMongoCallable(i);
            Future<Boolean> submit = executor.submit(worker);
            list.add(submit);
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
