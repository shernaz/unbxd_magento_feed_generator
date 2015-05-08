package com.unbxd.Entity;

import java.io.IOException;

/**
 * Created by albin on 5/4/15.
 */
public class ProductEntity extends AbstractEntity
{

    public ProductEntity(String iSiteName) throws IOException {
        super(iSiteName);
        this.setUniqueField("uniqueId");
        this.setCollection("products_");
    }

    public void setIndex() {
        this.dao.getCollection().ensureIndex("uniqueId");
    }


}
