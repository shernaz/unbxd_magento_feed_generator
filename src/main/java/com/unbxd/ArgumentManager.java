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
    String allowedArgs[];
    String args[];

    public ArgumentManager(String[] args) throws Exception {
        this.args = args;
        this.baseUrl = "";
        this.iSiteName = "";
        this.secretKey = "";
        this.productsPerThread = 0;
        if (args.length % 2 != 0) {
            throw new Exception("Invalid number of arguments");
        }
        allowedArgs = new String[]{
                "baseurl", "per-thread", "site-name", "secret-key"
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
        System.out.println("Either of these are not set:\nbaseurl\tper-thread\tsite-name\tsecret-key");
        return false;
    }

    private boolean processArgument(String key, String value) {
        key = key.substring(2);

        if (Arrays.asList(this.allowedArgs).contains(key)) {
            if(key.equals("baseurl")) {
                System.out.println("Setting baseurl to " + value);
                this.baseUrl = value;
            } else if(key.equals("per-thread")) {
                this.productsPerThread = Integer.parseInt(value);
                System.out.println("Setting per-thread to " + value);
            } else if(key.equals("site-name")) {
                this.iSiteName = value;
                System.out.println("Setting site-name to " + value);
            } else if(key.equals("secret-key")) {
                this.secretKey = value;
                System.out.println("Setting secret-key to " + value);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }
}
