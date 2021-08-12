package com.buka.amanah.view.pelanggan;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public interface PelangganPresenter {
    void getCustomer(int page);
    void getTotalCustomer();
    void delCustomer(String receiptId);
}
