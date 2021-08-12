package com.buka.amanah.view.transaksi.penerimaan;

import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;

import java.util.List;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PenerimaanView {
    void onSuccessGetReceipt(List<ResponseReceipt.ResponseReceiptItem> mData);
    void onErrorGetReceipt(String msg, int code);
    void onLoadingGetReceipt(boolean isLoading);

    void onSuccessGetSummaryReceipt(List<ResponseReceiptSummary.ResponseReceiptSummaryItem> mData);
    void onErrorGetSummaryReceipt(String msg, int code);
    void onLoadingGetSummaryReceipt(boolean isLoading);

    void onSuccessDelReceipt(ResponseEmptyData mData);
    void onErrorDelReceipt(String msg, int code);
    void onLoadingDelReceipt(boolean isLoading);
}
