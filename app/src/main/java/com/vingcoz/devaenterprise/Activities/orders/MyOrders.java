package com.vingcoz.devaenterprise.Activities.orders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vingcoz.devaenterprise.Adapters.MyOrderAdapter;
import com.vingcoz.devaenterprise.Model.ordersum.OrderSummaryItem;
import com.vingcoz.devaenterprise.Model.ordersum.OrderSummaryResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyOrders extends AppCompatActivity {


    UtilManager mUtilManager;
    PrefUtils mPrefUtils;
    RecyclerView mRecycleView;
    MyOrderAdapter mAdapter;
    ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.my_orders_activity);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("My Orders");
        }


        mUtilManager = new UtilManager(MyOrders.this);
        mPrefUtils = new PrefUtils(MyOrders.this);


        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        mRecycleView = findViewById(R.id.wishRecycle);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        getMyOrders();
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

    void getMyOrders() {

        shimmerMode(true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<OrderSummaryResponse> call;

        call = apiService.ViewOrderSummaryList(strUserID);

        call.enqueue(new Callback<OrderSummaryResponse>() {

            @Override
            public void onResponse(@NonNull Call<OrderSummaryResponse> call, @NonNull Response<OrderSummaryResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    loadDataList(response.body().getOrderSummary());
                    shimmerMode(false);
                } else {
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderSummaryResponse> call, @NonNull Throwable t) {
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }

    private void loadDataList(List<OrderSummaryItem> mList) {

        mAdapter = new MyOrderAdapter(MyOrders.this, mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyOrders.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

}
