package com.vingcoz.devaenterprise.Model.product;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductResponse {

    @SerializedName("product")
    private List<ProductItem> product;

    @SerializedName("error")
    private boolean error;

    @SerializedName("products")
    private String products;

    public void setProduct(List<ProductItem> product) {
        this.product = product;
    }

    public List<ProductItem> getProduct() {
        return product;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public boolean isError() {
        return error;
    }

    public void setProducts(String products) {
        this.products = products;
    }

    public String getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return
                "ProductResponse{" +
                        "product = '" + product + '\'' +
                        ",error = '" + error + '\'' +
                        ",products = '" + products + '\'' +
                        "}";
    }
}