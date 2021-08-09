package com.buka.amanah.pelanggan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.buka.amanah.R;
import com.buka.amanah.utils.tableview.TableViewAdapter;
import com.buka.amanah.utils.tableview.TableViewListener;
import com.buka.amanah.utils.tableview.TableViewModel;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.pagination.Pagination;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class PelangganFragment extends Fragment {

    RequestQueue mRequestQueue;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private Context mContext;

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

    @SuppressLint("ResourceType")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pelanggan, container, false);

        mContext = requireActivity();
        System.out.println("DATA CREATE CONTEXT : " + inflater.getContext());

        mRequestQueue = Volley.newRequestQueue(getContext());

        sharedPreferences = getContext().getSharedPreferences("BuKaAuth", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        getList();
        getDetailList();

        buttonAddPelanggan = view.findViewById(R.id.buttonAddPelanggan);

        buttonAddPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), FormPelanggan.class);
                intent.putExtra("method", "Add");
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
//        mTableView = view.findViewById(R.id.tableview_data);
//        mTableViewDetails = view.findViewById(R.id.tableview_details);
//
//        initializeTableView();
//        initializeTableViewDetails();
//
//        if (mPaginationEnabled) {
//            // Create an instance for the TableView pagination and pass the created TableView.
//            mPagination = new Pagination(mTableView);
//
//            // Sets the pagination listener of the TableView pagination to handle
//            // pagination actions. See onTableViewPageTurnedListener variable declaration below.
//            mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
//        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
    }

    private void getList() {
        String url = getString(R.string.host_get_customer);

        try {
            JSONObject jsonBody = new JSONObject();
            jsonBody.put("page", 1);

            final String mRequestBody = jsonBody.toString();
            System.out.println("DATA JSON : " + mRequestBody);

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    message = response;

                    // Let's get TableView
                    mTableView = getView().findViewById(R.id.tableview_data);
//                    mTableViewDetails = findViewById(R.id.tableview_details);

                    initializeTableView();
//                    initializeTableViewDetails();

                    if (mPaginationEnabled) {
                        // Create an instance for the TableView pagination and pass the created TableView.
                        mPagination = new Pagination(mTableView);

                        // Sets the pagination listener of the TableView pagination to handle
                        // pagination actions. See onTableViewPageTurnedListener variable declaration below.
                        mPagination.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();

                    String access_token = sharedPreferences.getString("token", "");

                    headers.put("Authorization", "Bearer " + access_token);
                    headers.put("Content-Type", "application/json; charset=utf-8");

                    return headers;
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    return mRequestBody == null ? null : mRequestBody.getBytes(StandardCharsets.UTF_8);
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        String responseData = new String(response.data, StandardCharsets.UTF_8);

                        responseString = responseData;
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            // Add the request to the RequestQueue.
            mRequestQueue.add(stringRequest);
            System.out.println("RESPONSE API >>> " + stringRequest.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getDetailList() {
        String url = getString(R.string.host_receipt_summary);

        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("LOG_VOLLEY", response);

                    messageDetail = response;

                    // Let's get TableView
//                    mTableView = findViewById(R.id.tableview_data);
                    mTableViewDetails = getView().findViewById(R.id.tableview_details);

//                    initializeTableView();
                    initializeTableViewDetails();

//                    if (mPaginationEnabled) {
//                        // Create an instance for the TableView pagination and pass the created TableView.
//                        mPaginationDetail = new Pagination(mTableViewDetails);
//
//                        // Sets the pagination listener of the TableView pagination to handle
//                        // pagination actions. See onTableViewPageTurnedListener variable declaration below.
//                        mPaginationDetail.setOnTableViewPageTurnedListener(onTableViewPageTurnedListener);
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("LOG_VOLLEY", error.toString());
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap headers = new HashMap();

                    String access_token = sharedPreferences.getString("token", "");

                    headers.put("Authorization", "Bearer " + access_token);

                    return headers;
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        String responseData = new String(response.data, StandardCharsets.UTF_8);

                        responseString = responseData;
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };

            // Add the request to the RequestQueue.
            mRequestQueue.add(stringRequest);
            System.out.println("RESPONSE API >>> " + stringRequest.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initializeTableView() {
        // Create TableView View model class  to group view models of TableView
        TableViewModel tableViewModel = new TableViewModel();

        // Create TableView Adapter
        TableViewAdapter tableViewAdapter = new TableViewAdapter(tableViewModel, PelangganFragment.this.mContext);
        System.out.println("DATA FRAGMENT CONTEXT : " + PelangganFragment.this.getActivity());
        System.out.println("DATA FRAGMENT CONTEXT : " + PelangganFragment.this.getContext());
        System.out.println("DATA FRAGMENT CONTEXT : " + PelangganFragment.this.requireContext());
        System.out.println("DATA FRAGMENT CONTEXT : " + PelangganFragment.this.requireActivity());

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