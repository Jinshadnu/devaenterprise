package com.vingcoz.devaenterprise.Activities.orders;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.google.android.material.button.MaterialButton;
import com.vingcoz.devaenterprise.ConfirmCart;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.ApiUtils;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;
import com.vingcoz.devaenterprise.Utils.PrefUtils;
import com.vingcoz.devaenterprise.Utils.RetrofitClient;
import com.vingcoz.devaenterprise.Utils.UtilManager;
import com.vingcoz.devaenterprise.Utils.common.ListManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vingcoz.devaenterprise.Utils.GlobalVariables.IMAGE_LOCATION;

public class ProductView extends AppCompatActivity {


    TextView txtHeading, txtFinalPrice, txtDescription, txtUnit;
    ImageView imgProduct;
    UtilManager mUtilManager;
    ListManager mListManager;
    long ProductID = (long) 0;
    MaterialButton btnProductShare;
    Button btnBuy, btnAddToCart;
    PrefUtils mPrefUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_view);

        mUtilManager = new UtilManager(ProductView.this);
        mListManager = new ListManager(ProductView.this);
        mPrefUtils = new PrefUtils(ProductView.this);
        txtHeading = findViewById(R.id.txtHeading);
        txtFinalPrice = findViewById(R.id.txtFinalPrice);
        imgProduct = findViewById(R.id.imgProduct);
        txtDescription = findViewById(R.id.txtDescription);
        txtUnit = findViewById(R.id.txtUnit);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        Intent intent = getIntent();
        String strProductID = intent.getStringExtra(GlobalVariables.PRODUCT_ID);
        String strProductName = intent.getStringExtra(GlobalVariables.PRO_NAME);
        String strDescription = intent.getStringExtra(GlobalVariables.PRO_DESCRIPTION);
        String strImageLocation = intent.getStringExtra(GlobalVariables.PRO_IMAGE_LOCATION);
        String strUnit = intent.getStringExtra(GlobalVariables.PRO_UNIT);
        String strPrice = intent.getStringExtra(GlobalVariables.PRO_PRICE);

        assert strProductID != null;
        ProductID = Long.parseLong(strProductID);
        txtHeading.setText(strProductName);
        txtUnit.setText("Unit in " + strUnit);
        txtDescription.setText(strDescription);
        double dblPrice = Double.parseDouble(strPrice);
        txtFinalPrice.setText("₹ " + String.format("%.2f", dblPrice));
        Glide.with(ProductView.this).load(IMAGE_LOCATION + strImageLocation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imgProduct);

        btnProductShare = findViewById(R.id.btnProductShare);
        btnProductShare.setOnClickListener(v -> {
            String ProductDescription = txtHeading.getText().toString();
            ProductDescription += "\n";
            ProductDescription += txtDescription.getText().toString();
            ProductDescription += "\n";
            ProductDescription += txtFinalPrice.getText().toString() + " /- ";

            mUtilManager.shareApp("I have found this product from ThaiValley , It is a good product " + ProductDescription);
        });
        btnBuy = findViewById(R.id.btnBuy);
        btnBuy.setOnClickListener(v -> {
            AddToCart(true);
        });
        btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {
            AddToCart(false);
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    void AddToCart(boolean IsBuy) {

        mUtilManager.showProgress("Adding to cart", true);
        ApiUtils apiService = RetrofitClient.getClient().create(ApiUtils.class);
        String strUserID = mPrefUtils.GetSharedString(PrefUtils.LoginId);
        Call<NormalResponse> call;
        call = apiService.AddToCartList(strUserID, ProductID);

        call.enqueue(new Callback<NormalResponse>() {

            @Override
            public void onResponse(@NonNull Call<NormalResponse> call, @NonNull Response<NormalResponse> response) {

                assert response.body() != null;
                if (!response.body().isError()) {
                    mUtilManager.showProgress("", false);
                    mUtilManager.showSuccess("Product added cart");

                    if (IsBuy) {
                        startActivity(new Intent(ProductView.this, ConfirmCart.class));
                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(ProductView.this)
                                .setIcon(R.drawable.ic_shopping_cart_black_24dp)
                                .setTitle("Continue Shopping?")
                                .setMessage("Do you want to continue shopping?")
                                .setPositiveButton("Yes", (dialogInterface, i) -> {

                                })
                                .setNegativeButton("No", (dialogInterface, i) -> {
                                    startActivity(new Intent(ProductView.this, ConfirmCart.class));
                                })
                                .show();
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
}
