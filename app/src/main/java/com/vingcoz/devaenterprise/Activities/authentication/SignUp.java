package com.vingcoz.devaenterprise.Activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUp extends AppCompatActivity {

    TextInputEditText txtName, txtPhone, txtEmail, txtAddress, txtPassword1, txtPassword2, txtPlace, txtPin;
    MaterialButton btnSignUp;
    TextView txtLogin;
    UtilManager mUtilManager;
    String strPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Sign Up");
        }

        mUtilManager = new UtilManager(SignUp.this);
        mUtilManager = new UtilManager(SignUp.this);
        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        txtPlace = findViewById(R.id.txtPlace);
        txtPin = findViewById(R.id.txtPin);

        txtPassword1 = findViewById(R.id.txtPassword1);
        txtPassword2 = findViewById(R.id.txtPassword2);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtLogin = findViewById(R.id.lnkSignIn);

        btnSignUp.setOnClickListener(v -> {
            if (Validate()) {
                SubmitSignUp();
            }
        });

        txtLogin.setOnClickListener(v -> {
            finish();
        });

    }

    boolean Validate() {

        boolean Result = true;
        if (Objects.requireNonNull(txtName.getText()).length() < 3) {
            txtName.setError("Invalid name");
            Result = false;
        }
        if (Objects.requireNonNull(txtPhone.getText()).toString().trim().length() != 10) {
            txtPhone.setError("Invalid Phone Number");
            Result = false;
        } else {
            strPhone = txtPhone.getText().toString().trim();
        }
        if (Objects.requireNonNull(txtEmail.getText()).length() < 7) {
            txtEmail.setError("Invalid Email");
            Result = false;
        }

        if (Objects.requireNonNull(txtAddress.getText()).length() < 6) {
            txtAddress.setError("Invalid Addess");
            Result = false;
        }

        if (Objects.requireNonNull(txtPin.getText()).length() < 6) {
            txtPin.setError("Invalid Pin code");
            Result = false;
        }

        if (Objects.requireNonNull(txtPlace.getText()).length() < 3) {
            txtPlace.setError("Invalid place");
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
        return Result;
    }

    void SubmitSignUp() {

        mUtilManager.showProgress("Creating new account, please wait..", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strName = Objects.requireNonNull(txtName.getText()).toString();
        String strMobile = Objects.requireNonNull(txtPhone.getText()).toString();
        String strEmail = Objects.requireNonNull(txtEmail.getText()).toString();
        String strAddress = Objects.requireNonNull(txtAddress.getText()).toString();
        String strPassword = Objects.requireNonNull(txtPassword1.getText()).toString();
        String strPin = Objects.requireNonNull(txtPin.getText()).toString();
        String strPlace = Objects.requireNonNull(txtPlace.getText()).toString();
        Call<NormalResponse> call;
        call = apiService.CreateAccount(strName, strMobile, strEmail, strPassword, strAddress, strPlace, strPin);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Account created successfully");
                    startActivity(new Intent(SignUp.this, Login.class));
                    finish();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
