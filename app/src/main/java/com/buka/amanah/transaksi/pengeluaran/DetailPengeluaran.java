package com.buka.amanah.transaksi.pengeluaran;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.buka.amanah.R;
import com.buka.amanah.transaksi.penerimaan.AddPenerimaan;
import com.buka.amanah.transaksi.penerimaan.DetailPenerimaan;
import com.buka.amanah.transaksi.penerimaan.PenerimaanActivity;

import dev.shreyaspatil.MaterialDialog.MaterialDialog;
import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;
import dev.shreyaspatil.MaterialDialog.model.TextAlignment;

public class DetailPengeluaran extends AppCompatActivity {

    TextView tvTgl, tvBiaya, tvCategory, tvKet, tvTotal;
    Button btnSelesai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengeluaran);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setTitle("Detail Penerimaan");

        tvTgl = findViewById(R.id.txtHariTgl);
        tvBiaya = findViewById(R.id.txtBiayaPengeluaran);
        tvCategory = findViewById(R.id.txtDetailBiaya);
        tvKet = findViewById(R.id.txtKeterangan);
        tvTotal = findViewById(R.id.txtTotalPenerimaan);
        btnSelesai = findViewById(R.id.btnSelesai);

        tvTgl.setText(getIntent().getStringExtra("tgl"));
        tvBiaya.setText(getIntent().getStringExtra("biaya"));
        tvCategory.setText(getIntent().getStringExtra("category"));
        tvKet.setText(getIntent().getStringExtra("detail"));
        tvTotal.setText(getIntent().getStringExtra("total"));

        btnSelesai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MaterialDialog mDialog = new MaterialDialog.Builder(DetailPengeluaran.this)
                        .setTitle("Success", TextAlignment.CENTER)
                        .setMessage("Data has been Saved")
                        .setAnimation("6717-loading-passed-state.json")
                        .setPositiveButton("DONE", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                                startActivity(new Intent(getApplicationContext(), PenerimaanActivity.class));
                                finish();
                            }
                        })
                        .setNegativeButton("Tambah Lagi", new MaterialDialog.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                dialogInterface.dismiss();

                                startActivity(new Intent(getApplicationContext(), AddPengeluaran.class));
                                finish();
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
        finish();
    }
}