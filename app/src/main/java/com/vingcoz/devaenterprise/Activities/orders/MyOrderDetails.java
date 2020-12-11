package com.vingcoz.devaenterprise.Activities.orders;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vingcoz.devaenterprise.Adapters.OrderDetailAdapter;
import com.vingcoz.devaenterprise.Model.orderdetails.OrderDetailResponse;
import com.vingcoz.devaenterprise.Model.orderdetails.OrderItemsDetailsItems;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrderDetails extends AppCompatActivity {


    UtilManager mUtilManager;
    PrefUtils mPrefUtils;
    RecyclerView mRecycleView;
    OrderDetailAdapter mAdapter;
    ShimmerFrameLayout mShimmerViewContainer;
    String strOrderID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_orders_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Orders");
        }

        Intent intent = getIntent();
        strOrderID = intent.getStringExtra(GlobalVariables.ORDER_ID);

        mUtilManager = new UtilManager(MyOrderDetails.this);
        mPrefUtils = new PrefUtils(MyOrderDetails.this);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        mRecycleView = findViewById(R.id.wishRecycle);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        getOrderDetails();
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void getOrderDetails() {

        shimmerMode(true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<OrderDetailResponse> call;

        call = apiService.viewOrderDetails(strUserID, strOrderID);

        call.enqueue(new Callback<OrderDetailResponse>() {

            @Override
            public void onResponse(@NonNull Call<OrderDetailResponse> call, @NonNull Response<OrderDetailResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    loadDataList(response.body().getItems());
                    shimmerMode(false);
                } else {
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderDetailResponse> call, @NonNull Throwable t) {
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }

    private void loadDataList(List<OrderItemsDetailsItems> mList) {

        mAdapter = new OrderDetailAdapter(getApplicationContext(), mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyOrderDetails.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

}
