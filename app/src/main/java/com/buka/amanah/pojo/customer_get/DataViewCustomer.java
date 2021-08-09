package com.buka.amanah.pojo.customer_get;

public class DataViewCustomer {
    private String createdAt;

    private String address;

    private String createdAtRaw;

    private String phone;

    private String distributorId;

    private String district;

    private String name;

    private String wa;

    private String id;

    private String branch;

    private String updatedAt;

    public String getCreatedAt ()
    {
        return createdAt;
    }

    public void setCreatedAt (String createdAt)
    {
        this.createdAt = createdAt;
    }

    public String getAddress ()
    {
        return address;
    }

    public void setAddress (String address)
    {
        this.address = address;
    }

    public String getCreatedAtRaw ()
    {
        return createdAtRaw;
    }

    public void setCreatedAtRaw (String createdAtRaw)
    {
        this.createdAtRaw = createdAtRaw;
    }

    public String getPhone ()
    {
        return phone;
    }

    public void setPhone (String phone)
    {
        this.phone = phone;
    }

    public String getDistributorId ()
    {
        return distributorId;
    }

    public void setDistributorId (String distributorId)
    {
        this.distributorId = distributorId;
    }

    public String getDistrict ()
    {
        return district;
    }

    public void setDistrict (String district)
    {
        this.district = district;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
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
        return "ClassPojo [createdAt = "+createdAt+", address = "+address+", createdAtRaw = "+createdAtRaw+", phone = "+phone+", distributorId = "+distributorId+", district = "+district+", name = "+name+", wa = "+wa+", id = "+id+", branch = "+branch+", updatedAt = "+updatedAt+"]";
    }
}
