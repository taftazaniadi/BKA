package com.buka.amanah.pojo.receipt_get;

public class DataViewReceipt {
    private String amount;

    private String total;

    private String updated_at;

    private String price;

    private String district;

    private String distributor_id;

    private String created_at;

    private String id;

    private String customer_id;

    private String customer_name;

    private String type;

    private String branch;

    private String created_at_raw;

    public String getAmount ()
    {
        return amount;
    }

    public void setAmount (String amount)
    {
        this.amount = amount;
    }

    public String getTotal ()
    {
        return total;
    }

    public void setTotal (String total)
    {
        this.total = total;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
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

    public String getDistributor_id ()
    {
        return distributor_id;
    }

    public void setDistributor_id (String distributor_id)
    {
        this.distributor_id = distributor_id;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCustomer_id ()
    {
        return customer_id;
    }

    public void setCustomer_id (String customer_id)
    {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
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

    public String getCreated_at_raw ()
    {
        return created_at_raw;
    }

    public void setCreated_at_raw (String created_at_raw)
    {
        this.created_at_raw = created_at_raw;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [amount = "+amount+", total = "+total+", updated_at = "+updated_at+", price = "+price+", district = "+district+", distributor_id = "+distributor_id+", created_at = "+created_at+", id = "+id+", customer_id = "+customer_id+", type = "+type+", branch = "+branch+", created_at_raw = "+created_at_raw+"]";
    }
}
