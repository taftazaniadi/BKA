package com.buka.amanah.view.transaksi.pengeluaran;

import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.disbursement_get.ResponseDisbursement;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;

import java.util.List;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PengeluaranView {
    void onSuccessGetDisbursements(List<ResponseDisbursement.ResponseDisbursementItem> mData);
    void onErrorGetDisbursements(String msg, int code);
    void onLoadingGetDisbursements(boolean isLoading);

    void onSuccessDelDisbursements(ResponseEmptyData mData);
    void onErrorDelDisbursements(String msg, int code);
    void onLoadingDelDisbursements(boolean isLoading);
}
