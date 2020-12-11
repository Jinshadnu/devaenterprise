package com.vingcoz.devaenterprise.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vingcoz.devaenterprise.Model.orderdetails.OrderItemsDetailsItems;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.common.ListManager;

import java.util.List;

import static com.vingcoz.devaenterprise.Utils.GlobalVariables.IMAGE_LOCATION;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.WishViewHolder> {

    Context mCtx;
    List<OrderItemsDetailsItems> OrderItemsList;
    ListManager mListManager;

    public OrderDetailAdapter(Context mCtx, List<OrderItemsDetailsItems> OrderItemsList) {
        this.mCtx = mCtx;
        this.OrderItemsList = OrderItemsList;
        mListManager = new ListManager(mCtx);
    }

    @NonNull
    @Override
    public OrderDetailAdapter.WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_row_order_details, parent, false);
        return new OrderDetailAdapter.WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailAdapter.WishViewHolder holder, int position) {
        OrderItemsDetailsItems WishSingle = OrderItemsList.get(position);

        holder.txtName.setText(WishSingle.getProduct());
        holder.txtQty.setText("Quantity : " + WishSingle.getQuantity());
        holder.txtPrice.setText("Unit MRP : ₹ " + WishSingle.getPrice());

        Glide.with(mCtx).load(IMAGE_LOCATION + WishSingle.getImage())
                .placeholder(R.drawable.thai_logo_small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSmallProduct);

    }

    @Override
    public int getItemCount() {
        return OrderItemsList.size();
    }

    class WishViewHolder extends RecyclerView.ViewHolder {

        TextView txtName, txtQty, txtPrice;
        CardView crdRow;
        ImageView imgSmallProduct;

        public WishViewHolder(View itemView) {
            super(itemView);
            txtName = itemView.findViewById(R.id.txtName);
            txtQty = itemView.findViewById(R.id.txtQty);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            imgSmallProduct = itemView.findViewById(R.id.imgSmallProduct);
            crdRow = itemView.findViewById(R.id.crdRow);
        }
    }
}
