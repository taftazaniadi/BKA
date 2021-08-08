package com.buka.amanah.transaksi;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.buka.amanah.R;
import com.buka.amanah.transaksi.penerimaan.PenerimaanActivity;
import com.buka.amanah.transaksi.pengeluaran.PengeluaranActivity;

public class TransaksiFragment extends Fragment {

    Button btnPenerimaan, btnPengeluaran;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View trx = inflater.inflate(R.layout.fragment_transaksi, container, false);

        btnPenerimaan = trx.findViewById(R.id.btnPenerimaan);
        btnPenerimaan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PenerimaanActivity.class);
                startActivity(intent);
            }
        });

        btnPengeluaran = trx.findViewById(R.id.btnPengeluaran);
        btnPengeluaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity().getApplicationContext(), PengeluaranActivity.class);
                startActivity(intent);
            }
        });

        // Inflate the layout for this fragment
        return trx;
    }
}