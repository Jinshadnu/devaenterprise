package com.vingcoz.devaenterprise.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.vingcoz.devaenterprise.Activities.orders.MyOrderDetails;
import com.vingcoz.devaenterprise.Model.ordersum.OrderSummaryItem;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.GlobalVariables;
import com.vingcoz.devaenterprise.Utils.common.ListManager;

import java.util.List;

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.WishViewHolder> {

    Context mCtx;
    List<OrderSummaryItem> WishList;
    ListManager mListManager;

    public MyOrderAdapter(Context mCtx, List<OrderSummaryItem> WishList) {
        this.mCtx = mCtx;
        this.WishList = WishList;
        mListManager = new ListManager(mCtx);
    }

    @NonNull
    @Override
    public MyOrderAdapter.WishViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_row_order, parent, false);
        return new MyOrderAdapter.WishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyOrderAdapter.WishViewHolder holder, int position) {
        OrderSummaryItem WishSingle = WishList.get(position);

        holder.txtOrderNo.setText("Order No. : " + WishSingle.getOrderId());
        holder.txtDate.setText("Order Date : " + WishSingle.getOrderedDate());
        holder.txtPrice.setText("₹ " + WishSingle.getTotalAmount());
        holder.txtStatus.setText(WishSingle.getOrderStatus());

        holder.crdRow.setOnClickListener(view ->
                {
                    Intent OpenProductView = new Intent(mCtx, MyOrderDetails.class);
                    OpenProductView.putExtra(GlobalVariables.ORDER_ID, String.valueOf(WishSingle.getOrderId()));
                    mCtx.startActivity(OpenProductView);
                }
        );
    }

    @Override
    public int getItemCount() {
        return WishList.size();
    }

    class WishViewHolder extends RecyclerView.ViewHolder {

        TextView txtOrderNo, txtDate, txtPrice, txtStatus;
        CardView crdRow;
        ImageView imgSmallProduct;

        public WishViewHolder(View itemView) {
            super(itemView);

            txtOrderNo = itemView.findViewById(R.id.txtOrderNo);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            crdRow = itemView.findViewById(R.id.crdRow);
        }
    }
}
