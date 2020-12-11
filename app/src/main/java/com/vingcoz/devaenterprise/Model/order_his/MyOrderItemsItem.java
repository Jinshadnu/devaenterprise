package com.vingcoz.devaenterprise.Model.order_his;


import com.google.gson.annotations.SerializedName;

public class MyOrderItemsItem{

	@SerializedName("CategoryId")
	private int categoryId;

	@SerializedName("Category")
	private String category;

	@SerializedName("Price")
	private double price;

	@SerializedName("Product")
	private String product;

	@SerializedName("Quantity")
	private int quantity;

	@SerializedName("ProductId")
	private int productId;

	@SerializedName("Image")
	private String image;

	public void setCategoryId(int categoryId){
		this.categoryId = categoryId;
	}

	public int getCategoryId(){
		return categoryId;
	}

	public void setCategory(String category){
		this.category = category;
	}

	public String getCategory(){
		return category;
	}

	public void setPrice(double price){
		this.price = price;
	}

	public double getPrice(){
		return price;
	}

	public void setProduct(String product){
		this.product = product;
	}

	public String getProduct(){
		return product;
	}

	public void setQuantity(int quantity){
		this.quantity = quantity;
	}

	public int getQuantity(){
		return quantity;
	}

	public void setProductId(int productId){
		this.productId = productId;
	}

	public int getProductId(){
		return productId;
	}

	public void setImage(String image){
		this.image = image;
	}

	public String getImage(){
		return image;
	}

	@Override
 	public String toString(){
		return 
			"MyOrderItemsItem{" + 
			"categoryId = '" + categoryId + '\'' + 
			",category = '" + category + '\'' + 
			",price = '" + price + '\'' + 
			",product = '" + product + '\'' + 
			",quantity = '" + quantity + '\'' + 
			",productId = '" + productId + '\'' + 
			",image = '" + image + '\'' + 
			"}";
		}
}