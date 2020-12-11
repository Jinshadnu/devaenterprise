package com.vingcoz.devaenterprise.Model.product;

import com.google.gson.annotations.SerializedName;


public class ProductItem {

    @SerializedName("Description")
    private String description;

    @SerializedName("Price")
    private double price;

    @SerializedName("Quantity")
    private double quantity;

    @SerializedName("id")
    private int id;

    @SerializedName("Unit")
    private String unit;

    @SerializedName("Image")
    private String image;

    @SerializedName("Name")
    private String name;

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return
                "ProductItem{" +
                        "description = '" + description + '\'' +
                        ",price = '" + price + '\'' +
                        ",quantity = '" + quantity + '\'' +
                        ",id = '" + id + '\'' +
                        ",unit = '" + unit + '\'' +
                        ",image = '" + image + '\'' +
                        ",name = '" + name + '\'' +
                        "}";
    }
}