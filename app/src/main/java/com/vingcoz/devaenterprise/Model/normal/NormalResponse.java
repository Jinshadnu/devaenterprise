package com.vingcoz.devaenterprise.Model.normal;

import com.google.gson.annotations.SerializedName;


public class NormalResponse {

    @SerializedName("error")
    private boolean error;

    @SerializedName("message")
    private String message;

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
                "NormalResponse{" +
                        "error = '" + error + '\'' +
                        ",message = '" + message + '\'' +
                        "}";
    }
}