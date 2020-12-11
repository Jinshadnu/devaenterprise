package com.vingcoz.devaenterprise.Activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.vingcoz.devaenterprise.Activities.dashboards.DashBoard;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.PrefUtils;

import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoggedIn;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginId;

public class SplashScreen extends AppCompatActivity {

    PrefUtils mPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        mPrefUtils = new PrefUtils(SplashScreen.this);
        int SPLASH_TIME_OUT = 3000;
        new Handler().postDelayed(() -> {

            if (mPrefUtils.GetSharedBool(LoggedIn) && !mPrefUtils.GetSharedString(LoginId).equals("")) {
                startActivity(new Intent(SplashScreen.this, DashBoard.class));
                finish();
            } else {
                startActivity(new Intent(SplashScreen.this, Login.class));
                finish();
            }

        }, SPLASH_TIME_OUT);
    }
}