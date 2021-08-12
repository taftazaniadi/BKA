package com.buka.amanah.view.hutangpiutang.piutang.usaha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.buka.amanah.R;
import com.buka.amanah.MainActivity;
import com.buka.amanah.utils.tableview.TableViewAdapter;
import com.buka.amanah.utils.tableview.TableViewListener;
import com.buka.amanah.utils.tableview.TableViewModel;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.pagination.Pagination;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PiutangUsahaActivity extends AppCompatActivity {

    Button buttonPiutangUsaha;
    String message, messageDetail;

    //Table View
    private FloatingActionButton previousButton, nextButton;
    private Spinner items_per_page;
    private TextView tablePaginationDetails, pageNumbers;
    private TableView mTableView, mTableViewDetails;
    @Nullable
    private Pagination mPagination; // This is used for paginating the table.

    private boolean mPaginationEnabled = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_piutang_usaha);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setElevation(16);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        //actionbar.setLogo(R.drawable.ic_logo);
        actionbar.setTitle("Piutang Usaha");

        buttonPiutangUsaha = findViewById(R.id.buttonPiutangUsaha);

        buttonPiutangUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), FormPiutangUsaha.class);
                startActivity(intent);
            }
        });

        // making notification bar transparent
        changeStatusBarColor();

        // Table View
        View tableTestContainer = findViewById(R.id.table_test_container);

        previousButton = findViewById(R.id.previous_button);
        nextButton = findViewById(R.id.next_button);
        tablePaginationDetails = findViewById(R.id.table_details);
        pageNumbers = findViewById(R.id.page_number_text);
        items_per_page = findViewById(R.id.items_per_page);

        previousButton.setVisibility(View.INVISIBLE);

        if (mPaginationEnabled) {
            tableTestContainer.setVisibility(View.VISIBLE);
            items_per_page.setOnItemSelectedListener(onItemsPerPageSelectedListener);

            previousButton.setOnClickListener(mClickListener);
            nextButton.setOnClickListener(mClickListener);
        } else {
            tableTestContainer.setVisibility(View.GONE);
        }

        // Let's get TableView
        mTableView = findViewById(R.id.tableview_data);
        mTableViewDetails = findViewById(R.id.tableview_details);

        initializeTableView();
        initializeTableViewDetails();

        if (mPaginationEnabled) {
            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // Sets the pagination listener of the TableView pagination to handle
            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, PiutangUsahaActivity.this);

        mTableView.setAdapter(tableViewAdapter);
        mTableView.setTableViewListener(new TableViewListener(mTableView, PiutangUsahaActivity.this));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderList("Piutang Usaha"), tableViewModel
                .getSimpleRowHeaderList(), tableViewModel.getCellList("Piutang Usaha", message));

        //mTableView.setHasFixedWidth(true);

        /*for (int i = 0; i < mTableViewModel.getCellList().size(); i++) {
            mTableView.setColumnWidth(i, 200);
        }*)

        //mTableView.setColumnWidth(0, -2);
        //mTableView.setColumnWidth(1, -2);

        /*mTableView.setColumnWidth(2, 200);
        mTableView.setColumnWidth(3, 300);
        mTableView.setColumnWidth(4, 400);
        mTableView.setColumnWidth(5, 500);*/

    }

    private void initializeTableViewDetails() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, PiutangUsahaActivity.this);

        mTableViewDetails.setAdapter(tableViewAdapter);
        mTableViewDetails.setTableViewListener(new TableViewListener(mTableViewDetails, PiutangUsahaActivity.this));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getDetailColumnHeaderList("Piutang Usaha"), tableViewModel
                .getDetailRowHeaderList("Piutang Usaha"), tableViewModel.getDetailCellList("Piutang Usaha", messageDetail));
    }

    // The following four methods below: nextTablePage(), previousTablePage(),
    // goToTablePage(int page) and setTableItemsPerPage(int itemsPerPage)
    // are for controlling the TableView pagination.
    public void nextTablePage() {
        if (mPagination != null) {
            mPagination.nextPage();
        }
    }

    public void previousTablePage() {
        if (mPagination != null) {
            mPagination.previousPage();
        }
    }

    public void setTableItemsPerPage(int itemsPerPage) {
        if (mPagination != null) {
            mPagination.setItemsPerPage(itemsPerPage);
        }
    }

    // Handler for the changing of pages in the paginated TableView.
    @NonNull
    private final Pagination.OnTableViewPageTurnedListener onTableViewPageTurnedListener = new
            Pagination.OnTableViewPageTurnedListener() {
                @Override
                public void onPageTurned(int numItems, int itemsStart, int itemsEnd) {
                    int currentPage = mPagination.getCurrentPage();
                    int pageCount = mPagination.getPageCount();
                    nextButton.setVisibility(View.VISIBLE);

                    if (currentPage == 1) {
                        previousButton.setVisibility(View.INVISIBLE);
                    } else {
                        previousButton.setVisibility(View.VISIBLE);
                    }

                    if (currentPage == pageCount) {
                        nextButton.setVisibility(View.INVISIBLE);
                    }

                    tablePaginationDetails.setText(String.format("%s dari %s data", (itemsStart + 1), (itemsEnd + 1)));
                    pageNumbers.setText(String.valueOf(currentPage));
                }
            };

    @NonNull
    private final AdapterView.OnItemSelectedListener onItemsPerPageSelectedListener = new AdapterView
            .OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            int itemsPerPage;
            if ("All".equals(parent.getItemAtPosition(position).toString())) {
                itemsPerPage = 0;
            } else {
                itemsPerPage = Integer.parseInt(parent.getItemAtPosition(position).toString());
            }

            setTableItemsPerPage(itemsPerPage);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    @NonNull
    private final View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == previousButton) {
                previousTablePage();
            } else if (v == nextButton) {
                nextTablePage();
            }
        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if(getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                }
                else {
                    super.onBackPressed();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        //this is only needed if you have specific things
        //that you want to do when the user presses the back button.
        /* your specific things...*/
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}