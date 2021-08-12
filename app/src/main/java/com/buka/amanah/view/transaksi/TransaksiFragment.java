package com.buka.amanah.view.transaksi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.buka.amanah.R;
import com.buka.amanah.view.transaksi.penerimaan.PenerimaanActivity;
import com.buka.amanah.view.transaksi.penerimaan.PenerimaanActivityOwl;
import com.buka.amanah.view.transaksi.pengeluaran.PengeluaranActivity;
import com.buka.amanah.view.transaksi.pengeluaran.PengeluaranActivityOwl;

public class TransaksiFragment extends Fragment {

    Button btnPenerimaan, btnPengeluaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View trx = inflater.inflate(R.layout.fragment_transaksi, container, false);

        btnPenerimaan = trx.findViewById(R.id.btnPenerimaan);
        btnPenerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PenerimaanActivityOwl.class);
                startActivity(intent);
            }
        });

        btnPengeluaran = trx.findViewById(R.id.btnPengeluaran);
        btnPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PengeluaranActivityOwl.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return trx;
    }
}