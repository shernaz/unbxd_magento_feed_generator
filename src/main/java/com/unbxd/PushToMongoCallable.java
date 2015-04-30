package com.unbxd;

/**
 * Created by albin on 4/29/15.
 */
import java.util.concurrent.Callable;

public class PushToMongoCallable implements Callable<Boolean> {

    private int pageNumber;

    public PushToMongoCallable(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Boolean call() throws Exception {
        System.out.println("Page number: " + this.pageNumber);
        return (this.pageNumber % 2 == 0) ? true : false;
    }
}
