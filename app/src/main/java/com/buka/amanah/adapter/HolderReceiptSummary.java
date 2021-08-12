package com.buka.amanah.adapter;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;

public class HolderReceiptSummary extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseReceiptSummary.ResponseReceiptSummaryItem> {
    private AppCompatTextView tvNo, tvReceive, tvCount, tvTotRp;
    public HolderReceiptSummary(@NonNull View itemView) {
        super(itemView);
        tvNo = itemView.findViewById(R.id.tvNo);
        tvReceive = itemView.findViewById(R.id.tvReceive);
        tvCount = itemView.findViewById(R.id.tvCount);
        tvTotRp = itemView.findViewById(R.id.tvTotRp);
    }

    @Override
    public void bind(ResponseReceiptSummary.ResponseReceiptSummaryItem data, int pos) {
        tvNo.setText(String.format("%02d", (pos+1)));
        tvReceive.setText(data.getName());
        tvCount.setText(data.getCount());
        tvTotRp.setText("Rp. " + data.getTotal());
    }
}
