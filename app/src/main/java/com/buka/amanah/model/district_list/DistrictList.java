package com.buka.amanah.model.district_list;

public class DistrictList {
    private String[] response_message;

    private String[] district;

    private String status;

    public String[] getResponse_message ()
    {
        return response_message;
    }

    public void setResponse_message (String[] response_message)
    {
        this.response_message = response_message;
    }

    public String[] getDistrict ()
    {
        return district;
    }

    public void setDistrict (String[] district)
    {
        this.district = district;
    }

    public String getStatus ()
    {
        return status;
    }

    public void setStatus (String status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [response_message = "+response_message+", district = "+district+", status = "+status+"]";
    }
}
