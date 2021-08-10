package com.buka.amanah.model.receipt_get;

public class DataViewReceipt {
    private String amount;

    private String distributorId;

    private String type;

    private String branch;

    private String customerName;

    private String createdAt;

    private String total;

    private String createdAtRaw;

    private String price;

    private String district;

    private String customerId;

    private String typeId;

    private String id;

    private String updatedAt;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getDistributorId ()
    {
        return distributorId;
    }

    public void setDistributorId (String distributorId)
    {
        this.distributorId = distributorId;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getBranch ()
    {
        return branch;
    }

    public void setBranch (String branch)
    {
        this.branch = branch;
    }

    public String getCustomerName ()
    {
        return customerName;
    }

    public void setCustomerName (String customerName)
    {
        this.customerName = customerName;
    }

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getCreatedAtRaw ()
    {
        return createdAtRaw;
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

    public String getDistrict ()
    {
        return district;
    }

    public void setDistrict (String district)
    {
        this.district = district;
    }

    public String getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
    }

    public String getTypeId ()
    {
        return typeId;
    }

    public void setTypeId (String typeId)
    {
        this.typeId = typeId;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
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
        return "ClassPojo [amount = "+amount+", distributorId = "+distributorId+", type = "+type+", branch = "+branch+", customerName = "+customerName+", createdAt = "+createdAt+", total = "+total+", createdAtRaw = "+createdAtRaw+", price = "+price+", district = "+district+", customerId = "+customerId+", typeId = "+typeId+", id = "+id+", updatedAt = "+updatedAt+"]";
    }
}
