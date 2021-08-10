package com.buka.amanah.model.detail_biaya;

public class DetailBiaya {
    private String response_message;

    private DataDetail[] data;

    private String status;

    public String getResponse_message ()
    {
        return response_message;
    }

    public void setResponse_message (String response_message)
    {
        this.response_message = response_message;
    }

    public DataDetail[] getData ()
    {
        return data;
    }

    public void setData (DataDetail[] data)
    {
        this.data = data;
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
        return "ClassPojo [response_message = "+response_message+", data = "+data+", status = "+status+"]";
    }
}
