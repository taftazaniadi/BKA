package com.buka.amanah.view.pelanggan;

import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.customer_get.ResponseCustomer;
import com.buka.amanah.model.customer_get.ResponseCustomerTotal;

import java.util.List;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PelangganView {
    void onSuccessGetCustomer(List<ResponseCustomer.ResponseCustomerItem> mData);
    void onErrorGetCustomer(String msg, int code);
    void onLoadingGetCustomer(boolean isLoading);

    void onSuccessGetTotalCustomer(List<ResponseCustomerTotal.ResponseCustomerTotalData> mData);
    void onErrorGetTotalCustomer(String msg, int code);
    void onLoadingGetTotalCustomer(boolean isLoading);

    void onSuccessDelCustomer(ResponseEmptyData mData);
    void onErrorDelCustomer(String msg, int code);
    void onLoadingDelCustomer(boolean isLoading);
}
