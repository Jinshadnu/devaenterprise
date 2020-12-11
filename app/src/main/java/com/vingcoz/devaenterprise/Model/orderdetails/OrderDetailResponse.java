package com.vingcoz.devaenterprise.Model.orderdetails;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class OrderDetailResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    @SerializedName("items")
    private List<OrderItemsDetailsItems> items;

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

    public void setItems(List<OrderItemsDetailsItems> items) {
        this.items = items;
    }

    public List<OrderItemsDetailsItems> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return
                "OrderDetailResponse{" +
                        "error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        ",items = '" + items + '\'' +
                        "}";
    }
}