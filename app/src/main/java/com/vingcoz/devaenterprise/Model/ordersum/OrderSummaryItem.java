package com.vingcoz.devaenterprise.Model.ordersum;

import com.google.gson.annotations.SerializedName;

public class OrderSummaryItem {

    @SerializedName("OrderedDate")
    private String orderedDate;

    @SerializedName("OrderStatus")
    private String orderStatus;

    @SerializedName("MobileNo.")
    private String mobileNo;

    @SerializedName("Address")
    private String address;

    @SerializedName("TotalAmount")
    private int totalAmount;

    @SerializedName("OrderId")
    private int orderId;

    @SerializedName("PINCode")
    private String pINCode;

    public void setOrderedDate(String orderedDate) {
        this.orderedDate = orderedDate;
    }

    public String getOrderedDate() {
        return orderedDate;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setPINCode(String pINCode) {
        this.pINCode = pINCode;
    }

    public String getPINCode() {
        return pINCode;
    }

    @Override
    public String toString() {
        return
                "OrderSummaryItem{" +
                        "orderedDate = '" + orderedDate + '\'' +
                        ",orderStatus = '" + orderStatus + '\'' +
                        ",mobileNo. = '" + mobileNo + '\'' +
                        ",address = '" + address + '\'' +
                        ",totalAmount = '" + totalAmount + '\'' +
                        ",orderId = '" + orderId + '\'' +
                        ",pINCode = '" + pINCode + '\'' +
                        "}";
    }
}