package com.vingcoz.devaenterprise.Activities.common;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.vingcoz.devaenterprise.ChangePassword;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.PrefUtils;

import static com.vingcoz.devaenterprise.Utils.PrefUtils.ADDRESS;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.EMAIL;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginName;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.MOBILE_NUMBER;

public class ProfileActivity extends AppCompatActivity {

    TextView txtName, txtPhone, txtAddress, txtEmail;
    CardView crdChangePassword;
    PrefUtils mPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Profile");
        }
        mPrefUtils = new PrefUtils(ProfileActivity.this);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        txtEmail = findViewById(R.id.txtEmail);

        txtName.setText("   " + mPrefUtils.GetSharedString(LoginName));
        txtPhone.setText("   " + mPrefUtils.GetSharedString(MOBILE_NUMBER));
        txtAddress.setText(" " +
                mPrefUtils.GetSharedString(ADDRESS));
        txtEmail.setText("   " + mPrefUtils.GetSharedString(EMAIL));

        crdChangePassword = findViewById(R.id.crdChangePassword);
        crdChangePassword.setOnClickListener(v -> {

            startActivity(new Intent(ProfileActivity.this, ChangePassword.class));
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
