package com.vingcoz.devaenterprise.Activities.orders;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.vingcoz.devaenterprise.Adapters.WishListAdapter;
import com.vingcoz.devaenterprise.Model.wishlist.WishItems;
import com.vingcoz.devaenterprise.Model.wishlist.WishResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyWishList extends AppCompatActivity {


    UtilManager mUtilManager;
    PrefUtils mPrefUtils;
    RecyclerView mRecycleView;
    WishListAdapter mAdapter;
    ShimmerFrameLayout mShimmerViewContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_wishlist_activity);

        mUtilManager = new UtilManager(MyWishList.this);
        mPrefUtils = new PrefUtils(MyWishList.this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Wish List");
        }

        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);


        mRecycleView = findViewById(R.id.wishRecycle);
        mRecycleView.setHasFixedSize(true);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));

        GetWishList();
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

    void GetWishList() {

        shimmerMode(true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<WishResponse> call;

        call = apiService.WishList(strUserID);

        call.enqueue(new Callback<WishResponse>() {

            @Override
            public void onResponse(@NonNull Call<WishResponse> call, @NonNull Response<WishResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    loadDataList(response.body().getWishItems());
                    shimmerMode(false);
                } else {
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<WishResponse> call, @NonNull Throwable t) {
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }

    private void loadDataList(List<WishItems> mList) {

        mAdapter = new WishListAdapter(getApplicationContext(), mList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MyWishList.this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setAdapter(mAdapter);
    }

}
