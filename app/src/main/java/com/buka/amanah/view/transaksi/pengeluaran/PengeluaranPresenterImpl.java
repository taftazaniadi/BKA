package com.buka.amanah.view.transaksi.pengeluaran;

import android.content.Context;
import android.content.SharedPreferences;

import com.buka.amanah.BuildConfig;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.disbursement_get.ResponseDisbursement;
import com.buka.amanah.utils.network.NetMater;
import com.buka.amanah.utils.network.NetUrl;
import com.buka.amanah.utils.network.ResponseCallback;

import org.json.JSONObject;

public class PengeluaranPresenterImpl implements PengeluaranPresenter {

    private String baseUrl;
    private NetMater netMater;
    private String token;
    private PengeluaranView view;

    public PengeluaranPresenterImpl(Context context, PengeluaranView view, SharedPreferences pref) {
        this.view = view;
        this.baseUrl = BuildConfig.baseUrl;
        token = pref.getString("token", "");
        this.netMater = new NetMater(context);
    }

    @Override
    public void getDisbursements(int page) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("page", page);
            netMater.postData(token, (baseUrl + NetUrl.urlDisbursements), jsonBody, ResponseDisbursement.class, new ResponseCallback<ResponseDisbursement>(){
                @Override
                public void onSuccess(ResponseDisbursement response) {
                    view.onSuccessGetDisbursements(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetDisbursements(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingGetDisbursements(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetDisbursements(false);
            view.onErrorGetDisbursements(e.getMessage(), 0);
        }
    }

    @Override
    public void delDisbursements(String disbursementId) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("disbursementId", Integer.parseInt(disbursementId));
            netMater.postData(token, (baseUrl + NetUrl.urlDeleteDisbursements), jsonBody, ResponseEmptyData.class, new ResponseCallback<ResponseEmptyData>(){
                @Override
                public void onSuccess(ResponseEmptyData response) {
                    view.onSuccessDelDisbursements(response);
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorDelDisbursements(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingDelDisbursements(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingDelDisbursements(false);
            view.onErrorDelDisbursements(e.getMessage(), 0);
        }
    }
}
