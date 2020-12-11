package com.vingcoz.devaenterprise.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vingcoz.devaenterprise.Activities.orders.ProductView;
import com.vingcoz.devaenterprise.Model.product.ProductItem;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;

import java.util.List;

import static com.vingcoz.devaenterprise.Utils.GlobalVariables.IMAGE_LOCATION;

public class ProductGridAdapter extends BaseAdapter {

    private Context mCtx;
    private List<ProductItem> ListContents;

    public ProductGridAdapter(Context applicationContext, List<ProductItem> ListContents) {
        this.mCtx = applicationContext;
        this.ListContents = ListContents;
        LayoutInflater layInflater = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return ListContents.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater cellLayout = (LayoutInflater) mCtx.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = cellLayout.inflate(R.layout.single_item, null);
        TextView txtProductName = view.findViewById(R.id.txtHeading);
        TextView txtFinalPrice = view.findViewById(R.id.txtFinalPrice);
        ImageView imgPoster = view.findViewById(R.id.imgSmallProduct);
        TextView txtProductID = view.findViewById(R.id.txtProductID);
        ConstraintLayout clickLayout = view.findViewById(R.id.clickConstrainLayout);

        clickLayout.setOnClickListener(v -> {
            Intent OpenProductView = new Intent(mCtx, ProductView.class);
            OpenProductView.putExtra(GlobalVariables.PRODUCT_ID, txtProductID.getText());
            OpenProductView.putExtra(GlobalVariables.PRO_NAME, ListContents.get(i).getName());
            OpenProductView.putExtra(GlobalVariables.PRO_DESCRIPTION, ListContents.get(i).getDescription());
            OpenProductView.putExtra(GlobalVariables.PRO_IMAGE_LOCATION, ListContents.get(i).getImage());
            OpenProductView.putExtra(GlobalVariables.PRO_UNIT, ListContents.get(i).getUnit());
            OpenProductView.putExtra(GlobalVariables.PRO_PRICE, String.format("%.2f", ListContents.get(i).getPrice()));

            mCtx.startActivity(OpenProductView);
            Toast.makeText(mCtx, ListContents.get(i).getName(), Toast.LENGTH_SHORT).show();
        });
        txtProductID.setText(String.valueOf(ListContents.get(i).getId()));
        Glide.with(mCtx).load(IMAGE_LOCATION + ListContents.get(i).getImage())
                .placeholder(R.drawable.thai_logo)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgPoster);

        if (ListContents.get(i).getName().length() > 20) {
            txtProductName.setText(ListContents.get(i).getName().substring(0, 20) + "...");
        } else {
            txtProductName.setText(ListContents.get(i).getName());
        }
        txtFinalPrice.setText("₹ " + ListContents.get(i).getPrice());

        return view;
    }
}
