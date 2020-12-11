package com.vingcoz.devaenterprise.Model.category;

import com.google.gson.annotations.SerializedName;

public class CategoryListItem {

    @SerializedName("Categories")
    private String categories;

    @SerializedName("Image")
    private String image;

    @SerializedName("id")
    private String id;

    public void setCategories(String categories) {
        this.categories = categories;
    }

    public String getCategories() {
        return categories;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return
                "CategoryListItem{" +
                        "categories = '" + categories + '\'' +
                        ",image = '" + image + '\'' +
                        ",id = '" + id + '\'' +
                        "}";
    }
}