package com.buka.amanah.adapter;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.customer_get.ResponseCustomer;
import com.buka.amanah.model.stok_get.ResponseStok;

public class HolderStock extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseStok.ResponseStokItem> {
    private AppCompatTextView tvNo, tvName, tvCount, tvAddress, tvPrice;
    private ImageButton btnSee, btnEdit, btnDel;
    private ActionCallback<ResponseStok.ResponseStokItem> callback;
    public HolderStock(@NonNull View itemView, ActionCallback<ResponseStok.ResponseStokItem> callback) {
        super(itemView);
        tvNo = itemView.findViewById(R.id.tvNo);
        tvName = itemView.findViewById(R.id.tvName);
        tvCount = itemView.findViewById(R.id.tvCount);
        tvPrice= itemView.findViewById(R.id.tvPrice);

        btnSee = itemView.findViewById(R.id.btnSee);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDel = itemView.findViewById(R.id.btnDel);
        this.callback = callback;
    }

    @Override
    public void bind(ResponseStok.ResponseStokItem data, int pos) {
        tvNo.setText(String.format("%03d", (pos+1)));
        tvName.setText(data.getProductName());
        tvCount.setText(data.getStock().toString());
        tvPrice.setText("Rp. " + data.getPrice());

        btnSee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onSee(data);
            }
        });
        btnDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onDel(data);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.onEdit(data);
            }
        });
    }
}
