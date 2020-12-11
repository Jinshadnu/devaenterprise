package com.vingcoz.devaenterprise.Model.wishlist;

import com.google.gson.annotations.SerializedName;


public class WishItems {

    @SerializedName("CategoryId")
    private int categoryId;

    @SerializedName("Category")
    private String category;

    @SerializedName("Description")
    private String description;

    @SerializedName("Price")
    private double price;

    @SerializedName("Offer")
    private double offer;

    @SerializedName("Product")
    private String product;

    @SerializedName("Quantity")
    private int quantity;

    @SerializedName("ProductId")
    private int productId;

    @SerializedName("MRP")
    private double mRP;

    @SerializedName("Image")
    private String image;

    @SerializedName("Unit")
    private String unit;

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setOffer(int offer) {
        this.offer = offer;
    }

    public double getOffer() {
        return offer;
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

    public void setMRP(int mRP) {
        this.mRP = mRP;
    }

    public double getMRP() {
        return mRP;
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
                "WishItems{" +
                        "categoryId = '" + categoryId + '\'' +
                        ",category = '" + category + '\'' +
                        ",description = '" + description + '\'' +
                        ",price = '" + price + '\'' +
                        ",offer = '" + offer + '\'' +
                        ",product = '" + product + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",productId = '" + productId + '\'' +
                        ",mRP = '" + mRP + '\'' +
                        ",image = '" + image + '\'' +
                        ",unit = '" + unit + '\'' +
                        "}";
    }
}