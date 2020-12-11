package com.vingcoz.devaenterprise.Utils.common;

import android.content.Context;
import android.widget.Toast;

public class RemoveFromWish {

    private Context mCtx;

    public RemoveFromWish(Context context) {
        mCtx = context;
    }

    public void ShowToast(String strMessage) {//todo api remove cart
        Toast.makeText(mCtx, strMessage, Toast.LENGTH_SHORT).show();
    }
}
