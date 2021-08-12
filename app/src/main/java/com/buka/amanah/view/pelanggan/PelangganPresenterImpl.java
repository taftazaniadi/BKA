package com.buka.amanah.view.pelanggan;

import android.content.Context;
import android.content.SharedPreferences;

import com.buka.amanah.BuildConfig;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.customer_get.ResponseCustomer;
import com.buka.amanah.model.customer_get.ResponseCustomerTotal;
import com.buka.amanah.utils.network.NetMater;
import com.buka.amanah.utils.network.NetUrl;
import com.buka.amanah.utils.network.ResponseCallback;

import org.json.JSONObject;

public class PelangganPresenterImpl implements PelangganPresenter {

    private String baseUrl;
    private NetMater netMater;
    private String token;
    private PelangganView view;

    public PelangganPresenterImpl(Context context, PelangganView view, SharedPreferences pref) {
        this.view = view;
        this.baseUrl = BuildConfig.baseUrl;
        token = pref.getString("token", "");
        this.netMater = new NetMater(context);
    }
    
    @Override
    public void getCustomer(int page) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("page", page);
            netMater.postData(token, (baseUrl + NetUrl.urlCustomer), jsonBody, ResponseCustomer.class, new ResponseCallback<ResponseCustomer>(){
                @Override
                public void onSuccess(ResponseCustomer response) {
                    view.onSuccessGetCustomer(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetCustomer(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingGetCustomer(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetCustomer(false);
            view.onErrorGetCustomer(e.getMessage(), 0);
        }
    }

    @Override
    public void getTotalCustomer() {
        try {
            netMater.getData(token, (baseUrl + NetUrl.urlCustomerTotal), ResponseCustomerTotal.class, new ResponseCallback<ResponseCustomerTotal>() {
                @Override
                public void onSuccess(ResponseCustomerTotal response) {
                    view.onSuccessGetTotalCustomer(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetTotalCustomer(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                        view.onLoadingGetTotalCustomer(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetTotalCustomer(false);
            view.onErrorGetTotalCustomer(e.getMessage(), 0);
        }
    }

    @Override
    public void delCustomer(String CustomerId) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("custId", Integer.parseInt(CustomerId));
            netMater.postData(token, (baseUrl + NetUrl.urlDeleteCustomer), jsonBody, ResponseEmptyData.class, new ResponseCallback<ResponseEmptyData>(){
                @Override
                public void onSuccess(ResponseEmptyData response) {
                    view.onSuccessDelCustomer(response);
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorDelCustomer(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingDelCustomer(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetCustomer(false);
            view.onErrorGetCustomer(e.getMessage(), 0);
        }
    }
}
