package com.vingcoz.devaenterprise.Model.login;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class LoginResponse {

    @SerializedName("details")
    private List<LoginDataItem> loginDataItem;

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

    public void setLoginDataItem(List<LoginDataItem> loginDataItem) {
        this.loginDataItem = loginDataItem;
    }

    public List<LoginDataItem> getLoginDataItem() {
        return loginDataItem;
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
                "LoginResponse{" +
                        "loginDataItem = '" + loginDataItem + '\'' +
                        ",error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}