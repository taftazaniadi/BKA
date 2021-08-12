package com.buka.amanah.view.home;

import com.buka.amanah.model.DummyDataModel;

import java.util.List;

public interface HomeView {
    void onSuccessAdvertisement(List<DummyDataModel> data);
    void onSuccessTotTransaction();
}
