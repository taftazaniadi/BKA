package com.buka.amanah.view.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buka.amanah.R;
import com.buka.amanah.view.report.ReportActivity;

public class HomeFragment extends Fragment {

    private CardView trx_card, hutang_card, cust_card, stock_card, report_card;
    private HomePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);
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
        presenter = new HomePresenterImpl();
        initEvent();
    }

    private void initEvent() {
        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ReportActivity.class));
            }
        });
    }
}