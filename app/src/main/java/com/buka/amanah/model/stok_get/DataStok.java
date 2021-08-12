package com.buka.amanah.model.stok_get;

public class DataStok {
    private String createdAt;

    private String createdAtRaw;

    private String price;

    private String distributorId;

    private String id;

    private String stock;

    private String productId;

    private String productName;

    private String updatedAt;

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getCreatedAtRaw ()
    {
        return createdAtRaw;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setCreatedAtRaw (String createdAtRaw)
    {
        this.createdAtRaw = createdAtRaw;
    }

    public String getPrice ()
    {
        return price;
    }

    public void setPrice (String price)
    {
        this.price = price;
    }

    public String getDistributorId ()
    {
        return distributorId;
    }

    public void setDistributorId (String distributorId)
    {
        this.distributorId = distributorId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getStock ()
    {
        return stock;
    }

    public void setStock (String stock)
    {
        this.stock = stock;
    }

    public String getProductName ()
    {
        return productName;
    }

    public void setProductName (String productName)
    {
        this.productName = productName;
    }

    public String getUpdatedAt ()
    {
        return updatedAt;
    }

    public void setUpdatedAt (String updatedAt)
    {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [createdAt = "+createdAt+", createdAtRaw = "+createdAtRaw+", price = "+price+", distributorId = "+distributorId+", id = "+id+", stock = "+stock+", productId = "+productId+", productName = "+productName+", updatedAt = "+updatedAt+"]";
    }
}
