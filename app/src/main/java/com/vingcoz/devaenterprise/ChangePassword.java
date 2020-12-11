package com.vingcoz.devaenterprise;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vingcoz.devaenterprise.Utils.PrefUtils.MOBILE_NUMBER;

public class ChangePassword extends AppCompatActivity {

    TextInputEditText txtOldPassword, txtPassword1, txtPassword2;
    MaterialButton btnChangePassword;
    UtilManager mUtilManager;
    PrefUtils mPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Change Password");
        }
        mUtilManager = new UtilManager(ChangePassword.this);
        mPrefUtils = new PrefUtils(ChangePassword.this);
        txtOldPassword = findViewById(R.id.txtOldPassword);
        txtPassword1 = findViewById(R.id.txtPassword1);
        txtPassword2 = findViewById(R.id.txtPassword2);
        btnChangePassword = findViewById(R.id.btnChangePassword);

        btnChangePassword.setOnClickListener(v -> {
            if (Validate()) {
                StartChanging();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    boolean Validate() {

        boolean Result = true;
        if (Objects.requireNonNull(txtOldPassword.getText()).length() < 6) {
            txtOldPassword.setError("Minimum 6 character required");
            Result = false;
        }
        if (Objects.requireNonNull(txtPassword1.getText()).length() < 6) {
            txtPassword1.setError("Minimum 6 character required");
            Result = false;
        }
        if (Objects.requireNonNull(!txtPassword1.getText().toString().equals(Objects.requireNonNull(txtPassword2.getText()).toString()))) {
            txtPassword1.setError("Password don't match");
            txtPassword2.setError("Password don't match");
            Result = false;
        }
        if (Objects.requireNonNull(txtOldPassword.getText().toString().equals(Objects.requireNonNull(txtPassword1.getText()).toString()))) {
            txtPassword1.setError("Same as old password");
            mUtilManager.showError("Old password and new password is same !!");
            Result = false;
        }
        return Result;
    }

    void StartChanging() {

        mUtilManager.showProgress("Changing password please wait", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strMobile = mPrefUtils.GetSharedString(MOBILE_NUMBER);
        String strOldPassword = Objects.requireNonNull(txtOldPassword.getText()).toString();
        String strNewPassword = Objects.requireNonNull(txtPassword1.getText()).toString();

        Call<NormalResponse> call;
        call = apiService.ChangePassword(strMobile, strOldPassword, strNewPassword);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {

                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Password changed successfully");
                    onSupportNavigateUp();
                } else {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NormalResponse> call, @NonNull Throwable t) {
                mUtilManager.showProgress("", false);
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }
}
