package com.buka.amanah.view.transaksi.penerimaan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.adapter.ActionCallback;
import com.buka.amanah.adapter.BaseAdapterRecycler;
import com.buka.amanah.adapter.HolderReceipt;
import com.buka.amanah.adapter.HolderReceiptSummary;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.model.receipt_summary.ResponseReceiptSummary;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public class PenerimaanActivityOwl extends AppCompatActivity implements PenerimaanView{

    public static int CODE_ADD = 100;
    public static int CODE_EDIT = 101;

    private PenerimaanPresenter presenter;
    private Toolbar toolbar;
    private Button btnPenerimaan;
    private FloatingActionButton btnPrevPage, btnNextPage;
    private ProgressBar pbPenerimaan, pbPenerimaanTotal;
    private RecyclerView rvPenerimaan, rvPenerimaanTotal;
    private TextView tvPage, tvTableDetail, tvEmptyPenerimaan;
    private BaseAdapterRecycler<ResponseReceipt.ResponseReceiptItem> adapterPenerimaan;
    private BaseAdapterRecycler<ResponseReceiptSummary.ResponseReceiptSummaryItem> adapterPenerimaanTotal;
    private int page = 1;
    private int dataCount = 0;
    private int pageRecord = 0;
    private int pageMaxRow = 10;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_penerimaan_owl);
        presenter = new PenerimaanPresenterImpl(getBaseContext(), this, getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE));
        initView();
        setupToolbar();
        setupRv();
        setupAction();
        presenter.getReceipt(page);
        presenter.getSummaryReceipt();
    }

    private void setupAction() {
        btnPenerimaan.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FormPenerimaan.class);
            intent.putExtra("method", "Add");
            startActivityForResult(intent, CODE_ADD);
        });
        btnPrevPage.setOnClickListener(view -> {
            adapterPenerimaan.clearItems();
            btnNextPage.setVisibility(View.VISIBLE);
            page -= 1;
            tvPage.setText(String.valueOf(page));
            presenter.getReceipt(page);
            if (page == 1)
                view.setVisibility(View.GONE);

        });
        btnNextPage.setOnClickListener(view -> {
            adapterPenerimaan.clearItems();
            page += 1;
            tvPage.setText(String.valueOf(page));
            presenter.getReceipt(page);
        });
    }

    private void setupRv() {
        adapterPenerimaan = new BaseAdapterRecycler<ResponseReceipt.ResponseReceiptItem>(){

            @Override
            protected int getLayoutId(int position, ResponseReceipt.ResponseReceiptItem obj) {
                return R.layout.item_penerimaan_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderReceipt(view, new ActionCallback<ResponseReceipt.ResponseReceiptItem>() {
                    @Override
                    public void onSee(ResponseReceipt.ResponseReceiptItem data) {
                        Intent i = new Intent(getBaseContext(), DetailPenerimaan.class);
                        i.putExtra("receiptId", data.getId().toString());
                        i.putExtra("method", "View");
                        startActivity(i);
                    }

                    @Override
                    public void onDel(ResponseReceipt.ResponseReceiptItem data) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(PenerimaanActivityOwl.this)
                                .setTitle("Confirmation", TextAlignment.CENTER)
                                .setMessage("Apa anda yakin untuk menghapus Data ini?")
                                .setAnimation("542-warning-sign.json")
                                .setPositiveButton("Yes", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        presenter.delReceipt(data.getId().toString());
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
                    public void onEdit(ResponseReceipt.ResponseReceiptItem data) {
                        Intent i = new Intent(getBaseContext(), FormPenerimaan.class);
                        i.putExtra("receiptId", data.getId().toString());
                        i.putExtra("method", "Edit");
                        startActivityForResult(i, CODE_EDIT);
                    }
                });
            }
        };
        rvPenerimaan.setAdapter(adapterPenerimaan);
        adapterPenerimaanTotal = new BaseAdapterRecycler<ResponseReceiptSummary.ResponseReceiptSummaryItem>(){

            @Override
            protected int getLayoutId(int position, ResponseReceiptSummary.ResponseReceiptSummaryItem obj) {
                return R.layout.item_penerimaan_total_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderReceiptSummary(view);
            }
        };
        rvPenerimaanTotal.setAdapter(adapterPenerimaanTotal);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Penerimaan");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        btnPenerimaan = findViewById(R.id.buttonPenerimaan);
        btnPrevPage = findViewById(R.id.previous_button);
        btnNextPage = findViewById(R.id.next_button);
        tvPage = findViewById(R.id.page_number_text);
        tvTableDetail = findViewById(R.id.table_details);
        tvEmptyPenerimaan = findViewById(R.id.tvEmptyPenerimaan);
        pbPenerimaan = findViewById(R.id.pbLoadingPenerimaan);
        pbPenerimaanTotal = findViewById(R.id.pbLoadingPenerimaanTotal);
        rvPenerimaan = findViewById(R.id.rvPenerimaan);
        rvPenerimaanTotal = findViewById(R.id.rvPenerimaanTotal);
        // setup
        btnPrevPage.setVisibility(View.GONE);
        tvPage.setText(String.valueOf(page));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK)
            presenter.getReceipt(page);
    }

    @Override
    public void onSuccessGetReceipt(List<ResponseReceipt.ResponseReceiptItem> mData) {
        if (pageRecord < page){
            pageRecord += 1;
            dataCount += mData.size();
        }
        tvTableDetail.setText(String.format("%s dari %s data", mData.size(), dataCount));
        adapterPenerimaan.clearItems();
        adapterPenerimaan.addAllItem(mData);
        if (mData.size() < pageMaxRow){
            btnNextPage.setVisibility(View.GONE);
        }else {
            btnNextPage.setVisibility(View.VISIBLE);
        }
        if (mData.size() == 0){
            tvEmptyPenerimaan.setVisibility(View.VISIBLE);
        }else {
            tvEmptyPenerimaan.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorGetReceipt(String msg, int code) {
        Toast.makeText(this, "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetReceipt(boolean isLoading) {
        if (isLoading) {
            pbPenerimaan.setVisibility(View.VISIBLE);
            btnNextPage.setVisibility(View.INVISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.GONE);
        }
        else {
            pbPenerimaan.setVisibility(View.GONE);
            btnNextPage.setVisibility(View.VISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessGetSummaryReceipt(List<ResponseReceiptSummary.ResponseReceiptSummaryItem> mData) {
        adapterPenerimaanTotal.clearItems();
        adapterPenerimaanTotal.addAllItem(mData);
    }

    @Override
    public void onErrorGetSummaryReceipt(String msg, int code) {
        Toast.makeText(this, "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetSummaryReceipt(boolean isLoading) {
        if (isLoading)
            pbPenerimaanTotal.setVisibility(View.VISIBLE);
        else
            pbPenerimaanTotal.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onSuccessDelReceipt(ResponseEmptyData mData) {
        MaterialDialog mDialog = new MaterialDialog.Builder(PenerimaanActivityOwl.this)
                .setTitle("Success", TextAlignment.CENTER)
                .setMessage(mData.getResponseMessage())
                .setAnimation("6717-loading-passed-state.json")
                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        adapterPenerimaan.clearItems();
                        presenter.getReceipt(page);
                    }
                })
                .build();

        mDialog.show();
    }

    @Override
    public void onErrorDelReceipt(String msg, int code) {
        MaterialDialog mDialog = new MaterialDialog.Builder(PenerimaanActivityOwl.this)
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
    public void onLoadingDelReceipt(boolean isLoading) {
        if (isLoading)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}