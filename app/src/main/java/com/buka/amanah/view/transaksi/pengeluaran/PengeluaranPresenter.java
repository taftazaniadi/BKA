package com.buka.amanah.view.transaksi.pengeluaran;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PengeluaranPresenter {
    void getDisbursements(int page);
    void delDisbursements(String receiptId);
}
