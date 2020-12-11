package com.vingcoz.devaenterprise;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vingcoz.devaenterprise.Adapters.CheckOutAdapter;
import com.vingcoz.devaenterprise.Model.cart.CartProductsItem;
import com.vingcoz.devaenterprise.Model.cart.CartResponse;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import org.parceler.Parcels;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmCart extends AppCompatActivity {

    UtilManager mUtilManager;
    PrefUtils mPrefUtils;
    RecyclerView mRecycleView;
    CheckOutAdapter mAdapter;
    TextView txtAmount;
    boolean isCompleted = false;
    ShimmerFrameLayout mShimmerViewContainer;
    Button btnBuy;
    Double dblTotal = 0.0;
    boolean IsBuyCan = false;
    boolean IsReturnEmptyCan = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_cart_activity);
        mUtilManager = new UtilManager(ConfirmCart.this);
        mPrefUtils = new PrefUtils(ConfirmCart.this);

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        txtAmount = findViewById(R.id.txtAmount);
        mRecycleView = findViewById(R.id.recyclerview);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.getViewTreeObserver()
                .addOnGlobalLayoutListener(this::ShowTotal);
        mRecycleView.getViewTreeObserver()
                .removeOnGlobalLayoutListener(this::ShowTotal);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Cart");
        }
        GetCartItems();
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            if (dblTotal < 29) {
                mUtilManager.showError("Minimum amount of order is ₹ 30");
            } else if (dblTotal < 1) {
                mUtilManager.showError("Empty Cart!! , please cart item before continue..");
            } else {
                List<CartProductsItem> mCart = mAdapter.getList();
                Intent OpenConfirm = new Intent(ConfirmCart.this, ConfirmOrder.class);
                OpenConfirm.putExtra(GlobalVariables.CART_AMOUNT, String.format(("%.2f"), dblTotal));
                OpenConfirm.putExtra(GlobalVariables.CART_ITEMS, Parcels.wrap(mCart));
                startActivity(OpenConfirm);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void shimmerMode(boolean blnActive) {

        if (blnActive) {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mRecycleView.setVisibility(View.GONE);
            mShimmerViewContainer.startShimmerAnimation();
        } else {
            mShimmerViewContainer.setVisibility(View.GONE);
            mRecycleView.setVisibility(View.VISIBLE);
            mShimmerViewContainer.stopShimmerAnimation();
        }
    }

    void GetCartItems() {

        shimmerMode(true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<CartResponse> call;

        call = apiService.GetCartList(strUserID);

        call.enqueue(new Callback<CartResponse>() {

            @Override
            public void onResponse(@NonNull Call<CartResponse> call, @NonNull Response<CartResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {

                    if (response.body().getCartProducts().size() == 0) {
                        mUtilManager.showError("No products in your cart !!");
                    } else {
                        loadDataList(response.body().getCartProducts());
                        shimmerMode(false);

                        for (int i = 0; i < response.body().getCartProducts().size(); i++) {

                            if (response.body().getCartProducts().get(i).getProductId() == GlobalVariables.BUY_CAN_ID) {
                                IsBuyCan = true;
                            }

                            if (response.body().getCartProducts().get(i).getProductId() == GlobalVariables.RETURN_CAN_ID) {
                                IsReturnEmptyCan = true;
                            }

                        }

                        if (IsBuyCan && !IsReturnEmptyCan) {


                            AlertDialog alertDialog = new AlertDialog.Builder(ConfirmCart.this)
                                    .setIcon(R.drawable.ic_exit_to_app_purple_800_24dp)
                                    .setTitle("Return empty can?")
                                    .setMessage("Do you want to return empty WATER CAN?")
                                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                                        AddToCart();
                                    })
                                    .setNegativeButton("No", (dialogInterface, i) -> {
                                        dialogInterface.dismiss();
                                    })
                                    .show();
                        }
                    }
                } else {
                    shimmerMode(false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<CartResponse> call, @NonNull Throwable t) {
                shimmerMode(false);
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }

    private void loadDataList(List<CartProductsItem> mList) {
        mAdapter = new CheckOutAdapter(getApplicationContext(), mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(ConfirmCart.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
        isCompleted = true;
    }

    void ShowTotal() {
        if (isCompleted) {

            dblTotal = mAdapter.CalculateTotalAmount();
            txtAmount.setText("₹ " + String.format("%.2f", dblTotal));
        }
    }

    void AddToCart() {

        mUtilManager.showProgress("Adding to cart", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<NormalResponse> call;
        call = apiService.AddToCartList(strUserID, GlobalVariables.RETURN_CAN_ID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {
                mUtilManager.showProgress("", false);
                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showSuccess("Product added cart");
                    GetCartItems();
                } else {
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
