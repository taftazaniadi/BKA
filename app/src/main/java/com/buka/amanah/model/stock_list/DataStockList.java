package com.buka.amanah.model.stock_list;

public class DataStockList {
    private String id;

    private String productName;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", productName = "+productName+"]";
    }
}
