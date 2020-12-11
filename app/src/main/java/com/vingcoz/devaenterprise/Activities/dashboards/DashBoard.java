package com.vingcoz.devaenterprise.Activities.dashboards;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.IndicatorAnimations;
import com.smarteist.autoimageslider.SliderAnimations;
import com.smarteist.autoimageslider.SliderView;
import com.vingcoz.devaenterprise.Activities.authentication.Login;
import com.vingcoz.devaenterprise.Activities.common.AboutActivity;
import com.vingcoz.devaenterprise.Activities.common.ProfileActivity;
import com.vingcoz.devaenterprise.Activities.orders.MyOrders;
import com.vingcoz.devaenterprise.Adapters.ProductGridAdapter;
import com.vingcoz.devaenterprise.Adapters.SliderAdapterExample;
import com.vingcoz.devaenterprise.ConfirmCart;
import com.vingcoz.devaenterprise.Model.SliderItem;
import com.vingcoz.devaenterprise.Model.product.ProductResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vingcoz.devaenterprise.Utils.PrefUtils.ADDRESS;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.EMAIL;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoggedIn;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginId;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.LoginName;
import static com.vingcoz.devaenterprise.Utils.PrefUtils.MOBILE_NUMBER;

public class DashBoard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    UtilManager mUtilManager;
    ProductGridAdapter mAdapter;
    PrefUtils mPrefUtils;
    ShimmerFrameLayout mShimmerViewContainer;
    LinearLayout lnMainLayout, lnSearchLayout;
    FloatingActionButton fab;
    GridView mGridView;

    SliderView sliderView;
    private SliderAdapterExample adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dash_board);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUtilManager = new UtilManager(DashBoard.this);
        mPrefUtils = new PrefUtils(DashBoard.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        TextView navUser = hView.findViewById(R.id.mUser);
        navUser.setText(mPrefUtils.GetSharedString(LoginName));

        sliderView = findViewById(R.id.imageSlider);

        adapter = new SliderAdapterExample(this);
        sliderView.setSliderAdapter(adapter);

        sliderView.setIndicatorAnimation(IndicatorAnimations.THIN_WORM); //set indicator animation by using SliderLayout.IndicatorAnimations. :WORM or THIN_WORM or COLOR or DROP or FILL or NONE or SCALE or SCALE_DOWN or SLIDE and SWAP!!
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.setAutoCycleDirection(SliderView.AUTO_CYCLE_DIRECTION_RIGHT);
        sliderView.setIndicatorSelectedColor(Color.WHITE);
        sliderView.setIndicatorUnselectedColor(Color.GRAY);
        sliderView.setScrollTimeInSec(3);
        sliderView.setAutoCycle(false);
        renewItems(sliderView);

        lnMainLayout = findViewById(R.id.lnMain);
        lnSearchLayout = findViewById(R.id.lnSearch);
        mShimmerViewContainer = findViewById(R.id.shimmer_view_container);
        lnSearchLayout.setVisibility(View.GONE);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, GlobalVariables.SHARE_CONTENT);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share to");
            startActivity(Intent.createChooser(shareIntent, "Share to"));
        });
        mGridView = findViewById(R.id.gridTopSelling);
        LoadProducts();
        CheckLogin();
    }

    public void renewItems(View view) {
        List<SliderItem> sliderItemList = new ArrayList<>();

        SliderItem sliderItem = new SliderItem();
        sliderItem.setStrImage("http://webapp.devaenterprisesthaikkad.com/v1/android_banner/a.png");
        sliderItemList.add(sliderItem);

        sliderItem = new SliderItem();
        sliderItem.setStrImage("http://webapp.devaenterprisesthaikkad.com/v1/android_banner/b.png");
        sliderItemList.add(sliderItem);

        sliderItem = new SliderItem();
        sliderItem.setStrImage("http://webapp.devaenterprisesthaikkad.com/v1/android_banner/c.png");
        sliderItemList.add(sliderItem);

        adapter.renewItems(sliderItemList);
    }

    public void removeLastItem(View view) {
        if (adapter.getCount() - 1 >= 0)
            adapter.deleteItem(adapter.getCount() - 1);
    }

    public void addNewItem(View view) {
        SliderItem sliderItem = new SliderItem();
        sliderItem.setStrImage("https://images.pexels.com/photos/929778/pexels-photo-929778.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260");
        adapter.addItem(sliderItem);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dash_board, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_website) {
            Uri uri = Uri.parse(GlobalVariables.CLIENT_WEBSITE); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_privacy_policy) {
            Uri uri = Uri.parse(GlobalVariables.PRIVACY_POLICY_LINK); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_update) {
            Uri uri = Uri.parse(GlobalVariables.PLAY_STORE_LINK); // missing 'http://' will cause crashed
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Cart) {
            startActivity(new Intent(DashBoard.this, ConfirmCart.class));
        } else if (id == R.id.nav_MyOrders) {
            startActivity(new Intent(DashBoard.this, MyOrders.class));
        } else if (id == R.id.nav_Account) {
            startActivity(new Intent(DashBoard.this, ProfileActivity.class));
        } else if (id == R.id.nav_Share) {

            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_TEXT, GlobalVariables.SHARE_CONTENT);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Share to");
            startActivity(Intent.createChooser(shareIntent, "Share to"));

        } else if (id == R.id.nav_AboutUs) {
            startActivity(new Intent(DashBoard.this, AboutActivity.class));
        } else if (id == R.id.nav_Log) {

            AlertDialog alertDialog = new AlertDialog.Builder(DashBoard.this)
                    .setIcon(R.drawable.ic_exit_to_app_purple_800_24dp)
                    .setTitle("Are you sure")
                    .setMessage("Do you want to log out from this application?")
                    .setPositiveButton("Yes", (dialogInterface, i) -> {
                        mPrefUtils.PutSharedBool(LoggedIn, false);
                        mPrefUtils.PutSharedString(LoginName, "");
                        mPrefUtils.PutSharedString(MOBILE_NUMBER, "");
                        mPrefUtils.PutSharedString(ADDRESS, "");
                        mPrefUtils.PutSharedString(EMAIL, "");
                        mPrefUtils.PutSharedString(LoginId, "");
                        startActivity(new Intent(DashBoard.this, Login.class));
                        finish();
                        mUtilManager.showInfo("Please Sign In to continue");
                    })
                    .setNegativeButton("No", (dialogInterface, i) -> {
                        mUtilManager.showInfo("Logout Cancelled..");
                    })
                    .show();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void CheckLogin() {
        if (!mPrefUtils.GetSharedBool(LoggedIn) && mPrefUtils.GetSharedLong(LoginId) == 0) {
            startActivity(new Intent(DashBoard.this, Login.class));
            finish();
            mUtilManager.showInfo("Please Sign In to continue");
        }
    }

    public void LoadProducts() {
        shimmerMode(true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<ProductResponse> call;
        call = apiService.GetProducts();

        call.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                assert response.body() != null;
                if (!response.body().isError()) {
                    mAdapter = new ProductGridAdapter(DashBoard.this, response.body().getProduct());
                    mGridView.setAdapter(mAdapter);
                    shimmerMode(false);
                }
                shimmerMode(false);
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                shimmerMode(false);
                mUtilManager.showError("Something went wrong please try again later.");
            }

        });
    }

    void shimmerMode(boolean blnActive) {

        if (blnActive) {
            mShimmerViewContainer.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mShimmerViewContainer.startShimmerAnimation();
        } else {
            mShimmerViewContainer.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            mShimmerViewContainer.stopShimmerAnimation();
        }
    }
}