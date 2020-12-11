package com.vingcoz.devaenterprise.Model.ordersum;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class OrderSummaryResponse{

	@SerializedName("OrderSummary")
	private List<OrderSummaryItem> orderSummary;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setOrderSummary(List<OrderSummaryItem> orderSummary){
		this.orderSummary = orderSummary;
	}

	public List<OrderSummaryItem> getOrderSummary(){
		return orderSummary;
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
			"OrderSummaryResponse{" + 
			"orderSummary = '" + orderSummary + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}