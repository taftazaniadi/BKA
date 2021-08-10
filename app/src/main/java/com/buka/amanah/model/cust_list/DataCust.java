package com.buka.amanah.model.cust_list;

public class DataCust {
    private String id;

    private String customerName;

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getCustomerName ()
    {
        return customerName;
    }

    public void setCustomerName (String customerName)
    {
        this.customerName = customerName;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [id = "+id+", customerName = "+customerName+"]";
    }
}
