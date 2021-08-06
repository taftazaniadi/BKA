package com.buka.amanah.utils.action_menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.buka.amanah.R;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<MenuPojo> {

    // invoke the suitable constructor of the ArrayAdapter class
    public MenuAdapter(@NonNull Context context, ArrayList<MenuPojo> arrayList) {

        // pass the context and arrayList for the super
        // constructor of the ArrayAdapter class
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // convertView which is recyclable view
        View currentItemView = convertView;

        // of the recyclable view is null then inflate the custom layout for the same
        if (currentItemView == null) {
            currentItemView = LayoutInflater.from(getContext()).inflate(R.layout.alertdialog_layout, parent, false);
        }

        // get the position of the view from the ArrayAdapter
        MenuPojo currentNumberPosition = getItem(position);

        // then according to the position of the view assign the desired image for the same
        ImageView numbersImage = currentItemView.findViewById(R.id.iv1);
        assert currentNumberPosition != null;
        numbersImage.setImageResource(currentNumberPosition.getIvMenu());

        // then according to the position of the view assign the desired TextView 1 for the same
        TextView textView1 = currentItemView.findViewById(R.id.tv1);
        textView1.setText(currentNumberPosition.getTvMenu());

        // then return the recyclable view
        return currentItemView;
    }
}
