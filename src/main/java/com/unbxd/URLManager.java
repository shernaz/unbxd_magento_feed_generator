package com.unbxd;

/**
 * Created by albin on 5/4/15.
 */
public class URLManager
{
//    http://bookpal03.gemshelp.com/index.php/searchcore/catalog/size?site=Main%20Website&auth=653d0e345a01c543bf75a8897b6ff16d
    private String url;
    public URLManager(String sizeUrl) {
        this.url = sizeUrl;
    }


    public String getProductsUrl() {
        String prodcutsUrl = "";
        prodcutsUrl = this.url.replaceFirst("/catalog/size", "/catalog/products");
        return prodcutsUrl;
    }

    public static String getFeedUploadAPI(String iSiteName, String secretKey) {
        return "http://feed.unbxdapi.com/upload/v2/" + secretKey + "/" + iSiteName + "?fullimport=true";
    }

    public static String getFeedUploadAPI(String iSiteName, String secretKey, boolean fullImport) {
        return "http://feed.unbxdapi.com/upload/v2/" + secretKey + "/" + iSiteName + (fullImport ? "?fullimport=true" : "");
    }


}
