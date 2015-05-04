package com.unbxd;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by albin on 4/30/15.
 */

public class UnbxdProperties {
    private static UnbxdProperties property = null;
    private Properties prop = null;
    protected UnbxdProperties() throws IOException {
        // So that this class cannot be instantiated directly outside of the class
        this.prop = new Properties();
        String propFileName = "config.properties";
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
        if (inputStream != null) {
            this.prop.load(inputStream);
        } else {
            throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
        }
    }

    public static UnbxdProperties getInstance() throws IOException {
        if(property == null) {
            property = new UnbxdProperties();
        }
        return property;
    }
    public String getProperty(String propertyName) throws IOException {
        return this.prop.getProperty(propertyName).replaceAll("^\"|\"$", "");
    }

}
