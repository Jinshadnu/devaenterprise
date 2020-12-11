package com.vingcoz.devaenterprise.Utils.common;

import android.content.Context;
import android.widget.Toast;

public class RemoveFromCart {

    private Context mCtx;

    public RemoveFromCart(Context context) {
        mCtx = context;
    }

    public void ShowToast(String strMessage) {//todo api remove cart
        Toast.makeText(mCtx, strMessage, Toast.LENGTH_SHORT).show();
    }
}