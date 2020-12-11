package com.vingcoz.devaenterprise.Model.order;

import com.google.gson.annotations.SerializedName;


public class OrderResponse{

	@SerializedName("OrderSummaryId")
	private int orderSummaryId;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setOrderSummaryId(int orderSummaryId){
		this.orderSummaryId = orderSummaryId;
	}

	public int getOrderSummaryId(){
		return orderSummaryId;
	}

	public void setError(boolean error){
		this.error = error;
	}

	public boolean isError(){
		return error;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	@Override
 	public String toString(){
		return 
			"OrderResponse{" + 
			"orderSummaryId = '" + orderSummaryId + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}