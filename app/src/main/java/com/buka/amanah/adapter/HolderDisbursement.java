package com.buka.amanah.adapter;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.disbursement_get.ResponseDisbursement;
import com.buka.amanah.model.receipt_get.ResponseReceipt;

public class HolderDisbursement extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseDisbursement.ResponseDisbursementItem> {
    private AppCompatTextView tvNo, tvDate, tvPriceType, tvDetailPrice, tvTotRp;
    private ImageButton btnSee, btnEdit, btnDel;
    private ActionCallback<ResponseDisbursement.ResponseDisbursementItem> callback;
    public HolderDisbursement(@NonNull View itemView, ActionCallback<ResponseDisbursement.ResponseDisbursementItem> callback) {
        super(itemView);
        tvNo = itemView.findViewById(R.id.tvNo);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvPriceType = itemView.findViewById(R.id.tvPriceType);
        tvDetailPrice= itemView.findViewById(R.id.tvDetailPrice);
        tvTotRp = itemView.findViewById(R.id.tvTotRp);
        btnSee = itemView.findViewById(R.id.btnSee);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDel = itemView.findViewById(R.id.btnDel);
        this.callback = callback;
    }

    @Override
    public void bind(ResponseDisbursement.ResponseDisbursementItem data, int pos) {
        tvNo.setText(String.format("%03d", (pos+1)));
        tvDate.setText(data.getCreatedAt());
        tvPriceType.setText(data.getType());
        tvDetailPrice.setText(data.getDetail());
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
