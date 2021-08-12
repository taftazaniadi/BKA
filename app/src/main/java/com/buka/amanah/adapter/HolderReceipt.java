package com.buka.amanah.adapter;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.receipt_get.ResponseReceipt;

public class HolderReceipt extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseReceipt.ResponseReceiptItem> {
    private AppCompatTextView tvNo, tvDate, tvCustomer, tvReceive, tvCount, tvTotPrice, tvTotRp;
    private ImageButton btnSee, btnEdit, btnDel;
    private ActionCallback<ResponseReceipt.ResponseReceiptItem> callback;
    public HolderReceipt(@NonNull View itemView, ActionCallback<ResponseReceipt.ResponseReceiptItem> callback) {
        super(itemView);
        tvNo = itemView.findViewById(R.id.tvNo);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvCustomer = itemView.findViewById(R.id.tvCustomer);
        tvReceive = itemView.findViewById(R.id.tvReceive);
        tvCount = itemView.findViewById(R.id.tvCount);
        tvTotPrice = itemView.findViewById(R.id.tvTotPrice);
        tvTotRp = itemView.findViewById(R.id.tvTotRp);
        btnSee = itemView.findViewById(R.id.btnSee);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDel = itemView.findViewById(R.id.btnDel);
        this.callback = callback;
    }

    @Override
    public void bind(ResponseReceipt.ResponseReceiptItem data, int pos) {
        tvNo.setText(String.format("%03d", (pos+1)));
        tvDate.setText(data.getCreatedAt());
        tvCustomer.setText(data.getCustomerName());
        tvReceive.setText(data.getType());
        tvCount.setText(data.getAmount());
        tvTotPrice.setText("Rp. " + data.getPrice());
        tvTotRp.setText("Rp. " + data.getTotal());
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
