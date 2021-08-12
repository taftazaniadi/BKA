package com.buka.amanah.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.customer_get.ResponseCustomerTotal;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;

public class HolderCustomerTotal extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseCustomerTotal.ResponseCustomerTotalData> {
    private AppCompatTextView tvReceive;
    public HolderCustomerTotal(@NonNull View itemView) {
        super(itemView);
        tvReceive = itemView.findViewById(R.id.tvReceive);
    }

    @Override
    public void bind(ResponseCustomerTotal.ResponseCustomerTotalData data, int pos) {
        tvReceive.setText(data.getTotal().toString());
    }
}
