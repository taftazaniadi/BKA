package com.buka.amanah.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.model.DummyDataModel;
import com.squareup.picasso.Picasso;

public class HolderAdvertisement extends RecyclerView.ViewHolder implements BaseAdapterRecycler.Binder<DummyDataModel> {
    private AppCompatImageView imgAdvertisement;

    public HolderAdvertisement(@NonNull View itemView) {
        super(itemView);
        imgAdvertisement = itemView.findViewById(R.id.imgAdvertisement);
    }

    @Override
    public void bind(DummyDataModel data, int pos) {
        Picasso.with(itemView.getContext()).load(data.getLabel()).placeholder(R.drawable.image_loading).into(imgAdvertisement);
    }
}
