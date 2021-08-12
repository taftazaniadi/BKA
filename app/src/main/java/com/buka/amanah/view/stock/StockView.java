package com.buka.amanah.view.stock;

import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.stok_get.ResponseStok;

import java.util.List;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface StockView {
    void onSuccessGetStock(List<ResponseStok.ResponseStokItem> mData);
    void onErrorGetStock(String msg, int code);
    void onLoadingGetStock(boolean isLoading);
    
    void onSuccessDelStock(ResponseEmptyData mData);
    void onErrorDelStock(String msg, int code);
    void onLoadingDelStock(boolean isLoading);
}
