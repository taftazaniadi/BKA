package com.buka.amanah.view.transaksi.penerimaan;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PenerimaanPresenter {
    void getReceipt(int page);
    void getSummaryReceipt();
    void delReceipt(String receiptId);
}
