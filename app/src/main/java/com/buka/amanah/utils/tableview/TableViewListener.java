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

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.buka.amanah.R;
import com.buka.amanah.utils.action_menu.MenuPojo;
import com.evrencoskun.tableview.TableView;
import com.evrencoskun.tableview.listener.ITableViewListener;

import java.util.ArrayList;

//import dev.shreyaspatil.MaterialDialog.interfaces.DialogInterface;


/**
 * Created by evrencoskun on 21/09/2017.
 */

public class TableViewListener implements ITableViewListener {
    private static final int VIEW_CELL_TYPE = 6;
    private static final int EDIT_CELL_TYPE = 7;
    private static final int DEL_CELL_TYPE = 8;
    // create a arraylist of the type NumbersView
    final ArrayList<MenuPojo> arrayList = new ArrayList<MenuPojo>();
    @NonNull
    private final Context mContext;
    @NonNull
    private final TableView mTableView;
    String[] listItems = {"Lihat Transaksi", "Edit Transaksi", "Hapus Transaksi"};
    Context context;


    public TableViewListener(@NonNull TableView tableView, Context context) {
        this.mContext = tableView.getContext();
        this.mTableView = tableView;
        this.context = context;
    }

    /**
     * Called when user click any cell item.
     *
     * @param cellView : Clicked Cell ViewHolder.
     * @param column   : X (Column) position of Clicked Cell item.
     * @param row      : Y (Row) position of Clicked Cell item.
     */
    @Override
    public void onCellClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {

        arrayList.add(new MenuPojo(R.drawable.ic_baseline_view_24, "Lihat Transaksi"));
        arrayList.add(new MenuPojo(R.drawable.ic_baseline_edit_24, "Edit Transaksi"));
        arrayList.add(new MenuPojo(R.drawable.ic_baseline_delete_24, "Hapus Transaksi"));

        // Do what you want.
//        if (column == VIEW_CELL_TYPE) {
////            showToast("Cell " + column + " " + row + " has been clicked.");
////            mContext.startActivity(new Intent(context, AddPenerimaan.class));
//
//
////        } else if (column == EDIT_CELL_TYPE) {
//////            showToast("Cell " + column + " " + row + " has been clicked.");
////            mContext.startActivity(new Intent(context, AddPenerimaan.class));
////        } else if (column == DEL_CELL_TYPE) {
//////            showToast("Cell " + column + " " + row + " has been clicked.");
////            MaterialDialog mDialog = new MaterialDialog.Builder((Activity) mContext)
////                    .setTitle("Confirmation", TextAlignment.CENTER)
////                    .setMessage("Apa anda yakin untuk menghapus Data Penerimaan ini?")
////                    .setAnimation("542-warning-sign.json")
////                    .setPositiveButton("Yes", R.drawable.ic_baseline_delete_24, new MaterialDialog.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int which) {
////                            dialogInterface.dismiss();
////
////                        }
////                    })
////                    .setNegativeButton("No", new MaterialDialog.OnClickListener() {
////                        @Override
////                        public void onClick(DialogInterface dialogInterface, int which) {
////                            dialogInterface.dismiss();
////                        }
////                    })
////                    .build();
////
////            // Show Dialog
////            mDialog.show();
////            mContext.startActivity(new Intent(context, AddPenerimaan.class));
//        }
    }

    /**
     * Called when user double click any cell item.
     *
     * @param cellView : Clicked Cell ViewHolder.
     * @param column   : X (Column) position of Clicked Cell item.
     * @param row      : Y (Row) position of Clicked Cell item.
     */
    @Override
    public void onCellDoubleClicked(@NonNull RecyclerView.ViewHolder cellView, int column, int row) {
        // Do what you want.
//        showToast("Cell " + column + " " + row + " has been double clicked.");
    }

    /**
     * Called when user long press any cell item.
     *
     * @param cellView : Long Pressed Cell ViewHolder.
     * @param column   : X (Column) position of Long Pressed Cell item.
     * @param row      : Y (Row) position of Long Pressed Cell item.
     */
    @Override
    public void onCellLongPressed(@NonNull RecyclerView.ViewHolder cellView, final int column,
                                  int row) {
        // Do What you want
//        showToast("Cell " + column + " " + row + " has been long pressed.");
    }

    /**
     * Called when user click any column header item.
     *
     * @param columnHeaderView : Clicked Column Header ViewHolder.
     * @param column           : X (Column) position of Clicked Column Header item.
     */
    @Override
    public void onColumnHeaderClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {
        // Do what you want.
//        showToast("Column header  " + column + " has been clicked.");
    }

    /**
     * Called when user double click any column header item.
     *
     * @param columnHeaderView : Clicked Column Header ViewHolder.
     * @param column           : X (Column) position of Clicked Column Header item.
     */
    @Override
    public void onColumnHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder columnHeaderView, int column) {
        // Do what you want.
//        showToast("Column header  " + column + " has been double clicked.");
    }

    /**
     * Called when user long press any column header item.
     *
     * @param columnHeaderView : Long Pressed Column Header ViewHolder.
     * @param column           : X (Column) position of Long Pressed Column Header item.
     */
    @Override
    public void onColumnHeaderLongPressed(@NonNull RecyclerView.ViewHolder columnHeaderView, int
            column) {

//        if (columnHeaderView instanceof ColumnHeaderViewHolder) {
//            // Create Long Press Popup
//            ColumnHeaderLongPressPopup popup = new ColumnHeaderLongPressPopup(
//                    (ColumnHeaderViewHolder) columnHeaderView, mTableView);
//            // Show
//            popup.show();
//        }
    }

    /**
     * Called when user click any Row Header item.
     *
     * @param rowHeaderView : Clicked Row Header ViewHolder.
     * @param row           : Y (Row) position of Clicked Row Header item.
     */
    @Override
    public void onRowHeaderClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        // Do whatever you want.
//        showToast("Row header " + row + " has been clicked.");
    }

    /**
     * Called when user double click any Row Header item.
     *
     * @param rowHeaderView : Clicked Row Header ViewHolder.
     * @param row           : Y (Row) position of Clicked Row Header item.
     */
    @Override
    public void onRowHeaderDoubleClicked(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
        // Do whatever you want.
//        showToast("Row header " + row + " has been double clicked.");
    }

    /**
     * Called when user long press any row header item.
     *
     * @param rowHeaderView : Long Pressed Row Header ViewHolder.
     * @param row           : Y (Row) position of Long Pressed Row Header item.
     */
    @Override
    public void onRowHeaderLongPressed(@NonNull RecyclerView.ViewHolder rowHeaderView, int row) {
//
//        // Create Long Press Popup
//        RowHeaderLongPressPopup popup = new RowHeaderLongPressPopup(rowHeaderView, mTableView);
//        // Show
//        popup.show();
    }


    private void showToast(String p_strMessage) {
        Toast.makeText(mContext, p_strMessage, Toast.LENGTH_SHORT).show();
    }

}
