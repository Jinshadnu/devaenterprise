package com.vingcoz.devaenterprise.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;

public class UtilManager {

    ProgressDialog progressDialog;
    boolean SendValue;
    private Dialog dialog_changed;
    private boolean ReturnValue = false;
    private Context mCtx;

    public UtilManager(Context context) {
        mCtx = context;
        progressDialog = new ProgressDialog(mCtx);
    }

    public void ShowToast(String strMessage) {
        Toast.makeText(mCtx, strMessage, Toast.LENGTH_SHORT).show();
    }

    public void showSuccess(String strMessage) {
        Toasty.success(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void showError(String strMessage) {
        Toasty.error(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void showInfo(String strMessage) {
        Toasty.info(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void showWarning(String strMessage) {
        Toasty.warning(mCtx, strMessage, Toast.LENGTH_SHORT, true).show();
    }

    public void shareApp(String str) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, str + "\n \n Let me recommend this free online shopping application \n \n ThaiValley \n\n Download from this link ");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share ThaiValley - Online Shopping Application");
            mCtx.startActivity(Intent.createChooser(shareIntent, "Share..."));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean alertYesNo(String strTitle, String strMessage) {


        AlertDialog alertDialog = new AlertDialog.Builder(mCtx)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle(strTitle)
                .setMessage(strMessage)
                .setPositiveButton("Yes", (dialogInterface, i) -> SendValue = true)
                .setNegativeButton("No", (dialogInterface, i) -> SendValue = false)
                .show();
        return SendValue;
    }

    public void AlertOk(String strTitle, String strMessage) {

        new AlertDialog.Builder(mCtx)
                .setTitle(strTitle)
                .setMessage(strMessage)
                .setPositiveButton(android.R.string.ok, null)
                .show();
    }

    public void showProgress(String strMessage, boolean blnActive) {

        if (blnActive) {
            // Sprite doubleBounce = new DoubleBounce();
            // progressDialog.setIndeterminateDrawable(doubleBounce);
            progressDialog.setMessage(strMessage);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }


    }

    public String getAPIDateTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat(GlobalVariables.API_DATE_TIME);
        return format.format(calendar.getTime());
    }
}