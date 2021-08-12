package com.buka.amanah.view.stock;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.adapter.ActionCallback;
import com.buka.amanah.adapter.BaseAdapterRecycler;
import com.buka.amanah.adapter.HolderStock;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.stok_get.ResponseStok;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class StockFragmentOwl extends Fragment implements StockView {

    public static int CODE_ADD = 100;
    public static int CODE_EDIT = 101;

    private StockPresenter presenter;
    private Button btnStock;
    private FloatingActionButton btnPrevPage, btnNextPage;
    private ProgressBar pbStock, pbStockTotal;
    private RecyclerView rvStock;
    private TextView tvPage, tvTableDetail, tvEmptyStock;
    private BaseAdapterRecycler<ResponseStok.ResponseStokItem> adapterStock;
    private int page = 1;
    private int dataCount = 0;
    private int pageRecord = 0;
    private int pageMaxRow = 10;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stock_owl, container, false);
        btnStock = view.findViewById(R.id.buttonStock);
        btnPrevPage = view.findViewById(R.id.previous_button);
        btnNextPage = view.findViewById(R.id.next_button);
        tvPage = view.findViewById(R.id.page_number_text);
        tvTableDetail = view.findViewById(R.id.table_details);
        tvEmptyStock = view.findViewById(R.id.tvEmptyStock);
        pbStock = view.findViewById(R.id.pbLoadingStock);
        pbStockTotal = view.findViewById(R.id.pbLoadingStockTotal);
        rvStock = view.findViewById(R.id.rvStock);
        // setup
        btnPrevPage.setVisibility(View.GONE);
        tvPage.setText(String.valueOf(page));

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading ...");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new StockPresenterImpl(requireContext(), this, requireContext().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE));
        setupRv();
        setupAction();
        presenter.getStock(page);
    }

    private void setupAction() {
        btnStock.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), FormStockBarang.class);
            intent.putExtra("method", "Add");
            startActivityForResult(intent, CODE_ADD);
        });
        btnPrevPage.setOnClickListener(view -> {
            adapterStock.clearItems();
            btnNextPage.setVisibility(View.VISIBLE);
            page -= 1;
            tvPage.setText(String.valueOf(page));
            presenter.getStock(page);
            if (page == 1)
                view.setVisibility(View.INVISIBLE);

        });
        btnNextPage.setOnClickListener(view -> {
            adapterStock.clearItems();
            page += 1;
            tvPage.setText(String.valueOf(page));
            presenter.getStock(page);
        });
    }

    private void setupRv() {
        adapterStock = new BaseAdapterRecycler<ResponseStok.ResponseStokItem>(){

            @Override
            protected int getLayoutId(int position, ResponseStok.ResponseStokItem obj) {
                return R.layout.item_stock_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderStock(view, new ActionCallback<ResponseStok.ResponseStokItem>() {
                    @Override
                    public void onSee(ResponseStok.ResponseStokItem data) {
                        Intent i = new Intent(requireContext(), DetailStock.class);
                        i.putExtra("stockId", data.getId().toString());
                        i.putExtra("method", "View");
                        startActivity(i);
                    }

                    @Override
                    public void onDel(ResponseStok.ResponseStokItem data) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                                .setTitle("Confirmation", TextAlignment.CENTER)
                                .setMessage("Apa anda yakin untuk menghapus Data ini?")
                                .setAnimation("542-warning-sign.json")
                                .setPositiveButton("Yes", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        presenter.delStock(data.getId().toString());
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setNegativeButton("No", new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .build();

                        // Show Dialog
                        mDialog.show();
                    }

                    @Override
                    public void onEdit(ResponseStok.ResponseStokItem data) {
                        Intent i = new Intent(requireContext(), FormStockBarang.class);
                        i.putExtra("stockId", data.getId().toString());
                        i.putExtra("method", "Edit");
                        startActivityForResult(i, CODE_EDIT);
                    }
                });
            }
        };
        rvStock.setAdapter(adapterStock);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.getStock(page);
    }

    @Override
    public void onSuccessGetStock(List<ResponseStok.ResponseStokItem> mData) {
        if (pageRecord < page){
            pageRecord += 1;
            dataCount += mData.size();
        }
        tvTableDetail.setText(String.format("%s dari %s data", mData.size(), dataCount));
        adapterStock.clearItems();
        adapterStock.addAllItem(mData);
        if (mData.size() < pageMaxRow){
            btnNextPage.setVisibility(View.INVISIBLE);
        }else {
            btnNextPage.setVisibility(View.VISIBLE);
        }
        if (mData.size() == 0){
            tvEmptyStock.setVisibility(View.VISIBLE);
        }else {
            tvEmptyStock.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorGetStock(String msg, int code) {
        Toast.makeText(requireContext(), "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetStock(boolean isLoading) {
        if (isLoading) {
            pbStock.setVisibility(View.VISIBLE);
            btnNextPage.setVisibility(View.INVISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.INVISIBLE);
        }
        else {
            pbStock.setVisibility(View.GONE);
            btnNextPage.setVisibility(View.VISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessDelStock(ResponseEmptyData mData) {
        MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                .setTitle("Success", TextAlignment.CENTER)
                .setMessage(mData.getResponseMessage())
                .setAnimation("6717-loading-passed-state.json")
                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        adapterStock.clearItems();
                        presenter.getStock(page);
                    }
                })
                .build();

        mDialog.show();
    }

    @Override
    public void onErrorDelStock(String msg, int code) {
        MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                .setTitle("Failed", TextAlignment.CENTER)
                .setMessage(msg)
                .setAnimation("6718-loading-fail-state.json")
                .setPositiveButton("OK", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                    }
                })
                .build();

        // Show Dialog
        mDialog.show();
    }

    @Override
    public void onLoadingDelStock(boolean isLoading) {
        if (isLoading)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}
