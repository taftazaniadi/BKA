
package com.buka.amanah.model.disbursement_get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResponseDisbursement {

    @Expose
    private List<ResponseDisbursementItem> data;
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    private String status;

    public List<ResponseDisbursementItem> getData() {
        return data;
    }

    public void setData(List<ResponseDisbursementItem> data) {
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

    public static class ResponseDisbursementItem {

        @Expose
        private String branch;
        @Expose
        private String category;
        @Expose
        private String categoryId;
        @Expose
        private String createdAt;
        @Expose
        private String createdAtRaw;
        @Expose
        private String detail;
        @SerializedName("distributor_id")
        private Long distributorId;
        @Expose
        private String district;
        @Expose
        private Long id;
        @Expose
        private String total;
        @Expose
        private String type;
        @Expose
        private String typeId;
        @Expose
        private String updatedAt;

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getCreatedAtRaw() {
            return createdAtRaw;
        }

        public void setCreatedAtRaw(String createdAtRaw) {
            this.createdAtRaw = createdAtRaw;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public Long getDistributorId() {
            return distributorId;
        }

        public void setDistributorId(Long distributorId) {
            this.distributorId = distributorId;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
