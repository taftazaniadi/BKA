package com.buka.amanah.view.pelanggan;

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
import com.buka.amanah.adapter.HolderCustomer;
import com.buka.amanah.adapter.HolderCustomerTotal;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.customer_get.ResponseCustomer;
import com.buka.amanah.model.customer_get.ResponseCustomerTotal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class PelangganFragmentOwl extends Fragment implements PelangganView {

    public static int CODE_ADD = 100;
    public static int CODE_EDIT = 101;

    private PelangganPresenter presenter;
    private Button btnPelanggan;
    private FloatingActionButton btnPrevPage, btnNextPage;
    private ProgressBar pbPelanggan, pbPelangganTotal;
    private RecyclerView rvPelanggan, rvPelangganTotal;
    private TextView tvPage, tvTableDetail, tvEmptyPelanggan;
    private BaseAdapterRecycler<ResponseCustomer.ResponseCustomerItem> adapterPelanggan;
    private BaseAdapterRecycler<ResponseCustomerTotal.ResponseCustomerTotalData> adapterPelangganTotal;
    private int page = 1;
    private int dataCount = 0;
    private int pageRecord = 0;
    private int pageMaxRow = 10;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelanggan_owl, container, false);
        btnPelanggan = view.findViewById(R.id.buttonPelanggan);
        btnPrevPage = view.findViewById(R.id.previous_button);
        btnNextPage = view.findViewById(R.id.next_button);
        tvPage = view.findViewById(R.id.page_number_text);
        tvTableDetail = view.findViewById(R.id.table_details);
        tvEmptyPelanggan = view.findViewById(R.id.tvEmptyPelanggan);
        pbPelanggan = view.findViewById(R.id.pbLoadingPelanggan);
        pbPelangganTotal = view.findViewById(R.id.pbLoadingPelangganTotal);
        rvPelanggan = view.findViewById(R.id.rvPelanggan);
        rvPelangganTotal = view.findViewById(R.id.rvPelangganTotal);
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
        presenter = new PelangganPresenterImpl(requireContext(), this, requireContext().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE));
        setupRv();
        setupAction();
        presenter.getCustomer(page);
        presenter.getTotalCustomer();
    }

    private void setupAction() {
        btnPelanggan.setOnClickListener(view -> {
            Intent intent = new Intent(requireContext(), FormPelanggan.class);
            intent.putExtra("method", "Add");
            startActivityForResult(intent, CODE_ADD);
        });
        btnPrevPage.setOnClickListener(view -> {
            adapterPelanggan.clearItems();
            btnNextPage.setVisibility(View.VISIBLE);
            page -= 1;
            tvPage.setText(String.valueOf(page));
            presenter.getCustomer(page);
            if (page == 1)
                view.setVisibility(View.INVISIBLE);

        });
        btnNextPage.setOnClickListener(view -> {
            adapterPelanggan.clearItems();
            page += 1;
            tvPage.setText(String.valueOf(page));
            presenter.getCustomer(page);
        });
    }

    private void setupRv() {
        adapterPelanggan = new BaseAdapterRecycler<ResponseCustomer.ResponseCustomerItem>(){

            @Override
            protected int getLayoutId(int position, ResponseCustomer.ResponseCustomerItem obj) {
                return R.layout.item_pelanggan_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderCustomer(view, new ActionCallback<ResponseCustomer.ResponseCustomerItem>() {
                    @Override
                    public void onSee(ResponseCustomer.ResponseCustomerItem data) {
                        Intent i = new Intent(requireContext(), DetailPelanggan.class);
                        i.putExtra("customerId", data.getId().toString());
                        i.putExtra("method", "View");
                        startActivity(i);
                    }

                    @Override
                    public void onDel(ResponseCustomer.ResponseCustomerItem data) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                                .setTitle("Confirmation", TextAlignment.CENTER)
                                .setMessage("Apa anda yakin untuk menghapus Data ini?")
                                .setAnimation("542-warning-sign.json")
                                .setPositiveButton("Yes", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        presenter.delCustomer(data.getId().toString());
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
                    public void onEdit(ResponseCustomer.ResponseCustomerItem data) {
                        Intent i = new Intent(requireContext(), FormPelanggan.class);
                        i.putExtra("customerId", data.getId().toString());
                        i.putExtra("method", "Edit");
                        startActivityForResult(i, CODE_EDIT);
                    }
                });
            }
        };
        rvPelanggan.setAdapter(adapterPelanggan);
        adapterPelangganTotal = new BaseAdapterRecycler<ResponseCustomerTotal.ResponseCustomerTotalData>(){

            @Override
            protected int getLayoutId(int position, ResponseCustomerTotal.ResponseCustomerTotalData obj) {
                return R.layout.item_pelanggan_total_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderCustomerTotal(view);
            }
        };
        rvPelangganTotal.setAdapter(adapterPelangganTotal);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (presenter != null)
            presenter.getCustomer(page);
    }

    @Override
    public void onSuccessGetCustomer(List<ResponseCustomer.ResponseCustomerItem> mData) {
        if (pageRecord < page){
            pageRecord += 1;
            dataCount += mData.size();
        }
        tvTableDetail.setText(String.format("%s dari %s data", mData.size(), dataCount));
        adapterPelanggan.clearItems();
        adapterPelanggan.addAllItem(mData);
        if (mData.size() < pageMaxRow){
            btnNextPage.setVisibility(View.INVISIBLE);
        }else {
            btnNextPage.setVisibility(View.VISIBLE);
        }
        if (mData.size() == 0){
            tvEmptyPelanggan.setVisibility(View.VISIBLE);
        }else {
            tvEmptyPelanggan.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorGetCustomer(String msg, int code) {
        Toast.makeText(requireContext(), "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetCustomer(boolean isLoading) {
        if (isLoading) {
            pbPelanggan.setVisibility(View.VISIBLE);
            btnNextPage.setVisibility(View.INVISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.INVISIBLE);
        }
        else {
            pbPelanggan.setVisibility(View.GONE);
            btnNextPage.setVisibility(View.VISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessGetTotalCustomer(List<ResponseCustomerTotal.ResponseCustomerTotalData> mData) {
        adapterPelangganTotal.clearItems();
        adapterPelangganTotal.addAllItem(mData);
    }

    @Override
    public void onErrorGetTotalCustomer(String msg, int code) {
        Toast.makeText(requireContext(), "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetTotalCustomer(boolean isLoading) {
        if (isLoading)
            pbPelangganTotal.setVisibility(View.VISIBLE);
        else
            pbPelangganTotal.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccessDelCustomer(ResponseEmptyData mData) {
        MaterialDialog mDialog = new MaterialDialog.Builder(requireActivity())
                .setTitle("Success", TextAlignment.CENTER)
                .setMessage(mData.getResponseMessage())
                .setAnimation("6717-loading-passed-state.json")
                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        adapterPelanggan.clearItems();
                        presenter.getCustomer(page);
                    }
                })
                .build();

        mDialog.show();
    }

    @Override
    public void onErrorDelCustomer(String msg, int code) {
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
    public void onLoadingDelCustomer(boolean isLoading) {
        if (isLoading)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}
