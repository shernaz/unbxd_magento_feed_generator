package com.unbxd;

import java.util.Arrays;

/**
 * Created by albin on 30/04/15.
 */
public class ArgumentManager {

    public String baseUrl;
    public int productsPerThread;
    String allowedArgs[];
    String args[];

    public ArgumentManager(String[] args) throws Exception {
        this.args = args;
        this.baseUrl = "";
        this.productsPerThread = 0;
        if (args.length % 2 != 0) {
            throw new Exception("Invalid number of arguments");
        }
        allowedArgs = new String[]{
                "baseurl", "per-thread"
        };



    }

    public boolean processArguments() {
        for (int i = 0; i < this.args.length; i++) {
            String key = "", value = "";
            if (i % 2 == 0) {
                key = args[i++];
                value = args[i];
            }
            this.processArgument(key, value);
        }
        if(this.baseUrl != "" && this.productsPerThread !=0) {
            return true;
        }
        return false;
    }

    private boolean processArgument(String key, String value) {
        key = key.substring(2);

        if (Arrays.asList(this.allowedArgs).contains(key)) {
            if(key.equals("baseurl")) {
                this.baseUrl = value;
            } else if(key.equals("per-thread")) {
                this.productsPerThread = Integer.parseInt(value);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
