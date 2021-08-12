
package com.buka.amanah.model.stok_get;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseStok {

    @SerializedName("data")
    private List<ResponseStokItem> mData;
    @SerializedName("response_message")
    private String mResponseMessage;
    @SerializedName("status")
    private String mStatus;

    public List<ResponseStokItem> getData() {
        return mData;
    }

    public void setData(List<ResponseStokItem> data) {
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

    public static class ResponseStokItem {

        @SerializedName("createdAt")
        private String mCreatedAt;
        @SerializedName("createdAtRaw")
        private String mCreatedAtRaw;
        @SerializedName("distributorId")
        private Long mDistributorId;
        @SerializedName("id")
        private Long mId;
        @SerializedName("price")
        private String mPrice;
        @SerializedName("productName")
        private String mProductName;
        @SerializedName("stock")
        private Long mStock;
        @SerializedName("updatedAt")
        private String mUpdatedAt;

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

        public Long getDistributorId() {
            return mDistributorId;
        }

        public void setDistributorId(Long distributorId) {
            mDistributorId = distributorId;
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

        public String getProductName() {
            return mProductName;
        }

        public void setProductName(String productName) {
            mProductName = productName;
        }

        public Long getStock() {
            return mStock;
        }

        public void setStock(Long stock) {
            mStock = stock;
        }

        public String getUpdatedAt() {
            return mUpdatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            mUpdatedAt = updatedAt;
        }

    }
}
