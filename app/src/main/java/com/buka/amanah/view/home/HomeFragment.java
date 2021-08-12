package com.buka.amanah.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buka.amanah.R;
import com.buka.amanah.adapter.BaseAdapterRecycler;
import com.buka.amanah.adapter.HolderAdvertisement;
import com.buka.amanah.model.DummyDataModel;
import com.buka.amanah.view.report.ReportActivity;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private HomePresenter presenter;
    private RecyclerView rvAdvertisement;
    private BaseAdapterRecycler<DummyDataModel> adapterAdvertisement;
    private CardView trx_card, hutang_card, cust_card, stock_card, report_card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
        rvAdvertisement = home.findViewById(R.id.rvAdvertisement);
        trx_card = home.findViewById(R.id.trx_card);
        hutang_card = home.findViewById(R.id.hutang_card);
        cust_card = home.findViewById(R.id.cust_card);
        stock_card = home.findViewById(R.id.stock_card);
        report_card = home.findViewById(R.id.report_card);
        return home;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new HomePresenterImpl(this);
        initEvent();
        setupAdvertisement();
        presenter.getDataAdvertisement();
        presenter.getDataTotTransaction();
    }

    private void setupAdvertisement() {
        adapterAdvertisement = new BaseAdapterRecycler<DummyDataModel>(){

            @Override
            protected int getLayoutId(int position, DummyDataModel obj) {
                return R.layout.item_home_advertisement;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderAdvertisement(view);
            }
        };
        rvAdvertisement.setAdapter(adapterAdvertisement);
    }

    private void initEvent() {
        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReportActivity.class));
            }
        });
    }

    @Override
    public void onSuccessAdvertisement(List<DummyDataModel> data) {
        adapterAdvertisement.clearItems();
        adapterAdvertisement.addAllItem(data);
    }

    @Override
    public void onSuccessTotTransaction() {

    }
}