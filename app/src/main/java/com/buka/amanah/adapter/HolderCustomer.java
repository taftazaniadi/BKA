package com.buka.amanah.adapter;

import android.view.View;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.customer_get.ResponseCustomer;
import com.buka.amanah.model.receipt_get.ResponseReceipt;

public class HolderCustomer extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<ResponseCustomer.ResponseCustomerItem> {
    private AppCompatTextView tvNo, tvDate, tvCustomer, tvAddress, tvHp, tvTransaction, tvAction;
    private ImageButton btnSee, btnEdit, btnDel;
    private ActionCallback<ResponseCustomer.ResponseCustomerItem> callback;
    public HolderCustomer(@NonNull View itemView, ActionCallback<ResponseCustomer.ResponseCustomerItem> callback) {
        super(itemView);
        tvNo = itemView.findViewById(R.id.tvNo);
        tvDate = itemView.findViewById(R.id.tvDate);
        tvCustomer = itemView.findViewById(R.id.tvCustomer);
        tvHp = itemView.findViewById(R.id.tvHp);
        tvAddress = itemView.findViewById(R.id.tvAddress);
        tvTransaction = itemView.findViewById(R.id.tvTransaction);
        tvAction = itemView.findViewById(R.id.tvAction);

        btnSee = itemView.findViewById(R.id.btnSee);
        btnEdit = itemView.findViewById(R.id.btnEdit);
        btnDel = itemView.findViewById(R.id.btnDel);
        this.callback = callback;
    }

    @Override
    public void bind(ResponseCustomer.ResponseCustomerItem data, int pos) {
        tvNo.setText(String.format("%03d", (pos+1)));
        tvDate.setText(data.getCreatedAt());
        tvCustomer.setText(data.getName());
        tvHp.setText(data.getPhone());
        tvAddress.setText(data.getAddress());
        tvTransaction.setText("kosong");
        tvAction.setText("kosong");
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
