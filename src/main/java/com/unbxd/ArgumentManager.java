package com.unbxd;

import java.util.Arrays;

/**
 * Created by albin on 30/04/15.
 */
public class ArgumentManager {

    public String baseUrl;
    public int productsPerThread;
    String allowedArgs[];

    public ArgumentManager(String[] args) throws Exception {
        System.out.println(args.length);
        if (args.length % 2 != 0) {
            throw new Exception("Invalid number of arguments");
        }
        allowedArgs = new String[]{
                "baseurl", "per-thread"
        };


        for (int i = 0; i < args.length; i++) {
            String key = "", value = "";
            if (i % 2 == 0) {
                key = args[i++];
                value = args[i];
            }
            this.processArgument(key, value);
            System.out.println("Key: " + key + "\tValue: " + value);
        }
    }

    private boolean processArgument(String key, String value) {
        if (Arrays.asList(this.allowedArgs).contains(key)) {
            
        }
        return true;
    }
}
