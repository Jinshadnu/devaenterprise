package com.vingcoz.devaenterprise.Model.category;

import java.util.List;
import com.google.gson.annotations.SerializedName;


public class CategoryResponse{

	@SerializedName("category")
	private List<CategoryListItem> categoryList;

	@SerializedName("error")
	private boolean error;

	@SerializedName("message")
	private String message;

	public void setCategoryList(List<CategoryListItem> categoryList){
		this.categoryList = categoryList;
	}

	public List<CategoryListItem> getCategoryList(){
		return categoryList;
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
			"CategoryResponse{" + 
			"categoryList = '" + categoryList + '\'' + 
			",error = '" + error + '\'' + 
			",message = '" + message + '\'' + 
			"}";
		}
}