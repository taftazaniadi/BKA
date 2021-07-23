package com.buka.amanah.home;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buka.amanah.R;
import com.buka.amanah.hutang_piutang.HutangPiutangFragment;
import com.buka.amanah.pelanggan.PelangganFragment;
import com.buka.amanah.stock.StockFragment;
import com.buka.amanah.transaksi.TransaksiFragment;

public class HomeFragment extends Fragment {

    CardView trx_card, hutang_card, cust_card, stock_card, report_card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View home = inflater.inflate(R.layout.fragment_home, container, false);

        trx_card = home.findViewById(R.id.trx_card);
        hutang_card = home.findViewById(R.id.hutang_card);
        cust_card = home.findViewById(R.id.cust_card);
        stock_card = home.findViewById(R.id.stock_card);
        report_card = home.findViewById(R.id.report_card);

        trx_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(HomeFragment.this);
                transaction.replace(R.id.fl_container, new TransaksiFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        hutang_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(HomeFragment.this);
                transaction.replace(R.id.fl_container, new HutangPiutangFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        cust_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(HomeFragment.this);
                transaction.replace(R.id.fl_container, new PelangganFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        stock_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(HomeFragment.this);
                transaction.replace(R.id.fl_container, new StockFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        report_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.hide(HomeFragment.this);
                transaction.replace(R.id.fl_container, new HomeFragment());
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return home;
    }

}