package com.vingcoz.devaenterprise.Model.cart;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class CartProductsItem {

    @SerializedName("ProductId")
    private int productId;

    @SerializedName("Category")
    private String category;

    @SerializedName("Price")
    private double price;

    @SerializedName("Product")
    private String product;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("categoryId")
    private int categoryId;

    @SerializedName("Image")
    private String image;

    @SerializedName("Unit")
    private String unit;

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getProductId() {
        return productId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
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

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public String toString() {
        return
                "Products{" +
                        "product Id = '" + productId + '\'' +
                        ",category = '" + category + '\'' +
                        ",price = '" + price + '\'' +
                        ",product = '" + product + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",category Id = '" + categoryId + '\'' +
                        ",image = '" + image + '\'' +
                        ",unit = '" + unit + '\'' +
                        "}";
    }
}