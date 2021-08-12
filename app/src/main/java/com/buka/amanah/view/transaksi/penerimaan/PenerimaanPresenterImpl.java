package com.buka.amanah.view.transaksi.penerimaan;

import android.content.Context;
import android.content.SharedPreferences;

import com.buka.amanah.BuildConfig;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;
import com.buka.amanah.utils.network.NetMater;
import com.buka.amanah.utils.network.NetUrl;
import com.buka.amanah.utils.network.ResponseCallback;

import org.json.JSONObject;

public class PenerimaanPresenterImpl implements PenerimaanPresenter {

    private String baseUrl;
    private NetMater netMater;
    private String token;
    private PenerimaanView view;

    public PenerimaanPresenterImpl(Context context, PenerimaanView view, SharedPreferences pref) {
        this.view = view;
        this.baseUrl = BuildConfig.baseUrl;
        token = pref.getString("token", "");
        this.netMater = new NetMater(context);
    }

    @Override
    public void getReceipt(int page) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("page", page);
            netMater.postData(token, (baseUrl + NetUrl.urlReceipt), jsonBody, ResponseReceipt.class, new ResponseCallback<ResponseReceipt>(){
                @Override
                public void onSuccess(ResponseReceipt response) {
                    view.onSuccessGetReceipt(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetReceipt(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingGetReceipt(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetReceipt(false);
            view.onErrorGetReceipt(e.getMessage(), 0);
        }

    }

    @Override
    public void getSummaryReceipt() {
        try {
            netMater.getData(token, (baseUrl + NetUrl.urlReceiptSummary), ResponseReceiptSummary.class, new ResponseCallback<ResponseReceiptSummary>() {
                @Override
                public void onSuccess(ResponseReceiptSummary response) {
                    view.onSuccessGetSummaryReceipt(response.getData());
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorGetSummaryReceipt(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingGetSummaryReceipt(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetSummaryReceipt(false);
            view.onErrorGetSummaryReceipt(e.getMessage(), 0);
        }
    }

    @Override
    public void delReceipt(String receiptId) {
        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("receiptId", Integer.parseInt(receiptId));
            netMater.postData(token, (baseUrl + NetUrl.urlDeleteReceipt), jsonBody, ResponseEmptyData.class, new ResponseCallback<ResponseEmptyData>(){
                @Override
                public void onSuccess(ResponseEmptyData response) {
                    view.onSuccessDelReceipt(response);
                }

                @Override
                public void onError(String error, Integer code) {
                    view.onErrorDelReceipt(error, code);
                }

                @Override
                public void onLoading(Boolean isLoading) {
                    view.onLoadingDelReceipt(isLoading);
                }
            });
        }catch (Exception e){
            view.onLoadingGetReceipt(false);
            view.onErrorGetReceipt(e.getMessage(), 0);
        }
    }
}
