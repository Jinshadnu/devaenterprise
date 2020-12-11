package com.vingcoz.devaenterprise.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vingcoz.devaenterprise.Model.wishlist.WishItems;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.common.ListManager;

import java.util.List;

import static com.vingcoz.devaenterprise.Utils.GlobalVariables.IMAGE_LOCATION;

public class WishListAdapter extends RecyclerView.Adapter<WishListAdapter.WishViewHolder> {

    Context mCtx;
    List<WishItems> WishList;
    ListManager mListManager;

    public WishListAdapter(Context mCtx, List<WishItems> WishList) {
        this.mCtx = mCtx;
        this.WishList = WishList;
        mListManager = new ListManager(mCtx);
    }

    @NonNull
    @Override
    public WishListAdapter.WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_row_wish, parent, false);
        return new WishListAdapter.WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WishListAdapter.WishViewHolder holder, int position) {
        WishItems WishSingle = WishList.get(position);

        holder.txtProductID.setText(String.valueOf(WishSingle.getProductId()));
        holder.txtName.setText(WishSingle.getProduct());
        holder.txtUnit.setText("Unit in " + WishSingle.getUnit());
        holder.txtPrice.setText(String.format("%.2f", WishSingle.getPrice()));
        holder.txtMrp.setText(String.format("%.2f", WishSingle.getMRP()));
        holder.txtOffer.setText(String.format("%.2f", WishSingle.getOffer()));

        if (WishSingle.getOffer() == 0) {
            holder.txtMrp.setVisibility(View.GONE);
            holder.txtOffer.setVisibility(View.GONE);
        } else {
            holder.txtMrp.setVisibility(View.VISIBLE);
            holder.txtOffer.setVisibility(View.VISIBLE);
        }

        Glide.with(mCtx).load(IMAGE_LOCATION + WishSingle.getImage())
                .placeholder(R.drawable.thai_logo_small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSmallProduct);
        holder.btnCart.setOnClickListener(view ->
                {
                    mListManager.AddToCart(Long.valueOf(holder.txtProductID.getText().toString()));
                    WishList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, WishList.size());

                }
        );

        holder.btnRemove.setOnClickListener(view ->
                {
                    WishList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, WishList.size());
                    mListManager.RemoveFromWishList(Long.valueOf(holder.txtProductID.getText().toString()));
                }
        );

        if (WishSingle.getQuantity() > 10) {
            holder.txtOutStock.setVisibility(View.INVISIBLE);
        } else if (WishSingle.getQuantity() < 10 && WishSingle.getQuantity() > 0) {
            holder.txtOutStock.setText("Limited stock,Hurry!!");
        } else {
            holder.txtOutStock.setText("Out of stock");
        }

    }

    @Override
    public int getItemCount() {
        return WishList.size();
    }

    class WishViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductID, txtName, txtUnit, txtPrice, txtOutStock, txtMrp, txtOffer;
        // MaterialButton btnRemove;
        Button btnCart, btnRemove;
        LinearLayout cart_linear;
        ImageView imgSmallProduct;

        public WishViewHolder(View itemView) {
            super(itemView);

            txtProductID = itemView.findViewById(R.id.txtProductID);
            txtName = itemView.findViewById(R.id.txtName);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            txtMrp = itemView.findViewById(R.id.txtMrp);
            txtOffer = itemView.findViewById(R.id.txtOffer);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtOutStock = itemView.findViewById(R.id.txtOutStock);
            btnCart = itemView.findViewById(R.id.btnCart);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            imgSmallProduct = itemView.findViewById(R.id.imgSmallProduct);

            cart_linear = itemView.findViewById(R.id.cart_linear);
        }
    }
}
