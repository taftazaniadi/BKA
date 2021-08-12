package com.buka.amanah.view.home;

import com.buka.amanah.model.DummyDataModel;

import java.util.ArrayList;
import java.util.List;

public class HomePresenterImpl implements HomePresenter {

    private HomeView view;
    public HomePresenterImpl(HomeView view){
        this.view = view;
    }

    @Override
    public void getDataAdvertisement() {
        List<DummyDataModel> imgItem = new ArrayList<>();
        DummyDataModel data1 = new DummyDataModel();
        data1.setId(1);
        data1.setLabel("https://assets.materialup.com/uploads/dcc07ea4-845a-463b-b5f0-4696574da5ed/preview.jpg");
        imgItem.add(data1);
        DummyDataModel data2 = new DummyDataModel();
        data2.setId(1);
        data2.setLabel("https://assets.materialup.com/uploads/20ded50d-cc85-4e72-9ce3-452671cf7a6d/preview.jpg");
        imgItem.add(data2);
        view.onSuccessAdvertisement(imgItem);
    }

    @Override
    public void getDataTotTransaction() {

    }
}
