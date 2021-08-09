/*
 * MIT License
 *
 * Copyright (c) 2021 Evren Coşkun
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.buka.amanah.utils.tableview;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

import com.buka.amanah.R;
import com.buka.amanah.pojo.customer_get.CustomerView;
import com.buka.amanah.pojo.disbursement_get.DisbursementView;
import com.buka.amanah.pojo.receipt_get.ReceiptView;
import com.buka.amanah.pojo.receipt_summary.ReceiptSummary;
import com.buka.amanah.pojo.stok_get.StokView;
import com.buka.amanah.utils.tableview.model.Cell;
import com.buka.amanah.utils.tableview.model.ColumnHeader;
import com.buka.amanah.utils.tableview.model.RowHeader;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by evrencoskun on 4.02.2018.
 */

public class TableViewModel {

    public static final int MENU = 1;
    public static final int VIEW = 1;
    public static final int EDIT = 2;
    public static final int DEL = 3;


    public static final int PENERIMAAN_COLUMN_INDEX = 6;
    public static final int PENGELUARAN_COLUMN_INDEX = 4;
    public static final int HUTANG_USAHA_COLUMN_INDEX = 5;
    public static final int HUTANG_GALON_COLUMN_INDEX = 5;
    public static final int PIUTANG_USAHA_COLUMN_INDEX = 6;
    public static final int PIUTANG_GALON_COLUMN_INDEX = 5;
    public static final int PELANGGAN_COLUMN_INDEX = 6;
    public static final int STOCK_COLUMN_INDEX = 3;

    //    public static final int VIEW_COLUMN_INDEX = 6;
//    public static final int EDIT_COLUMN_INDEX = 7;
//    public static final int DEL_COLUMN_INDEX = 8;
    // Constant size for dummy data sets
//    private static final int COLUMN_SIZE_PENERIMAAN = 7;
//    private static final int COLUMN_SIZE_PENGELUARAN = 6;
//    private static final int COLUMN_SIZE_DETAIL_PENERIMAAN = 3;
    private static final int ROW_SIZE = 500;
    private static final int ROW_SIZE_DETAIL = 500;
//    private static final int ROW_SIZE_DET = 6;
    //    @DrawableRes
//    private final int mViewDrawable;
//    @DrawableRes
//    private final int mEditDrawable;
//    @DrawableRes
//    private final int mDeleteDrawable;
    @DrawableRes
    private final int mMenuDrawable;
    int COLUMN_SIZE, COLUMN_SIZE_DETAIL, ROW_SIZE_DETAILS;

    ReceiptView receiptGet = new ReceiptView();
    DisbursementView disbursementGet = new DisbursementView();
    CustomerView customerView = new CustomerView();
    ReceiptSummary receiptSummary = new ReceiptSummary();
    StokView stokView = new StokView();

    public TableViewModel() {
        // initialize drawables
//        mViewDrawable = R.drawable.ic_baseline_view_24;
//        mEditDrawable = R.drawable.ic_baseline_edit_24;
//        mDeleteDrawable = R.drawable.ic_baseline_delete_24;
        mMenuDrawable = R.drawable.ic_baseline_more_vert_24;
    }

    // Row Number
    @NonNull
    public List<RowHeader> getSimpleRowHeaderList() {
        List<RowHeader> list = new ArrayList<>();

        for (int i = 1; i < ROW_SIZE; i++) {
            RowHeader header = new RowHeader(String.valueOf(i), "" + i);
            list.add(header);
        }

        return list;
    }

