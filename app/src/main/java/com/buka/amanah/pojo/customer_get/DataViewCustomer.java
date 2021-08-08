package com.buka.amanah.pojo.customer_get;

public class DataViewCustomer {
    private String address;

    private String updated_at;

    private String phone;

    private String district;

    private String distributor_id;

    private String name;

    private String created_at;

    private String wa;

    private String id;

    private String branch;

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getUpdated_at ()
    {
        return updated_at;
    }

    public void setUpdated_at (String updated_at)
    {
        this.updated_at = updated_at;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
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

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getCreated_at ()
    {
        return created_at;
    }

    public void setCreated_at (String created_at)
    {
        this.created_at = created_at;
    }

    public String getWa ()
    {
        return wa;
    }

    public void setWa (String wa)
    {
        this.wa = wa;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getBranch ()
    {
        return branch;
    }

    public void setBranch (String branch)
    {
        this.branch = branch;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [address = "+address+", updated_at = "+updated_at+", phone = "+phone+", district = "+district+", distributor_id = "+distributor_id+", name = "+name+", created_at = "+created_at+", wa = "+wa+", id = "+id+", branch = "+branch+"]";
    }
}
