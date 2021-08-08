package com.buka.amanah.pelanggan;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.buka.amanah.R;
import com.buka.amanah.utils.tableview.TableViewAdapter;
import com.buka.amanah.utils.tableview.TableViewListener;
import com.buka.amanah.utils.tableview.TableViewModel;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.pagination.Pagination;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PelangganFragment extends Fragment {

    Button buttonAddPelanggan;
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelanggan, container, false);

        buttonAddPelanggan = view.findViewById(R.id.buttonAddPelanggan);

        buttonAddPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddPelanggan.class);
                startActivity(intent);
            }
        });

        // Table View
        View tableTestContainer = view.findViewById(R.id.table_test_container);

        previousButton = view.findViewById(R.id.previous_button);
        nextButton = view.findViewById(R.id.next_button);
        tablePaginationDetails = view.findViewById(R.id.table_details);
        pageNumbers = view.findViewById(R.id.page_number_text);
        items_per_page = view.findViewById(R.id.items_per_page);

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
        mTableView = view.findViewById(R.id.tableview_data);
        mTableViewDetails = view.findViewById(R.id.tableview_details);

        initializeTableView();
        initializeTableViewDetails();

        if (mPaginationEnabled) {
            // Create an instance for the TableView pagination and pass the created TableView.
            mPagination = new Pagination(mTableView);

            // Sets the pagination listener of the TableView pagination to handle
            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
        }

        return view;
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, getContext());

        mTableView.setAdapter(tableViewAdapter);
        mTableView.setTableViewListener(new TableViewListener(mTableView, getContext()));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getColumnHeaderList("Pelanggan"), tableViewModel
                .getSimpleRowHeaderList(), tableViewModel.getCellList("Pelanggan", message));

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
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, getContext());

        mTableViewDetails.setAdapter(tableViewAdapter);
        mTableViewDetails.setTableViewListener(new TableViewListener(mTableViewDetails, getContext()));

        // Create an instance of a Filter and pass the TableView.
        //mTableFilter = new Filter(mTableView);

        // Load the dummy data to the TableView
        tableViewAdapter.setAllItems(tableViewModel.getDetailColumnHeaderList("Pelanggan"), tableViewModel
                .getDetailRowHeaderList("Pelanggan"), tableViewModel.getDetailCellList("Pelanggan", messageDetail));
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

                    tablePaginationDetails.setText(getString(R.string.table_pagination_details, String.valueOf(itemsStart + 1), String.valueOf(itemsEnd + 1)));
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
}