    @NonNull
    public List<RowHeader> getDetailRowHeaderList(String category) {
        List<RowHeader> list = new ArrayList<>();
        int ROW_SIZE_DET = 6;

        if (category.equalsIgnoreCase("Penerimaan")) {
            for (int i = 1; i <= ROW_SIZE_DET; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
            for (int i = 1; i <= ROW_SIZE_DET; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Galon")) {
            for (int i = 1; i <= ROW_SIZE_DET; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
            for (int i = 1; i <= ROW_SIZE_DET; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Galon")) {
            for (int i = 1; i <= ROW_SIZE_DET; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Pelanggan")) {
            for (int i = 1; i <= ROW_SIZE_DETAIL; i++) {
                RowHeader header = new RowHeader(String.valueOf(i), "" + i);
                list.add(header);
            }
        }

        return list;
    }


    // Column Header

    /**
     * This is a dummy model list test some cases.
     */
    @NonNull
    public List<ColumnHeader> getColumnHeaderList(String category) {
        List<ColumnHeader> list = new ArrayList<>();

        if (category.equalsIgnoreCase("Penerimaan")) {
            COLUMN_SIZE = 7;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Pelanggan";
                        break;
                    case 3:
                        title = "Penerimaan";
                        break;
                    case 4:
                        title = "Jumlah";
                        break;
                    case 5:
                        title = "Harga";
                        break;
                    case 6:
                        title = "Total Rupiah";
                        break;
                    case 7:
                        title = "Aksi";
                        break;
//                    case 8:
//                        title = "Edit";
//                        break;
//                    case 9:
//                        title = "Delete";
//                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Pengeluaran")) {
            COLUMN_SIZE = 5;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Biaya";
                        break;
                    case 3:
                        title = "Detail Biaya";
                        break;
                    case 4:
                        title = "Total Rupiah";
                        break;
                    case 5:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
            COLUMN_SIZE = 6;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Perusahaan / Pelanggan";
                        break;
                    case 3:
                        title = "Hutang";
                        break;
                    case 4:
                        title = "Detail Harga";
                        break;
                    case 5:
                        title = "Total Rupiah";
                        break;
                    case 6:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Galon")) {
            COLUMN_SIZE = 6;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Perusahaan / Pelanggan";
                        break;
                    case 3:
                        title = "Jumlah Galon";
                        break;
                    case 4:
                        title = "Harga";
                        break;
                    case 5:
                        title = "Total Rupiah";
                        break;
                    case 6:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
            COLUMN_SIZE = 7;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Perusahaan / Pelanggan";
                        break;
                    case 3:
                        title = "Transaksi";
                        break;
                    case 4:
                        title = "Jumlah";
                        break;
                    case 5:
                        title = "Harga";
                        break;
                    case 6:
                        title = "Total Rupiah";
                        break;
                    case 7:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Galon")) {
            COLUMN_SIZE = 6;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Perusahaan / Pelanggan";
                        break;
                    case 3:
                        title = "Jumlah Galon";
                        break;
                    case 4:
                        title = "Harga";
                        break;
                    case 5:
                        title = "Total Rupiah";
                        break;
                    case 6:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Pelanggan")) {
            COLUMN_SIZE = 7;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Hari, Tanggal";
                        break;
                    case 2:
                        title = "Pelanggan";
                        break;
                    case 3:
                        title = "Handphone";
                        break;
                    case 4:
                        title = "Whatsapp";
                        break;
                    case 5:
                        title = "Alamat";
                        break;
                    case 6:
                        title = "Lihat Transaksi";
                        break;
                    case 7:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Stok")) {
            COLUMN_SIZE = 4;
            for (int i = 1; i <= COLUMN_SIZE; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Nama Barang";
                        break;
                    case 2:
                        title = "Jumlah Barang";
                        break;
                    case 3:
                        title = "Harga";
                        break;
                    case 4:
                        title = "Aksi";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }

    @NonNull
    public List<ColumnHeader> getDetailColumnHeaderList(String category) {
        List<ColumnHeader> list = new ArrayList<>();

        if (category.equalsIgnoreCase("Penerimaan")) {
            COLUMN_SIZE_DETAIL = 3;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Penerimaan";
                        break;
                    case 2:
                        title = "Jumlah";
                        break;
                    case 3:
                        title = "Total Rupiah";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Total Hutang Usaha";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Hutang Galon")) {
            COLUMN_SIZE_DETAIL = 3;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Total Hutang Galon";
                        break;
                    case 2:
                        title = "Harga";
                        break;
                    case 3:
                        title = "Total Rupiah";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Total Piutang Usaha";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Piutang Galon")) {
            COLUMN_SIZE_DETAIL = 3;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Total Piutang Galon";
                        break;
                    case 2:
                        title = "Harga";
                        break;
                    case 3:
                        title = "Total Rupiah";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        } else if (category.equalsIgnoreCase("Pelanggan")) {
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 1; i <= COLUMN_SIZE_DETAIL; i++) {
                String title = null;

                switch (i) {
                    case 1:
                        title = "Total Pelanggan";
                        break;
                }

                ColumnHeader header = new ColumnHeader(String.valueOf(i), title);
                list.add(header);
            }
        }

        return list;
    }


    // Cell Data

    /**
     * This is a dummy model list test some cases.
     */
//    @NonNull
//    public List<List<Cell>> getCellList(String category, String response) {
//        List<List<Cell>> list = new ArrayList<>();
//
//        if (category.equalsIgnoreCase("Penerimaan")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                COLUMN_SIZE = 7;
//                for (int j = 0; j < COLUMN_SIZE; j++) {
//                    Cell cell;
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    if (j == 0) {
//                        cell = new Cell(id, receiptGet.getData()[i].getCreatedAt());
//                    } else if (j == 1) {
//                        cell = new Cell(id, receiptGet.getData()[i].getCustomerName());
//                    } else if (j == 2) {
//                        cell = new Cell(id, receiptGet.getData()[i].getType());
//                    } else if (j == 3) {
//                        cell = new Cell(id, receiptGet.getData()[i].getAmount());
//                    } else if (j == 4) {
//                        cell = new Cell(id, "Rp " + receiptGet.getData()[i].getPrice());
//                    } else if (j == 5) {
//                        cell = new Cell(id, "Rp " + receiptGet.getData()[i].getTotal());
//                    } else {
//                        cell = new Cell(id, receiptGet.getData()[i].getId());
//                    }
//
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//
//            }
//        } else if (category.equalsIgnoreCase("Pengeluaran")) {
//            Gson gson = new Gson();
//            disbursementGet = gson.fromJson(response, DisbursementView.class);
//
//            for (int i = 0; i < disbursementGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                COLUMN_SIZE = 5;
//                for (int j = 0; j < COLUMN_SIZE; j++) {
//                    Cell cell;
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    if (j == 0) {
//                        cell = new Cell(id, disbursementGet.getData()[i].getCreatedAt());
//                    } else if (j == 1) {
//                        cell = new Cell(id, disbursementGet.getData()[i].getType());
//                    } else if (j == 2) {
//                        cell = new Cell(id, disbursementGet.getData()[i].getCategory());
//                    } else if (j == 3) {
//                        cell = new Cell(id, "Rp " + disbursementGet.getData()[i].getTotal());
//                    } else {
//                        cell = new Cell(id, disbursementGet.getData()[i].getId());
//                    }
//
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Hutang Usaha")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Hutang Galon")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Hutang Galon")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Piutang Usaha")) {
//                    COLUMN_SIZE = 7;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Piutang Galon")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Piutang Galon")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Pelanggan")) {
//            Gson gson = new Gson();
//            customerView = gson.fromJson(response, CustomerView.class);
//
//            for (int i = 0; i < customerView.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                COLUMN_SIZE = 7;
//                for (int j = 0; j < COLUMN_SIZE; j++) {
//                    Cell cell;
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    if (j == 0) {
//                        cell = new Cell(id, customerView.getData()[i].getCreated_at());
//                    } else if (j == 1) {
//                        cell = new Cell(id, customerView.getData()[i].getName());
//                    } else if (j == 2) {
//                        cell = new Cell(id, customerView.getData()[i].getPhone());
//                    } else if (j == 3) {
//                        cell = new Cell(id, customerView.getData()[i].getWa());
//                    } else if (j == 4) {
//                        cell = new Cell(id, customerView.getData()[i].getAddress());
//                    } else if (j == 5) {
//                        cell = new Cell(id, "Lihat Transaksi");
//                    } else {
//                        cell = new Cell(id, customerView.getData()[i].getId());
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//
//            }
//        } else if (category.equalsIgnoreCase("Stok")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Stok")) {
//                    COLUMN_SIZE = 4;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        }
//
//        return list;
//    }

    @NonNull
    public List<List<Cell>> getCellList(String category, String response) {
        List<List<Cell>> list = new ArrayList<>();

        if (category.equalsIgnoreCase("Penerimaan")) {
            Gson gson = new Gson();
            receiptGet = gson.fromJson(response, ReceiptView.class);

            for (int i = 0; i < receiptGet.getData().length; i++) {
                List<Cell> cellList = new ArrayList<>();
                COLUMN_SIZE = 7;
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Cell cell;

                    // Create dummy id.
                    String id = j + "-" + i;

                    if (j == 0) {
                        cell = new Cell(id, receiptGet.getData()[i].getCreatedAt());
                    } else if (j == 1) {
                        cell = new Cell(id, receiptGet.getData()[i].getCustomerName());
                    } else if (j == 2) {
                        cell = new Cell(id, receiptGet.getData()[i].getType());
                    } else if (j == 3) {
                        cell = new Cell(id, receiptGet.getData()[i].getAmount());
                    } else if (j == 4) {
                        cell = new Cell(id, "Rp " + receiptGet.getData()[i].getPrice());
                    } else if (j == 5) {
                        cell = new Cell(id, "Rp " + receiptGet.getData()[i].getTotal());
                    } else {
                        cell = new Cell(id, receiptGet.getData()[i].getId());
                    }

                    cellList.add(cell);
                }
                list.add(cellList);

            }
        } else if (category.equalsIgnoreCase("Pengeluaran")) {
            Gson gson = new Gson();
            disbursementGet = gson.fromJson(response, DisbursementView.class);

            for (int i = 0; i < disbursementGet.getData().length; i++) {
                List<Cell> cellList = new ArrayList<>();
                COLUMN_SIZE = 5;
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Cell cell;

                    // Create dummy id.
                    String id = j + "-" + i;

                    if (j == 0) {
                        cell = new Cell(id, disbursementGet.getData()[i].getCreatedAt());
                    } else if (j == 1) {
                        cell = new Cell(id, disbursementGet.getData()[i].getType());
                    } else if (j == 2) {
                        cell = new Cell(id, disbursementGet.getData()[i].getCategory());
                    } else if (j == 3) {
                        cell = new Cell(id, "Rp " + disbursementGet.getData()[i].getTotal());
                    } else {
                        cell = new Cell(id, disbursementGet.getData()[i].getId());
                    }

                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Pelanggan")) {
            Gson gson = new Gson();
            customerView = gson.fromJson(response, CustomerView.class);

            for (int i = 0; i < customerView.getData().length; i++) {
                List<Cell> cellList = new ArrayList<>();
                COLUMN_SIZE = 7;
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Cell cell;

                    // Create dummy id.
                    String id = j + "-" + i;

                    if (j == 0) {
                        cell = new Cell(id, customerView.getData()[i].getCreatedAt());
                    } else if (j == 1) {
                        cell = new Cell(id, customerView.getData()[i].getName());
                    } else if (j == 2) {
                        cell = new Cell(id, customerView.getData()[i].getPhone());
                    } else if (j == 3) {
                        cell = new Cell(id, customerView.getData()[i].getWa());
                    } else if (j == 4) {
                        cell = new Cell(id, customerView.getData()[i].getAddress());
                    } else if (j == 5) {
                        cell = new Cell(id, null);
                    } else {
                        cell = new Cell(id, customerView.getData()[i].getId());
                    }

                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Stok")) {
            Gson gson = new Gson();
            stokView = gson.fromJson(response, StokView.class);

            for (int i = 0; i < stokView.getData().length; i++) {
                List<Cell> cellList = new ArrayList<>();
                COLUMN_SIZE = 4;
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Cell cell;

                    // Create dummy id.
                    String id = j + "-" + i;

                    if (j == 0) {
                        cell = new Cell(id, stokView.getData()[i].getProductName());
                    } else if (j == 1) {
                        cell = new Cell(id, stokView.getData()[i].getStock());
                    } else if (j == 2) {
                        cell = new Cell(id, "Rp " + stokView.getData()[i].getPrice());
                    } else {
                        cell = new Cell(id, stokView.getData()[i].getId());
                    }

                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else {
            for (int i = 0; i < ROW_SIZE; i++) {
                List<Cell> cellList = new ArrayList<>();

                if (category.equalsIgnoreCase("Hutang Usaha")) {
                    COLUMN_SIZE = 6;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                } else if (category.equalsIgnoreCase("Hutang Galon")) {
                    COLUMN_SIZE = 6;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                } else if (category.equalsIgnoreCase("Piutang Usaha")) {
                    COLUMN_SIZE = 7;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                } else if (category.equalsIgnoreCase("Piutang Galon")) {
                    COLUMN_SIZE = 6;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                } else if (category.equalsIgnoreCase("Pelanggan")) {
                    COLUMN_SIZE = 7;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                } else if (category.equalsIgnoreCase("Stok")) {
                    COLUMN_SIZE = 4;
                    for (int j = 0; j < COLUMN_SIZE; j++) {
                        Object text = "cell " + j + " " + i;

                        final int random = new Random().nextInt();
                        if (j == 0) {
                            text = i;
                        } else if (j == 1) {
                            text = random;
                        }

                        // Create dummy id.
                        String id = j + "-" + i;

                        Cell cell;
                        if (j == 3) {
                            cell = new Cell(id, text);
                        } else if (j == 4) {
                            // NOTE female and male keywords for filter will have conflict since "female"
                            // contains "male"
                            cell = new Cell(id, text);
                        } else {
                            cell = new Cell(id, text);
                        }
                        cellList.add(cell);
                    }
                }
                list.add(cellList);
            }
        }
        return list;
    }

    @NonNull
    public List<List<Cell>> getDetailCellList(String category, String response) {
        List<List<Cell>> list = new ArrayList<>();

//        if (category.equalsIgnoreCase("Penerimaan")) {
//            ROW_SIZE_DETAIL = 3;
//            COLUMN_SIZE_DETAIL = 3;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
//            ROW_SIZE_DETAIL = 1;
//            COLUMN_SIZE_DETAIL = 1;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Hutang Galon")) {
//            ROW_SIZE_DETAIL = 1;
//            COLUMN_SIZE_DETAIL = 3;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
//            ROW_SIZE_DETAIL = 1;
//            COLUMN_SIZE_DETAIL = 1;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Piutang Galon")) {
//            ROW_SIZE_DETAIL = 1;
//            COLUMN_SIZE_DETAIL = 3;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        } else if (category.equalsIgnoreCase("Pelanggan")) {
//            ROW_SIZE_DETAIL = 1;
//            COLUMN_SIZE_DETAIL = 1;
//            for (int i = 0; i < ROW_SIZE_DETAIL; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
//                    Object text = "cell " + j + " " + i;
//
//                    final int random = new Random().nextInt();
//                    if (j == 0) {
//                        text = i;
//                    } else if (j == 1) {
//                        text = random;
//                    }
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    Cell cell;
//                    if (j == 3) {
//                        cell = new Cell(id, text);
//                    } else if (j == 4) {
//                        // NOTE female and male keywords for filter will have conflict since "female"
//                        // contains "male"
//                        cell = new Cell(id, text);
//                    } else {
//                        cell = new Cell(id, text);
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//            }
//        }

        if (category.equalsIgnoreCase("Penerimaan")) {
            Gson gson = new Gson();
            receiptSummary = gson.fromJson(response, ReceiptSummary.class);

            for (int i = 0; i < receiptSummary.getData().length; i++) {
                List<Cell> cellList = new ArrayList<>();
                COLUMN_SIZE = 3;
                for (int j = 0; j < COLUMN_SIZE; j++) {
                    Cell cell;

                    // Create dummy id.
                    String id = j + "-" + i;

                    if (j == 0) {
                        cell = new Cell(id, receiptSummary.getData()[i].getName());
                    } else if (j == 1) {
                        cell = new Cell(id, receiptSummary.getData()[i].getCount());
                    } else {
                        cell = new Cell(id, receiptSummary.getData()[i].getTotal());
                    }

                    cellList.add(cell);
                }
                list.add(cellList);

            }
        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
            ROW_SIZE_DETAILS = 1;
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 0; i < ROW_SIZE_DETAILS; i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
                    Object text = "cell " + j + " " + i;

                    final int random = new Random().nextInt();
                    if (j == 0) {
                        text = i;
                    } else if (j == 1) {
                        text = random;
                    }

                    // Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
                    if (j == 3) {
                        cell = new Cell(id, text);
                    } else if (j == 4) {
                        // NOTE female and male keywords for filter will have conflict since "female"
                        // contains "male"
                        cell = new Cell(id, text);
                    } else {
                        cell = new Cell(id, text);
                    }
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Hutang Galon")) {
            ROW_SIZE_DETAILS = 1;
            COLUMN_SIZE_DETAIL = 3;
            for (int i = 0; i < ROW_SIZE_DETAILS; i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
                    Object text = "cell " + j + " " + i;

                    final int random = new Random().nextInt();
                    if (j == 0) {
                        text = i;
                    } else if (j == 1) {
                        text = random;
                    }

                    // Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
                    if (j == 3) {
                        cell = new Cell(id, text);
                    } else if (j == 4) {
                        // NOTE female and male keywords for filter will have conflict since "female"
                        // contains "male"
                        cell = new Cell(id, text);
                    } else {
                        cell = new Cell(id, text);
                    }
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
            ROW_SIZE_DETAILS = 1;
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 0; i < ROW_SIZE_DETAILS; i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
                    Object text = "cell " + j + " " + i;

                    final int random = new Random().nextInt();
                    if (j == 0) {
                        text = i;
                    } else if (j == 1) {
                        text = random;
                    }

                    // Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
                    if (j == 3) {
                        cell = new Cell(id, text);
                    } else if (j == 4) {
                        // NOTE female and male keywords for filter will have conflict since "female"
                        // contains "male"
                        cell = new Cell(id, text);
                    } else {
                        cell = new Cell(id, text);
                    }
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Piutang Galon")) {
            ROW_SIZE_DETAILS = 1;
            COLUMN_SIZE_DETAIL = 3;
            for (int i = 0; i < ROW_SIZE_DETAILS; i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
                    Object text = "cell " + j + " " + i;

                    final int random = new Random().nextInt();
                    if (j == 0) {
                        text = i;
                    } else if (j == 1) {
                        text = random;
                    }

                    // Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
                    if (j == 3) {
                        cell = new Cell(id, text);
                    } else if (j == 4) {
                        // NOTE female and male keywords for filter will have conflict since "female"
                        // contains "male"
                        cell = new Cell(id, text);
                    } else {
                        cell = new Cell(id, text);
                    }
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        } else if (category.equalsIgnoreCase("Pelanggan")) {
            ROW_SIZE_DETAILS = 1;
            COLUMN_SIZE_DETAIL = 1;
            for (int i = 0; i < ROW_SIZE_DETAILS; i++) {
                List<Cell> cellList = new ArrayList<>();
                for (int j = 0; j < COLUMN_SIZE_DETAIL; j++) {
                    Object text = "cell " + j + " " + i;

                    final int random = new Random().nextInt();
                    if (j == 0) {
                        text = i;
                    } else if (j == 1) {
                        text = random;
                    }

                    // Create dummy id.
                    String id = j + "-" + i;

                    Cell cell;
                    if (j == 3) {
                        cell = new Cell(id, text);
                    } else if (j == 4) {
                        // NOTE female and male keywords for filter will have conflict since "female"
                        // contains "male"
                        cell = new Cell(id, text);
                    } else {
                        cell = new Cell(id, text);
                    }
                    cellList.add(cell);
                }
                list.add(cellList);
            }
        }

        return list;
    }
//        } else if (category.equalsIgnoreCase("Hutang Usaha")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Hutang Usaha")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Hutang Galon")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Hutang Galon")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Piutang Usaha")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Piutang Usaha")) {
//                    COLUMN_SIZE = 7;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Piutang Galon")) {
//            Gson gson = new Gson();
//            receiptGet = gson.fromJson(response, ReceiptView.class);
//
//            for (int i = 0; i < receiptGet.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//
//                if (category.equalsIgnoreCase("Piutang Galon")) {
//                    COLUMN_SIZE = 6;
//                    for (int j = 0; j < COLUMN_SIZE; j++) {
//                        Object text = "cell " + j + " " + i;
//
//                        final int random = new Random().nextInt();
//                        if (j == 0) {
//                            text = i;
//                        } else if (j == 1) {
//                            text = random;
//                        }
//
//                        // Create dummy id.
//                        String id = j + "-" + i;
//
//                        Cell cell;
//                        if (j == 3) {
//                            cell = new Cell(id, text);
//                        } else if (j == 4) {
//                            // NOTE female and male keywords for filter will have conflict since "female"
//                            // contains "male"
//                            cell = new Cell(id, text);
//                        } else {
//                            cell = new Cell(id, text);
//                        }
//                        cellList.add(cell);
//                    }
//                    list.add(cellList);
//                }
//            }
//        } else if (category.equalsIgnoreCase("Pelanggan")) {
//            Gson gson = new Gson();
//            customerView = gson.fromJson(response, CustomerView.class);
//
//            for (int i = 0; i < customerView.getData().length; i++) {
//                List<Cell> cellList = new ArrayList<>();
//                COLUMN_SIZE = 7;
//                for (int j = 0; j < COLUMN_SIZE; j++) {
//                    Cell cell;
//
//                    // Create dummy id.
//                    String id = j + "-" + i;
//
//                    if (j == 0) {
//                        cell = new Cell(id, customerView.getData()[i].getCreated_at());
//                    } else if (j == 1) {
//                        cell = new Cell(id, customerView.getData()[i].getName());
//                    } else if (j == 2) {
//                        cell = new Cell(id, customerView.getData()[i].getPhone());
//                    } else if (j == 3) {
//                        cell = new Cell(id, customerView.getData()[i].getWa());
//                    } else if (j == 4) {
//                        cell = new Cell(id, customerView.getData()[i].getAddress());
//                    } else if (j == 5) {
//                        cell = new Cell(id, "Lihat Transaksi");
//                    } else {
//                        cell = new Cell(id, customerView.getData()[i].getId());
//                    }
//                    cellList.add(cell);
//                }
//                list.add(cellList);
//
//            }
//        }
//
//        return list;
//    }

    @DrawableRes
    public int getDrawable(int value) {
        return value == MENU ? mMenuDrawable : mMenuDrawable;
    }

//    @DrawableRes
//    public int getDrawableEdit(int value) {
//        return value == EDIT ? mEditDrawable : mEditDrawable;
//    }

//    @DrawableRes
//    public int getDrawableView(int value) {
//        return value == VIEW ? mViewDrawable : mViewDrawable;
//    }

//    @DrawableRes
//    public int getDrawableDel(int value) {
//        return value == DEL ? mDeleteDrawable : mDeleteDrawable;
//    }

    // Get Cell Data
//    @NonNull
//    public List<List<Cell>> getCellList() {
//        return getCellListForSortingTest();
//    }
//
//    @NonNull
//    public List<List<Cell>> getDetailCell() {
//        return getDetailCellList();
//    }


    // Get Row Number
//    @NonNull
//    public List<RowHeader> getRowHeaderList() {
//        return getSimpleRowHeaderList();
//    }
//
//    @NonNull
//    public List<RowHeader> getDetailRowHeader() {
//        return getDetailRowHeaderList();
//    }


    // Get Column Header
//    @NonNull
//    public List<ColumnHeader> getPenerimaanColumnHeader() {
//        return getPenerimaanColumnHeaderList();
//    }
//
//    @NonNull
//    public List<ColumnHeader> getDetailPenerimaanColumnHeader() {
//        return getDetailPenerimaanColumnHeaderList();
//    }
}
