
package com.buka.amanah.model.customer_get;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseCustomerTotal {

    @SerializedName("data")
    private List<ResponseCustomerTotalData> mData;
    @SerializedName("response_message")
    private String mResponseMessage;
    @SerializedName("status")
    private String mStatus;

    public List<ResponseCustomerTotalData> getData() {
        return mData;
    }

    public void setData(List<ResponseCustomerTotalData> data) {
        mData = data;
    }

    public String getResponseMessage() {
        return mResponseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        mResponseMessage = responseMessage;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public static class ResponseCustomerTotalData {

        @SerializedName("total")
        private Long mTotal;

        public Long getTotal() {
            return mTotal;
        }

        public void setTotal(Long total) {
            mTotal = total;
        }

    }
}
