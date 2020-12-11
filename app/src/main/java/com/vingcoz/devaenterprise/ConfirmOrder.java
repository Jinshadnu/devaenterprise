package com.vingcoz.devaenterprise;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.vingcoz.devaenterprise.Activities.dashboards.DashBoard;
import com.vingcoz.devaenterprise.Activities.orders.MapsActivity;
import com.vingcoz.devaenterprise.Model.cart.CartProductsItem;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.Model.order.OrderResponse;
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

import static com.vingcoz.devaenterprise.Utils.PrefUtils.MOBILE_NUMBER;

public class ConfirmOrder extends AppCompatActivity {

    TextView txtAmount, txtAddress, txtMapAddress, txtNoItems, txtDelivery, txtAmountPayable;
    Button btnBuy;
    List<CartProductsItem> mCart;
    String strTotalAmount;
    PrefUtils mPrefUtils;
    UtilManager mUtilManager;
    String strCustomer, strAddress, strPin, strPhone, strDateTime;
    Double dblToTal = 0.0;
    Double dblDelivery = 0.0;
    int OrderID = 0;
    Button btnMap;
    String strLatitude, strLongitude, strMapAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirm_order_activity);

        mUtilManager = new UtilManager(ConfirmOrder.this);
        mPrefUtils = new PrefUtils(ConfirmOrder.this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Confirm Order");
        }

        Intent intent = getIntent();
        strTotalAmount = intent.getStringExtra(GlobalVariables.CART_AMOUNT);
        mCart = Parcels.unwrap(getIntent().getParcelableExtra(GlobalVariables.CART_ITEMS));

        strCustomer = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        strAddress = mPrefUtils.GetSharedString(PrefUtils.ADDRESS);
        strPin = " ";
        strPhone = mPrefUtils.GetSharedString(MOBILE_NUMBER);
        strDateTime = mUtilManager.getAPIDateTime();

        txtAmount = findViewById(R.id.txtAmount);
        txtAddress = findViewById(R.id.txtAddress);
        txtMapAddress = findViewById(R.id.txtMapAddress);
        txtNoItems = findViewById(R.id.txtNoItems);
        txtDelivery = findViewById(R.id.txtDelivery);
        txtAmountPayable = findViewById(R.id.txtAmountPayable);
        btnMap = findViewById(R.id.btnMap);
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            if (validate()) {
                StartSubmit();
            }
        });

        btnMap.setOnClickListener(v -> {
            Intent OpenMap = new Intent(ConfirmOrder.this, MapsActivity.class);
            startActivityForResult(OpenMap, 1);
        });
        dblToTal = CalculateTotalAmount();
        dblToTal += dblDelivery;

        txtAddress.setText(strAddress);
        txtNoItems.setText(String.valueOf(mCart.size()));
        txtDelivery.setText("Free");

        txtAmount.setText(String.format("%.2f", dblToTal));
        txtAmountPayable.setText(String.format("%.2f", dblToTal));

        strLatitude = mPrefUtils.GetSharedString(PrefUtils.PREF_LATITUDE);
        strLongitude = mPrefUtils.GetSharedString(PrefUtils.PREF_LONGITUDE);
        txtMapAddress.setText(mPrefUtils.GetSharedString(PrefUtils.PREF_MAP_ADDRESS));
        strMapAddress = mPrefUtils.GetSharedString(PrefUtils.PREF_MAP_ADDRESS);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public double CalculateTotalAmount() {

        int CartSize = mCart.size();
        dblToTal = 0.0;
        for (int ij = 0; ij < CartSize; ij++) {

            CartProductsItem rowCart = mCart.get(ij);
            int Qty = rowCart.getQuantity();
            double dblPrice = rowCart.getPrice();
            double dblSum = Qty * dblPrice;
            dblToTal += dblSum;
        }

        return dblToTal;
    }

    boolean validate() {
        if (strLatitude.length() < 3) {
            mUtilManager.showError("Please select map location before submitting");
            return false;
        }
        return true;
    }

    void StartSubmit() {

        mUtilManager.showProgress("Submitting order ..please wait..", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<OrderResponse> call;
        call = apiService.SaveOrderSummary(strCustomer, strAddress + " [" + strMapAddress + "]", strPin, strPhone, strLatitude, strLongitude, dblToTal, strDateTime);

        call.enqueue(new Callback<OrderResponse>() {

            @Override
            public void onResponse(@NonNull Call<OrderResponse> call, @NonNull Response<OrderResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    OrderID = response.body().getOrderSummaryId();
                    UpdateProducts();
                } else {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<OrderResponse> call, @NonNull Throwable t) {
                mUtilManager.showProgress("", false);
                mUtilManager.showError("Something went wrong please try again later");
            }

        });
    }

    public void UpdateProducts() {

        int CartSize = mCart.size();
        int kl = 0;
        int snd = 0;
        for (int ij = 0; ij < CartSize; ij++) {

            CartProductsItem rowCart = mCart.get(ij);
            int Qty = rowCart.getQuantity();
            double dblPrice = rowCart.getPrice();
            double dblSum = Qty * dblPrice;
            kl++;
            snd = 2;
            if (kl == 0) {
                snd = 0;
            }
            if (kl == CartSize) {
                snd = 1;
            }
            SubmitOrder(rowCart.getProductId(), dblPrice, Qty, snd);
        }
    }

    void SubmitOrder(int pid, double dblMrp, int qty, int mPosition) {

        if (mPosition == 0) {
            mUtilManager.showProgress("Submitting order ..please wait..", true);
        }
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        Call<NormalResponse> call;
        call = apiService.SaveProducts(strCustomer, pid, dblMrp, qty, OrderID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    if (mPosition == 1) {
                        mUtilManager.showProgress("", false);
                        AlertDialog.Builder builder = new AlertDialog.Builder(ConfirmOrder.this);
                        builder.setMessage("Your order is received, we will contact you soon, your order number is " + String.valueOf(OrderID))
                                .setTitle("Order received")
                                .setCancelable(false)
                                .setPositiveButton("OK", (dialog, id) -> {
                                    startActivity(new Intent(ConfirmOrder.this, DashBoard.class));
                                });
                        AlertDialog alert = builder.create();
                        alert.show();
                    }

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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            strLatitude = data.getStringExtra("GetLatitude");
            strLongitude = data.getStringExtra("GetLongitude");
            strMapAddress = data.getStringExtra("GetAddress");
            txtMapAddress.setText(data.getStringExtra("GetAddress"));

            mPrefUtils.PutSharedString(PrefUtils.PREF_LATITUDE, strLatitude);
            mPrefUtils.PutSharedString(PrefUtils.PREF_LONGITUDE, strLatitude);
            mPrefUtils.PutSharedString(PrefUtils.PREF_MAP_ADDRESS, data.getStringExtra("GetAddress"));

            mUtilManager.showSuccess("Location updated");

        }
    }
}
