package com.buka.amanah.view.hutang_piutang;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.buka.amanah.R;
import com.buka.amanah.view.hutang_piutang.hutang.galon.HutangGalonActivity;
import com.buka.amanah.view.hutang_piutang.hutang.usaha.HutangUsahaActivity;
import com.buka.amanah.view.hutang_piutang.piutang.galon.PiutangGalonActivity;
import com.buka.amanah.view.hutang_piutang.piutang.usaha.PiutangUsahaActivity;

public class HutangPiutangFragment extends Fragment {

    CardView hutang_usaha_card, hutang_galon_card, piutang_usaha_card, piutang_galon_card;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_hutang_piutang, container, false);

        hutang_usaha_card = view.findViewById(R.id.hutang_usaha_card);
        hutang_galon_card = view.findViewById(R.id.hutang_galon_card);
        piutang_usaha_card = view.findViewById(R.id.piutang_usaha_card);
        piutang_galon_card = view.findViewById(R.id.piutang_galon_card);

        hutang_usaha_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HutangUsahaActivity.class));
            }
        });

        hutang_galon_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), HutangGalonActivity.class));
            }
        });

        piutang_usaha_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PiutangUsahaActivity.class));
            }
        });

        piutang_galon_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), PiutangGalonActivity.class));
            }
        });

        return view;
    }
}