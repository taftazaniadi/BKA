package com.buka.amanah.model.hutang_usaha;

public class DataHutangUsaha {
    private String debtTypeOptName;

    private String debtCategory;

    private String customerName;

    private String debtTypeOpt;

    private String createdAt;

    private String createdAtRaw;

    private String debtLeft;

    private String customerId;

    private String id;

    private String detail;

    private String debtType;

    private String debtCategoryName;

    private String debtTypeName;

    private String debtAmount;

    private String status;

    private String updatedAt;

    public String getDebtTypeOptName ()
    {
        return debtTypeOptName;
    }

    public void setDebtTypeOptName (String debtTypeOptName)
    {
        this.debtTypeOptName = debtTypeOptName;
    }

    public String getDebtCategory ()
    {
        return debtCategory;
    }

    public void setDebtCategory (String debtCategory)
    {
        this.debtCategory = debtCategory;
    }

    public String getCustomerName ()
    {
        return customerName;
    }

    public void setCustomerName (String customerName)
    {
        this.customerName = customerName;
    }

    public String getDebtTypeOpt ()
    {
        return debtTypeOpt;
    }

    public void setDebtTypeOpt (String debtTypeOpt)
    {
        this.debtTypeOpt = debtTypeOpt;
    }

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

    public void setCreatedAtRaw (String createdAtRaw)
    {
        this.createdAtRaw = createdAtRaw;
    }

    public String getDebtLeft ()
    {
        return debtLeft;
    }

    public void setDebtLeft (String debtLeft)
    {
        this.debtLeft = debtLeft;
    }

    public String getCustomerId ()
    {
        return customerId;
    }

    public void setCustomerId (String customerId)
    {
        this.customerId = customerId;
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

    public String getDebtType ()
    {
        return debtType;
    }

    public void setDebtType (String debtType)
    {
        this.debtType = debtType;
    }

    public String getDebtCategoryName ()
    {
        return debtCategoryName;
    }

    public void setDebtCategoryName (String debtCategoryName)
    {
        this.debtCategoryName = debtCategoryName;
    }

    public String getDebtTypeName ()
    {
        return debtTypeName;
    }

    public void setDebtTypeName (String debtTypeName)
    {
        this.debtTypeName = debtTypeName;
    }

    public String getDebtAmount ()
    {
        return debtAmount;
    }

    public void setDebtAmount (String debtAmount)
    {
        this.debtAmount = debtAmount;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
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
        return "ClassPojo [debtTypeOptName = "+debtTypeOptName+", debtCategory = "+debtCategory+", customerName = "+customerName+", debtTypeOpt = "+debtTypeOpt+", createdAt = "+createdAt+", createdAtRaw = "+createdAtRaw+", debtLeft = "+debtLeft+", customerId = "+customerId+", id = "+id+", detail = "+detail+", debtType = "+debtType+", debtCategoryName = "+debtCategoryName+", debtTypeName = "+debtTypeName+", debtAmount = "+debtAmount+", status = "+status+", updatedAt = "+updatedAt+"]";
    }
}
