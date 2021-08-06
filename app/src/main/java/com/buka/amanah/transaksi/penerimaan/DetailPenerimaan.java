package com.buka.amanah.transaksi.penerimaan;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buka.amanah.R;

import dev.shreyaspatil.MaterialDialog.AbstractDialog;
import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class DetailPenerimaan extends AppCompatActivity {

    TextView tvTgl, tvCust, tvReceipt, tvAmount, tvPrice, tvTotal;
    Button btnSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_penerimaan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Detail Penerimaan");

        tvTgl = findViewById(R.id.txtHariTgl);
        tvCust = findViewById(R.id.txtPelangganPenerimaan);
        tvReceipt = findViewById(R.id.txtPenerimaan);
        tvAmount = findViewById(R.id.txtJumlahPenerimaan);
        tvPrice = findViewById(R.id.txtHargaPenerimaan);
        tvTotal = findViewById(R.id.txtTotalPenerimaan);
        btnSelesai = findViewById(R.id.btnSelesai);

        tvTgl.setText(getIntent().getStringExtra("tgl"));
        tvCust.setText(getIntent().getStringExtra("cust"));
        tvReceipt.setText(getIntent().getStringExtra("receipt"));
        tvAmount.setText(getIntent().getStringExtra("amount"));
        tvPrice.setText(getIntent().getStringExtra("price"));
        tvTotal.setText(getIntent().getStringExtra("total"));

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(DetailPenerimaan.this)
                        .setTitle("Success", TextAlignment.CENTER)
                        .setMessage("Data has been Saved")
                        .setAnimation("6717-loading-passed-state.json")
                        .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                                startActivity(new Intent(getApplicationContext(), PenerimaanActivity.class));
                                finishAffinity();
                            }
                        })
                        .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                                startActivity(new Intent(getApplicationContext(), AddPenerimaan.class));
                                finishAffinity();
                            }
                        })
                        .build();

                // Show Dialog
                mDialog.show();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
        startActivity(new Intent(this, PenerimaanActivity.class));
        finishAffinity();
    }
}