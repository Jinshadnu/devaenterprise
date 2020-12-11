package com.vingcoz.devaenterprise.Model.wishlist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class WishResponse{

	@SerializedName("items")
	private List<WishItems> wishItems;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setWishItems(List<WishItems> wishItems){
		this.wishItems = wishItems;
	}

	public List<WishItems> getWishItems(){
		return wishItems;
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
			"WishResponse{" + 
			"wishItems = '" + wishItems + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}