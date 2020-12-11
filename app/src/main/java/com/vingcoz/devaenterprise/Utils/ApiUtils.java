package com.vingcoz.devaenterprise.Utils;

import com.vingcoz.devaenterprise.Model.cart.CartResponse;
import com.vingcoz.devaenterprise.Model.category.CategoryResponse;
import com.vingcoz.devaenterprise.Model.login.LoginResponse;
import com.vingcoz.devaenterprise.Model.normal.NormalResponse;
import com.vingcoz.devaenterprise.Model.order.OrderResponse;
import com.vingcoz.devaenterprise.Model.orderdetails.OrderDetailResponse;
import com.vingcoz.devaenterprise.Model.ordersum.OrderSummaryResponse;
import com.vingcoz.devaenterprise.Model.product.ProductResponse;
import com.vingcoz.devaenterprise.Model.wishlist.WishResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiUtils {

    public String MATERIAL_LOCATION = "Materials/";

    //    @FormUrlEncoded
//    @Headers({"Content-Type: application/x-www-form-urlencoded"})
//    @POST("LoginM")
//    Call<LoginResponse> PostLogin(@Field("Email") String Email, @Field("password") String Password);
//
//
    @FormUrlEncoded
    @POST("Customer_registration")
    Call<NormalResponse> CreateAccount(@Field("CUSTOMER_NAME") String strName,
                                       @Field("CUSTOMER_PHONE") String strMobile,
                                       @Field("CUSTOMER_EMAIL") String strEmail,
                                       @Field("CUSTOMER_PASSWORD") String strPassword,
                                       @Field("CUSTOMER_PLACE") String strPlace,
                                       @Field("CUSTOMER_ADDRESS") String strAddress,
                                       @Field("CUSTOMER_PIN") String strPin);


    @FormUrlEncoded
    @POST("customer_login")
    Call<LoginResponse> AccountLogin(@Field("CUSTOMER_CONTACT") String strMobile,
                                     @Field("CUSTOMER_PASSWORD") String strPassword,
                                     @Field("CUSTOMER_TOKEN") String strToken
    );

    @FormUrlEncoded
    @POST("change_password")
    Call<NormalResponse> ChangePassword(@Field("CUSTOMER_CONTACT") String strMobile,
                                        @Field("CUSTOMER_PASSWORD") String strOldPassword,
                                        @Field("newpass") String strNewPass);

    @FormUrlEncoded
    @POST("feedback")
    Call<NormalResponse> SubmitFeedback(@Field("CUSTOMER") String strCus,
                                        @Field("EMAIL") String strEmail,
                                        @Field("MESSAGE") String strMessage,
                                        @Field("DATETIME") String strDateTime
    );

    @FormUrlEncoded
    @POST("feedback")
    Call<NormalResponse> SubmitWishList(@Field("CUSTOMER_ID") Long CusID,
                                        @Field("PRODUCT_ID") Long PID
    );

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded"})
    @POST("products_category_wise")
    Call<ProductResponse> GetProductsInCategory(@Field("CATEGORY") Integer intCategory);

    @GET("Product_list")
    Call<ProductResponse> GetProducts();

    @GET("category_list")
    Call<CategoryResponse> GetCategories();

    @FormUrlEncoded
    @POST("product_details")
    Call<ProductResponse> GetProductDetails(@Field("PRODUCT_ID") Integer intCategory);

    @FormUrlEncoded
    @POST("search")
    Call<ProductResponse> SearchProduct(@Field("SEARCH_PRO") String strProductName);

    //Wish list

    //add
    @FormUrlEncoded
    @POST("add_to_wishlist")
    Call<NormalResponse> AddToWishList(@Field("CUSTOMER_ID") String strCustomer,
                                       @Field("PRODUCT_ID") long lngProduct);

    //list
    @FormUrlEncoded
    @POST("view_wishlist_items")
    Call<WishResponse> WishList(@Field("CUSTOMER_ID") String strCustomer);

    //remove
    @FormUrlEncoded
    @POST("remove_from_wishlist")
    Call<NormalResponse> RemoveWish(@Field("CUSTOMER_ID") String strCustomer,
                                    @Field("PRODUCT_ID") long lngProduct);

//Cart

    //Add
    @FormUrlEncoded
    @POST("add_to_cart")
    Call<NormalResponse> AddToCartList(@Field("CUSTOMER_ID") String strCustomer,
                                       @Field("PRODUCT_ID") long lngProduct);

    //List
    @FormUrlEncoded
    @POST("view_cart_items")
    Call<CartResponse> GetCartList(@Field("CUSTOMER_ID") String strCustomer);

    //Remove
    @FormUrlEncoded
    @POST("remove_from_cart")
    Call<NormalResponse> RemoveCart(@Field("CUSTOMER_ID") String strCustomer,
                                    @Field("PRODUCT_ID") long lngProduct);


//Order

    //Add
    @FormUrlEncoded
    @POST("ordersummary_insert")
    Call<OrderResponse> SaveOrderSummary(@Field("CUSTOMER_ID") String strCustomer,
                                         @Field("CUSTOMER_ADDRESS") String strAddress,
                                         @Field("CUSTOMER_PIN") String strPin,
                                         @Field("CUSTOMER_PHONE") String strPhone,
                                         @Field("PLACE_LATITUDE") String strLatitude,
                                         @Field("PLACE_LONGITUDE") String strLongitude,
                                         @Field("NET_AMOUNT") Double dblNetAmt,
                                         @Field("DATE_TIME") String strDtTime
    );


    //Add
    @FormUrlEncoded
    @POST("order_insert")
    Call<NormalResponse> SaveProducts(@Field("CUSTOMER_ID") String strCustomer,
                                      @Field("PRODUCT_ID") int intProductID,
                                      @Field("PRODUCT_MRP") double dblMrp,
                                      @Field("PRODUCT_QUANTITY") int intQty,
                                      @Field("ORDER_S_ID") int intoId
    );

    //list
    @FormUrlEncoded
    @POST("view_ordersummary")
    Call<OrderSummaryResponse> ViewOrderSummaryList(@Field("CUSTOMER_ID") String strCustomer);

    @FormUrlEncoded
    @POST("view_orders")
    Call<OrderDetailResponse> viewOrderDetails(@Field("CUSTOMER_ID") String strCustomer,
                                               @Field("ORDER_S_ID") String strOrderID);

}