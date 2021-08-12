
package com.buka.amanah.model.receipt_summary;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseReceiptSummary {

    @Expose
    private List<ResponseReceiptSummaryItem> data;
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    private String status;

    public List<ResponseReceiptSummaryItem> getData() {
        return data;
    }

    public void setData(List<ResponseReceiptSummaryItem> data) {
        this.data = data;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResponseReceiptSummaryItem {

        @SerializedName("count")
        private String mCount;
        @SerializedName("name")
        private String mName;
        @SerializedName("total")
        private String mTotal;

        public String getCount() {
            return mCount;
        }

        public void setCount(String count) {
            mCount = count;
        }

        public String getName() {
            return mName;
        }

        public void setName(String name) {
            mName = name;
        }

        public String getTotal() {
            return mTotal;
        }

        public void setTotal(String total) {
            mTotal = total;
        }

    }
}
