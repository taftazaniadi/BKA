package com.buka.amanah.pojo.disbursement_get;

public class DataViewDisbursement {
    private String type;

    private String branch;

    private String createdAt;

    private String total;

    private String createdAtRaw;

    private String district;

    private String distributor_id;

    private String typeId;

    private String id;

    private String detail;

    private String category;

    private String categoryId;

    private String updatedAt;

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

    public String getDetail ()
    {
        return detail;
    }

    public void setDetail (String detail)
    {
        this.detail = detail;
    }

    public String getCategory ()
    {
        return category;
    }

    public void setCategory (String category)
    {
        this.category = category;
    }

    public String getCategoryId ()
    {
        return categoryId;
    }

    public void setCategoryId (String categoryId)
    {
        this.categoryId = categoryId;
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
        return "ClassPojo [type = "+type+", branch = "+branch+", createdAt = "+createdAt+", total = "+total+", createdAtRaw = "+createdAtRaw+", district = "+district+", distributor_id = "+distributor_id+", typeId = "+typeId+", id = "+id+", detail = "+detail+", category = "+category+", categoryId = "+categoryId+", updatedAt = "+updatedAt+"]";
    }
}
