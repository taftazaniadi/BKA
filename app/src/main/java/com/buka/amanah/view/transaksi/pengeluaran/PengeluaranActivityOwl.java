package com.buka.amanah.view.transaksi.pengeluaran;

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
import com.buka.amanah.adapter.HolderDisbursement;
import com.buka.amanah.adapter.HolderReceipt;
import com.buka.amanah.model.ResponseEmptyData;
import com.buka.amanah.model.disbursement_get.ResponseDisbursement;
import com.buka.amanah.model.receipt_get.ResponseReceipt;
import com.buka.amanah.view.transaksi.penerimaan.DetailPenerimaan;
import com.buka.amanah.view.transaksi.penerimaan.FormPenerimaan;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

/*
 * Created by OwlDev23 on 11,August,2021
 */

public class PengeluaranActivityOwl extends AppCompatActivity implements PengeluaranView {

    public static int CODE_ADD = 100;
    public static int CODE_EDIT = 101;

    private PengeluaranPresenter presenter;
    private Toolbar toolbar;
    private Button btnPengeluaran;
    private FloatingActionButton btnPrevPage, btnNextPage;
    private ProgressBar pbPengeluaran;
    private RecyclerView rvPengeluaran;
    private TextView tvPage, tvTableDetail, tvEmptyPengeluaran;
    private BaseAdapterRecycler<ResponseDisbursement.ResponseDisbursementItem> adapterPengeluaran;
    private int page = 1;
    private int dataCount = 0;
    private int pageRecord = 0;
    private int pageMaxRow = 10;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengeluaran_owl);
        presenter = new PengeluaranPresenterImpl(getBaseContext(), this, getApplication().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE));
        initView();
        setupToolbar();
        setupRv();
        setupAction();
        presenter.getDisbursements(page);
    }

    private void setupAction() {
        btnPengeluaran.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), FormPenerimaan.class);
            intent.putExtra("method", "Add");
            startActivityForResult(intent, CODE_ADD);
        });
        btnPrevPage.setOnClickListener(view -> {
            adapterPengeluaran.clearItems();
            btnNextPage.setVisibility(View.VISIBLE);
            page -= 1;
            tvPage.setText(String.valueOf(page));
            presenter.getDisbursements(page);
            if (page == 1)
                view.setVisibility(View.GONE);

        });
        btnNextPage.setOnClickListener(view -> {
            adapterPengeluaran.clearItems();
            page += 1;
            tvPage.setText(String.valueOf(page));
            presenter.getDisbursements(page);
        });
    }

    private void setupRv() {
        adapterPengeluaran = new BaseAdapterRecycler<ResponseDisbursement.ResponseDisbursementItem>(){

            @Override
            protected int getLayoutId(int position, ResponseDisbursement.ResponseDisbursementItem obj) {
                return R.layout.item_pengeluaran_row_table;
            }

            @Override
            protected RecyclerView.ViewHolder getHolder(View view, int viewType) {
                return new HolderDisbursement(view, new ActionCallback<ResponseDisbursement.ResponseDisbursementItem>() {
                    @Override
                    public void onSee(ResponseDisbursement.ResponseDisbursementItem data) {
                        Intent i = new Intent(getBaseContext(), DetailPengeluaran.class);
                        i.putExtra("disbursementId", data.getId().toString());
                        i.putExtra("method", "View");
                        startActivity(i);
                    }

                    @Override
                    public void onDel(ResponseDisbursement.ResponseDisbursementItem data) {
                        MaterialDialog mDialog = new MaterialDialog.Builder(PengeluaranActivityOwl.this)
                                .setTitle("Confirmation", TextAlignment.CENTER)
                                .setMessage("Apa anda yakin untuk menghapus Data ini?")
                                .setAnimation("542-warning-sign.json")
                                .setPositiveButton("Yes", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int which) {
                                        presenter.delDisbursements(data.getId().toString());
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
                    public void onEdit(ResponseDisbursement.ResponseDisbursementItem data) {
                        Intent i = new Intent(getBaseContext(), FormPengeluaran.class);
                        i.putExtra("disbursementId", data.getId().toString());
                        i.putExtra("method", "Edit");
                        startActivityForResult(i, CODE_EDIT);
                    }
                });
            }
        };
        rvPengeluaran.setAdapter(adapterPengeluaran);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Pegeluaran");
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
        btnPengeluaran = findViewById(R.id.buttonPengeluaran);
        btnPrevPage = findViewById(R.id.previous_button);
        btnNextPage = findViewById(R.id.next_button);
        tvPage = findViewById(R.id.page_number_text);
        tvTableDetail = findViewById(R.id.table_details);
        tvEmptyPengeluaran = findViewById(R.id.tvEmptyPengeluaran);
        pbPengeluaran = findViewById(R.id.pbLoadingPengeluaran);
        rvPengeluaran = findViewById(R.id.rvPengeluaran);
        // setup
        btnPrevPage.setVisibility(View.INVISIBLE);
        tvPage.setText(String.valueOf(page));

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ...");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK)
            presenter.getDisbursements(page);
    }

    @Override
    public void onSuccessGetDisbursements(List<ResponseDisbursement.ResponseDisbursementItem> mData) {
        if (pageRecord < page){
            pageRecord += 1;
            dataCount += mData.size();
        }
        tvTableDetail.setText(String.format("%s dari %s data", mData.size(), dataCount));
        adapterPengeluaran.clearItems();
        adapterPengeluaran.addAllItem(mData);
        if (mData.size() < pageMaxRow){
            btnNextPage.setVisibility(View.INVISIBLE);
        }else {
            btnNextPage.setVisibility(View.VISIBLE);
        }
        if (mData.size() == 0){
            tvEmptyPengeluaran.setVisibility(View.VISIBLE);
        }else {
            tvEmptyPengeluaran.setVisibility(View.GONE);
        }
    }

    @Override
    public void onErrorGetDisbursements(String msg, int code) {
        Toast.makeText(this, "Error " + msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoadingGetDisbursements(boolean isLoading) {
        if (isLoading) {
            pbPengeluaran.setVisibility(View.VISIBLE);
            btnNextPage.setVisibility(View.INVISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.GONE);
        }
        else {
            pbPengeluaran.setVisibility(View.GONE);
            btnNextPage.setVisibility(View.VISIBLE);
            if (page != 1)
                btnPrevPage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSuccessDelDisbursements(ResponseEmptyData mData) {
        MaterialDialog mDialog = new MaterialDialog.Builder(PengeluaranActivityOwl.this)
                .setTitle("Success", TextAlignment.CENTER)
                .setMessage(mData.getResponseMessage())
                .setAnimation("6717-loading-passed-state.json")
                .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        adapterPengeluaran.clearItems();
                        presenter.getDisbursements(page);
                    }
                })
                .build();

        mDialog.show();
    }

    @Override
    public void onErrorDelDisbursements(String msg, int code) {
        MaterialDialog mDialog = new MaterialDialog.Builder(PengeluaranActivityOwl.this)
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
    public void onLoadingDelDisbursements(boolean isLoading) {
        if (isLoading)
            progressDialog.show();
        else
            progressDialog.dismiss();
    }
}