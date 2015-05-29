package com.unbxd;

import java.util.Arrays;

/**
 * Created by albin on 30/04/15.
 */
public class ArgumentManager {

    public String baseUrl;
    public int productsPerThread;
    public String iSiteName;
    public String secretKey;
    public Boolean authentication;
    public String username;
    public String password;
    public Boolean flushMongo;
    String allowedArgs[];
    String args[];

    public ArgumentManager(String[] args) throws Exception {
        this.args = args;
        this.baseUrl = "";
        this.iSiteName = "";
        this.secretKey = "";
        this.productsPerThread = 0;
        this.username = "";
        this.password = "";
        this.authentication = false;
        this.flushMongo = true;

        if (args.length % 2 != 0) {
            throw new Exception("Invalid number of arguments");
        }
        allowedArgs = new String[]{
                "sizeurl", "per-thread", "site-name", "secret-key", "username", "password", "no-flush"
        };
    }

    public boolean processArguments() {
        for (int i = 0; i < this.args.length; i++) {
            String key = "", value = "";
            key = this.args[i];
            int numberOfValues = this.processArgument(args, key, i);
            if (numberOfValues >= 0) {
                i = i + numberOfValues;
            }
        }
        if(!this.baseUrl.equals("") && this.productsPerThread !=0 && !this.iSiteName.equals("") && !this.secretKey.equals("")) {
            return true;
        }
        System.out.println("Either of these are not set:\nsizeurl\tper-thread\tsite-name\tsecret-key");
        return false;
    }


    /*
    Input: args, key, index
    Return: number of expected values for the key passed
    Variables used:
        values: number of expected values for the currently parsing argument
    From the arguments array, sets the corresponding values based on the current key
    */
    private int processArgument(String[] args, String key, int i) {
        key = key.substring(2);
        int values = 0;
        if (Arrays.asList(this.allowedArgs).contains(key)) {
            if (key.equals("sizeurl")) {
                this.baseUrl = args[i + 1];
                values = 1;
            } else if (key.equals("per-thread")) {
                this.productsPerThread = Integer.parseInt(args[i + 1]);
                values = 1;
            } else if (key.equals("site-name")) {
                this.iSiteName = args[i + 1];
                values = 1;
            } else if (key.equals("secret-key")) {
                this.secretKey = args[i + 1];
                values = 1;
            } else if (key.equals("username")) {
                this.username = args[i + 1];
                this.authentication = true;
                values = 1;
            } else if (key.equals("password")) {
                this.password = args[i + 1];
                values = 1;
            } else if (key.equals("no-flush")) {
                this.flushMongo = false;
                values = 0;
            } else {
                values = -1;
            }

            return values;
        }
        return -1;
    }
}
