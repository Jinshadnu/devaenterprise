package com.vingcoz.devaenterprise.Activities.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.iid.FirebaseInstanceId;
import com.vingcoz.devaenterprise.Activities.dashboards.DashBoard;
import com.vingcoz.devaenterprise.ChangeLPassword;
import com.vingcoz.devaenterprise.Model.login.LoginResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vingcoz.devaenterprise.Utils.PrefUtils.ADDRESS;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.EMAIL;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoggedIn;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginId;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginName;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.MOBILE_NUMBER;

public class Login extends AppCompatActivity {

    TextInputEditText txtLoginMobile, txtLoginPassword;
    MaterialButton btnLogin;
    TextView lnkSignUp, lnkForget;
    PrefUtils mPrefUtils;
    UtilManager mUtilManager;
    String strToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mPrefUtils = new PrefUtils(Login.this);
        txtLoginMobile = findViewById(R.id.txtLoginMobile);
        txtLoginPassword = findViewById(R.id.txtLoginPassword);
        btnLogin = findViewById(R.id.btnLogin);
        lnkSignUp = findViewById(R.id.lnkSignUp);
        lnkForget = findViewById(R.id.lnkForget);
        lnkForget.setVisibility(View.GONE);

        mUtilManager = new UtilManager(Login.this);

        btnLogin.setOnClickListener(v -> {
            if (Validate()) {

                try {
                    FirebaseInstanceId.getInstance().getInstanceId()
                            .addOnCompleteListener(task -> {
                                if (!task.isSuccessful()) {
                                    strToken = "Failed";
                                } else {
                                    strToken = Objects.requireNonNull(task.getResult()).getToken();
                                }
                                StartLogin();
                            });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        lnkSignUp.setOnClickListener(v -> {

            startActivity(new Intent(Login.this, SignUp.class));
        });
        lnkForget.setOnClickListener(v -> {

            startActivity(new Intent(Login.this, ChangeLPassword.class));
        });

    }

    boolean Validate() {

        boolean Result = true;
        if (Objects.requireNonNull(txtLoginMobile.getText()).length() != 10) {
            txtLoginMobile.setError("Invalid Phone Number");
            Result = false;
        }
        if (Objects.requireNonNull(txtLoginPassword.getText()).length() < 6) {
            txtLoginPassword.setError("Minimum 6 character required");
            Result = false;
        }
        return Result;
    }

    void StartLogin() {

        mUtilManager.showProgress("Authenticating, Please wait..", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strMobile = Objects.requireNonNull(txtLoginMobile.getText()).toString();
        String strPassword = Objects.requireNonNull(txtLoginPassword.getText()).toString();

        Call<LoginResponse> call;
        call = apiService.AccountLogin(strMobile, strPassword, strToken);

        call.enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(@NonNull Call<LoginResponse> call, @NonNull Response<LoginResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {

                    mPrefUtils.PutSharedBool(LoggedIn, true);
                    mPrefUtils.PutSharedString(LoginName, response.body().getLoginDataItem().get(0).getName());
                    mPrefUtils.PutSharedString(MOBILE_NUMBER, response.body().getLoginDataItem().get(0).getMobileNo());
                    mPrefUtils.PutSharedString(ADDRESS, response.body().getLoginDataItem().get(0).getAddress());
                    mPrefUtils.PutSharedString(EMAIL, response.body().getLoginDataItem().get(0).getEMail());
                    mPrefUtils.PutSharedString(LoginId, String.valueOf(response.body().getLoginDataItem().get(0).getID()));
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Hello, " + response.body().getLoginDataItem().get(0).getName());
                    mUtilManager.showProgress("", false);
                    startActivity(new Intent(Login.this, DashBoard.class));
                    finish();
                } else {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<LoginResponse> call, @NonNull Throwable t) {
                mUtilManager.showProgress("", false);
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }
}
