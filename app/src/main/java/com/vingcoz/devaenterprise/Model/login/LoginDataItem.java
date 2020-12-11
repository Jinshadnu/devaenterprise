package com.vingcoz.devaenterprise.Model.login;

import com.google.gson.annotations.SerializedName;

public class LoginDataItem {

	@SerializedName("Mobile No.")
	private String mobileNo;

	@SerializedName("E-Mail")
	private String eMail;

	@SerializedName("Address")
	private String address;

	@SerializedName("ID")
	private int iD;

	@SerializedName("Name")
	private String name;

	public void setMobileNo(String mobileNo){
		this.mobileNo = mobileNo;
	}

	public String getMobileNo(){
		return mobileNo;
	}

	public void setEMail(String eMail){
		this.eMail = eMail;
	}

	public String getEMail(){
		return eMail;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getAddress(){
		return address;
	}

	public void setID(int iD){
		this.iD = iD;
	}

	public int getID(){
		return iD;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	@Override
 	public String toString(){
		return 
			"LoginDataItemItem{" + 
			"mobile No. = '" + mobileNo + '\'' + 
			",e-Mail = '" + eMail + '\'' + 
			",address = '" + address + '\'' + 
			",iD = '" + iD + '\'' + 
			",name = '" + name + '\'' + 
			"}";
		}
}