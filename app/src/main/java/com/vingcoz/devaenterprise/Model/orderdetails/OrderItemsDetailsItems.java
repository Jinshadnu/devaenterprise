package com.vingcoz.devaenterprise.Model.orderdetails;

import com.google.gson.annotations.SerializedName;

public class OrderItemsDetailsItems {

    @SerializedName("Price")
    private int price;

    @SerializedName("Product")
    private String product;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("ProductId")
    private int productId;

    @SerializedName("Image")
    private String image;

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProduct() {
        return product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return
                "OrderItemsDetailsItems{" +
                        "price = '" + price + '\'' +
                        ",product = '" + product + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",image = '" + image + '\'' +
                        "}";
    }
}