package com.example.android_pda_system.CustomControl.CustomDialog.Dlg_MessageBox_YN;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class Dlg_MessageBox {

    Context _context;
    // public Dlg_IF_MessageBox _Dlg_IF_Select_YN;

    public void Dlg_Select_YN(final Dlg_IF_MessageBox IF_Select, Context context, String Title, String Message, String Yes, String No) {
        this._context = context;

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        //화면밖터치시 닫히지 않도록
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton(Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        IF_Select.onClicked(true);
                    }
                });
        builder.setNegativeButton(No,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        IF_Select.onClicked(false);
                    }
                });
        builder.show();
    }

    public void Dlg_Select(final Dlg_IF_MessageBox IF_Select, Context context, String Title, String Message, String Yes) {
        this._context = context;

        AlertDialog.Builder builder = new AlertDialog.Builder(_context);
        //todo 화면밖터치시 닫히지 않도록
        builder.setCancelable(false);
        builder.setTitle(Title);
        builder.setMessage(Message);
        builder.setPositiveButton(Yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        IF_Select.onClicked(true);
                    }
                });

        builder.show();
    }


}
