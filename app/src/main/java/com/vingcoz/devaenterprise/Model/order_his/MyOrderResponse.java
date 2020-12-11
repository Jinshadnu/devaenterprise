package com.vingcoz.devaenterprise.Model.order_his;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class MyOrderResponse {

    @SerializedName("items")
    private List<MyOrderItemsItem> myOrderItems;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setMyOrderItems(List<MyOrderItemsItem> myOrderItems) {
        this.myOrderItems = myOrderItems;
    }

    public List<MyOrderItemsItem> getMyOrderItems() {
        return myOrderItems;
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
                "MyOrderResponse{" +
                        "myOrderItems = '" + myOrderItems + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}