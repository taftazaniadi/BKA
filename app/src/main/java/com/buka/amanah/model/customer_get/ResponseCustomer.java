
package com.buka.amanah.model.customer_get;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ResponseCustomer {

    @Expose
    private List<ResponseCustomerItem> data;
    @SerializedName("response_message")
    private String responseMessage;
    @Expose
    private String status;

    public List<ResponseCustomerItem> getData() {
        return data;
    }

    public void setData(List<ResponseCustomerItem> data) {
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


    public static class ResponseCustomerItem {

        @Expose
        private String address;
        @Expose
        private String branch;
        @Expose
        private String createdAt;
        @Expose
        private String createdAtRaw;
        @Expose
        private Long distributorId;
        @Expose
        private String district;
        @Expose
        private Long id;
        @Expose
        private String name;
        @Expose
        private String phone;
        @Expose
        private String updatedAt;
        @Expose
        private String wa;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
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

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public String getWa() {
            return wa;
        }

        public void setWa(String wa) {
            this.wa = wa;
        }

    }
}
