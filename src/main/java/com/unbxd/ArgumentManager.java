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

        if (args.length % 2 != 0) {
            throw new Exception("Invalid number of arguments");
        }
        allowedArgs = new String[]{
                "sizeurl", "per-thread", "site-name", "secret-key", "username", "password"
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
        if(!this.baseUrl.equals("") && this.productsPerThread !=0 && !this.iSiteName.equals("") && !this.secretKey.equals("")) {
            return true;
        }
        System.out.println("Either of these are not set:\nsizeurl\tper-thread\tsite-name\tsecret-key");
        return false;
    }

    private boolean processArgument(String key, String value) {
        key = key.substring(2);

        if (Arrays.asList(this.allowedArgs).contains(key)) {
            if (key.equals("sizeurl")) {
                this.baseUrl = value;
            } else if(key.equals("per-thread")) {
                this.productsPerThread = Integer.parseInt(value);
            } else if(key.equals("site-name")) {
                this.iSiteName = value;
            } else if(key.equals("secret-key")) {
                this.secretKey = value;
            } else if(key.equals("username")) {
                this.username = value;
                this.authentication = true;
            } else if(key.equals("password")) {
                this.password = value;
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
