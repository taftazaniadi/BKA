package com.buka.amanah;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.buka.amanah.adapter.ViewPagerAdapter;
import com.buka.amanah.view.hutangpiutang.HutangPiutangFragment;
import com.buka.amanah.view.pelanggan.PelangganFragment;
import com.buka.amanah.view.stock.StockFragment;
import com.buka.amanah.view.transaksi.TransaksiFragment;
import com.buka.amanah.view.users.profile.UserProfile;
import com.buka.amanah.view.home.HomeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    MenuItem prevMenuItem;
    ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Toolbar */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(0);

//        actionbar = getSupportActionBar();
////        actionbar.setIcon(R.drawable.image);
//        actionbar.setTitle(getString(R.string.app_name));

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        viewPager = (ViewPager) findViewById(R.id.viewpager);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_home:
                        viewPager.setCurrentItem(0);
                        break;

                    case R.id.action_trx:
                        viewPager.setCurrentItem(1);
                        break;

                    case R.id.action_hutang:
                        viewPager.setCurrentItem(2);
                        break;

                    case R.id.action_customer:
                        viewPager.setCurrentItem(3);
                        break;

                    case R.id.action_stock:
                        viewPager.setCurrentItem(4);
                        break;

                }
                return false;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                } else {
                    bottomNavigationView.getMenu().getItem(0).setChecked(false);
                }
                bottomNavigationView.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNavigationView.getMenu().getItem(position);

                switch(position) {
                    case 0:
                        actionbar = getSupportActionBar();
//        actionbar.setIcon(R.drawable.image);
                        actionbar.setTitle(getString(R.string.app_name));
                        break;

                    case 1:
                        actionbar = getSupportActionBar();
                        actionbar.setTitle(getString(R.string.transaksi));
                        break;

                    case 2:
                        actionbar = getSupportActionBar();
                        actionbar.setTitle(getString(R.string.utang));
                        break;

                    case 3:
                        actionbar = getSupportActionBar();
                        actionbar.setTitle(getString(R.string.pelanggan));
                        break;

                    case 4:
                        actionbar = getSupportActionBar();
                        actionbar.setTitle(getString(R.string.stok));
                        break;
                }
            }


            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        setupViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new TransaksiFragment());
        adapter.addFragment(new HutangPiutangFragment());
        adapter.addFragment(new PelangganFragment());
        adapter.addFragment(new StockFragment());
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profil:
                startActivity(new Intent(this, UserProfile.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}