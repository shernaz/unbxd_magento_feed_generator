package com.unbxd.Entity;

import java.io.IOException;

/**
 * Created by albin on 5/3/15.
 */
public class SchemaEntity extends AbstractEntity
{
    public SchemaEntity(String iSiteName) throws IOException {
        super(iSiteName);
        this.setUniqueField("fieldName");
        this.setCollection("schema_");
    }
}