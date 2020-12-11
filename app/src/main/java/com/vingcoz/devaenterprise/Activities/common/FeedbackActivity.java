package com.vingcoz.devaenterprise.Activities.common;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    TextInputEditText txtFeedback;
    MaterialButton btnSubmitFeedback;
    UtilManager mUtilManager;
    PrefUtils mPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feedback_activity);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Feedback");
        }
        mUtilManager = new UtilManager(FeedbackActivity.this);
        mPrefUtils = new PrefUtils(FeedbackActivity.this);
        txtFeedback = findViewById(R.id.txtFeedback);
        btnSubmitFeedback = findViewById(R.id.btnSubmitFeedback);
        btnSubmitFeedback.setOnClickListener(v -> {
            if (Validate()) {
                SubmitFeedback();
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
        if (Objects.requireNonNull(txtFeedback.getText()).length() < 6) {
            txtFeedback.setError("Minimum 6 character required");
            Result = false;
        }
        return Result;
    }

    void SubmitFeedback() {

        mUtilManager.showProgress("Submitting Feedback...", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strContent = Objects.requireNonNull(txtFeedback.getText()).toString();
        String strEmail = mPrefUtils.GetSharedString(PrefUtils.EMAIL);
        String strDateTime = mUtilManager.getAPIDateTime();
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<NormalResponse> call;

        call = apiService.SubmitFeedback(strUserID, strEmail, strContent, strDateTime);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Feedback submitted Successfully");
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
}
