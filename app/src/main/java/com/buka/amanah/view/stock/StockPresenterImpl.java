package com.buka.amanah.view.stock;

import android.content.Context;
import android.content.SharedPreferences;

import com.buka.amanah.BuildConfig;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.stok_get.ResponseStok;
import com.buka.amanah.utils.network.NetMater;
import com.buka.amanah.utils.network.NetUrl;
import com.buka.amanah.utils.network.ResponseCallback;
import com.buka.amanah.view.pelanggan.PelangganView;

import org.json.JSONObject;

public class StockPresenterImpl implements StockPresenter {

    private String baseUrl;
    private NetMater netMater;
    private String token;
    private StockView view;

    public StockPresenterImpl(Context context, StockView view, SharedPreferences pref) {
        this.view = view;
        this.baseUrl = BuildConfig.baseUrl;
        token = pref.getString("token", "");
        this.netMater = new NetMater(context);
    }

    @Override
    public void getStock(int page) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("page", page);
            netMater.postData(token, (baseUrl + NetUrl.urlStock), jsonBody, ResponseStok.class, new ResponseCallback<ResponseStok>(){
                @Override
                public void onSuccess(ResponseStok response) {
                    view.onSuccessGetStock(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetStock(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingGetStock(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetStock(false);
            view.onErrorGetStock(e.getMessage(), 0);
        }
    }

    @Override
    public void delStock(String stockId) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("stockId", Integer.parseInt(stockId));
            netMater.postData(token, (baseUrl + NetUrl.urlDeleteStock), jsonBody, ResponseEmptyData.class, new ResponseCallback<ResponseEmptyData>(){
                @Override
                public void onSuccess(ResponseEmptyData response) {
                    view.onSuccessDelStock(response);
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorDelStock(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingDelStock(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetStock(false);
            view.onErrorGetStock(e.getMessage(), 0);
        }
    }
}
