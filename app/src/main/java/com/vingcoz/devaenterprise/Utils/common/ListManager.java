package com.vingcoz.devaenterprise.Utils.common;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListManager {

    private Context mCtx;

    UtilManager mUtilManager;
    PrefUtils mPrefUtils;
    String strUserID = "";
    boolean ReturnValue = false;

    public ListManager(Context context) {
        mCtx = context;
        mUtilManager = new UtilManager(mCtx);
        mPrefUtils = new PrefUtils(mCtx);
        strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
    }

    public void ShowToast(String strMessage) {//todo api remove cart
        Toast.makeText(mCtx, strMessage, Toast.LENGTH_SHORT).show();
    }

    public void AddToWishList(long ProductID) {

        mUtilManager.showProgress("Adding to wish list", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<NormalResponse> call;
        call = apiService.AddToWishList(strUserID, ProductID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Product added to wish list");
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

    public void AddToCart(long ProductID) {

        mUtilManager.showProgress("Adding to cart", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);

        Call<NormalResponse> call;
        call = apiService.AddToCartList(strUserID, ProductID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Product added cart");

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

    public void RemoveFromCart(long ProductID) {

       // mUtilManager.showProgress("Removing from cart..", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<NormalResponse> call;
        call = apiService.RemoveCart(strUserID, ProductID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                  //  mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Product removed from cart");
                } else {
                  //  mUtilManager.showProgress("", false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NormalResponse> call, @NonNull Throwable t) {
               // mUtilManager.showProgress("", false);

                mUtilManager.showError("Something went wrong please try again later");
            }

        });

    }

    public void RemoveFromWishList(long ProductID) {

     //   mUtilManager.showProgress("Removing from wish list", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<NormalResponse> call;
        call = apiService.RemoveWish(strUserID, ProductID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                  //  mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Product removed from wish list");
                } else {
                  //  mUtilManager.showProgress("", false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<NormalResponse> call, @NonNull Throwable t) {
              //  mUtilManager.showProgress("", false);

                mUtilManager.showError("Something went wrong please try again later");
            }

        });

    }
}
