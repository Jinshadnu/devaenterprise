package com.vingcoz.devaenterprise.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.vingcoz.devaenterprise.Model.cart.CartProductsItem;
import com.vingcoz.devaenterprise.R;
import com.vingcoz.devaenterprise.Utils.common.ListManager;

import java.util.List;

import static com.vingcoz.devaenterprise.Utils.GlobalVariables.IMAGE_LOCATION;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CartViewHolder> {

    Context mCtx;
    List<CartProductsItem> CartList;
    ListManager mListManager;
    Double dblToTal = 0.0;

    public CheckOutAdapter(Context mCtx, List<CartProductsItem> CartList) {
        this.mCtx = mCtx;
        this.CartList = CartList;
        mListManager = new ListManager(mCtx);
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mCtx).inflate(R.layout.single_row_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProductsItem CartSingle = CartList.get(position);

        holder.txtProductID.setText(String.valueOf(CartSingle.getProductId()));
        holder.txtName.setText(CartSingle.getProduct());
        holder.txtUnit.setText("Unit in " + CartSingle.getUnit());
        holder.txtPrice.setText(String.format("%.2f", CartSingle.getPrice()));
        Glide.with(mCtx).load(IMAGE_LOCATION + CartSingle.getImage())
                .placeholder(R.drawable.thai_logo_small)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imgSmallProduct);

        CartSingle.setQuantity(1);
        holder.btnAdd.setOnClickListener(view ->
                {
                    String strQty = holder.txtQty.getText().toString();
                    int Qty = Integer.valueOf(strQty);
                    Qty++;
                    CartSingle.setQuantity(Qty);
                    holder.txtQty.setText(String.valueOf(Qty));
                }
        );
        holder.btnMinus.setOnClickListener(view ->
                {
                    String strQty = holder.txtQty.getText().toString();
                    int Qty = Integer.valueOf(strQty);

                    if (Qty != 0) {
                        Qty--;
                        CartSingle.setQuantity(Qty);
                        holder.txtQty.setText(String.valueOf(Qty));
                    }
                }
        );
        holder.btnRemove.setOnClickListener(view ->
                {
                    CartSingle.setQuantity(0);
                    holder.txtQty.setText(String.valueOf(0));
                    CartList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, CartList.size());
                    mListManager.RemoveFromCart(Long.valueOf(holder.txtProductID.getText().toString()));
                }
        );

        if (CartSingle.getQuantity() > 10) {
            holder.txtOutStock.setVisibility(View.INVISIBLE);
        } else if (CartSingle.getQuantity() < 10 && CartSingle.getQuantity() > 0) {
            holder.txtOutStock.setText("Limited stock,Hurry!!");
        } else {
            holder.txtOutStock.setText("Out of stock");
        }

    }

    @Override
    public int getItemCount() {
        return CartList.size();
    }


    public double CalculateTotalAmount() {

        int CartSize = CartList.size();
        dblToTal = 0.0;
        for (int ij = 0; ij < CartSize; ij++) {

            CartProductsItem rowCart = CartList.get(ij);
            int Qty = rowCart.getQuantity();
            double dblPrice = rowCart.getPrice();
            double dblSum = Qty * dblPrice;
            dblToTal += dblSum;
        }

        return dblToTal;
    }

    public List<CartProductsItem> getList() {
        return CartList;
    }


    class CartViewHolder extends RecyclerView.ViewHolder {

        TextView txtProductID, txtName, txtUnit, txtPrice, txtOutStock;
        // MaterialButton btnRemove;
        Button btnAdd, btnMinus, btnRemove;
        EditText txtQty;
        LinearLayout cart_linear;
        ImageView imgSmallProduct;

        public CartViewHolder(View itemView) {
            super(itemView);

            txtProductID = itemView.findViewById(R.id.txtProductID);
            txtName = itemView.findViewById(R.id.txtName);
            txtUnit = itemView.findViewById(R.id.txtUnit);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtOutStock = itemView.findViewById(R.id.txtOutStock);
            txtQty = itemView.findViewById(R.id.txtQty);
            btnAdd = itemView.findViewById(R.id.btnAdd);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnRemove = itemView.findViewById(R.id.btnRemove);
            imgSmallProduct = itemView.findViewById(R.id.imgSmallProduct);

            cart_linear = itemView.findViewById(R.id.cart_linear);
        }
    }
}
