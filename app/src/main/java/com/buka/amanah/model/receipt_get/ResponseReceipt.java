
package com.buka.amanah.model.receipt_get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseReceipt {

    @Expose
    private List<ResponseReceiptItem> data;
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    private String status;

    public List<ResponseReceiptItem> getData() {
        return data;
    }

    public void setData(List<ResponseReceiptItem> data) {
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


    public static class ResponseReceiptItem {

        @SerializedName("amount")
        private String mAmount;
        @SerializedName("branch")
        private String mBranch;
        @SerializedName("createdAt")
        private String mCreatedAt;
        @SerializedName("createdAtRaw")
        private String mCreatedAtRaw;
        @SerializedName("customerId")
        private String mCustomerId;
        @SerializedName("customerName")
        private String mCustomerName;
        @SerializedName("distributorId")
        private Long mDistributorId;
        @SerializedName("district")
        private String mDistrict;
        @SerializedName("id")
        private Long mId;
        @SerializedName("price")
        private String mPrice;
        @SerializedName("total")
        private String mTotal;
        @SerializedName("type")
        private String mType;
        @SerializedName("typeId")
        private String mTypeId;
        @SerializedName("updatedAt")
        private String mUpdatedAt;

        public String getAmount() {
            return mAmount;
        }

        public void setAmount(String amount) {
            mAmount = amount;
        }

        public String getBranch() {
            return mBranch;
        }

        public void setBranch(String branch) {
            mBranch = branch;
        }

        public String getCreatedAt() {
            return mCreatedAt;
        }

        public void setCreatedAt(String createdAt) {
            mCreatedAt = createdAt;
        }

        public String getCreatedAtRaw() {
            return mCreatedAtRaw;
        }

        public void setCreatedAtRaw(String createdAtRaw) {
            mCreatedAtRaw = createdAtRaw;
        }

        public String getCustomerId() {
            return mCustomerId;
        }

        public void setCustomerId(String customerId) {
            mCustomerId = customerId;
        }

        public String getCustomerName() {
            return mCustomerName;
        }

        public void setCustomerName(String customerName) {
            mCustomerName = customerName;
        }

        public Long getDistributorId() {
            return mDistributorId;
        }

        public void setDistributorId(Long distributorId) {
            mDistributorId = distributorId;
        }

        public String getDistrict() {
            return mDistrict;
        }

        public void setDistrict(String district) {
            mDistrict = district;
        }

        public Long getId() {
            return mId;
        }

        public void setId(Long id) {
            mId = id;
        }

        public String getPrice() {
            return mPrice;
        }

        public void setPrice(String price) {
            mPrice = price;
        }

        public String getTotal() {
            return mTotal;
        }

        public void setTotal(String total) {
            mTotal = total;
        }

        public String getType() {
            return mType;
        }

        public void setType(String type) {
            mType = type;
        }

        public String getTypeId() {
            return mTypeId;
        }

        public void setTypeId(String typeId) {
            mTypeId = typeId;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

    }
}
