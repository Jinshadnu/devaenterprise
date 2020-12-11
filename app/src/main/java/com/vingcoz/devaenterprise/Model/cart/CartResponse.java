package com.vingcoz.devaenterprise.Model.cart;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CartResponse {

    @SerializedName("Products")
    private List<CartProductsItem> cartProducts;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setCartProducts(List<CartProductsItem> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public List<CartProductsItem> getCartProducts() {
        return cartProducts;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return
                "CartResponse{" +
                        "cartProducts = '" + cartProducts + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}