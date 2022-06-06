package com.example.tiendita.RetrofitSales;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @GET("ventas.php")
    Call<ResponseModel> retrieve();

    @FormUrlEncoded
    @POST("ventas.php")
    Call<ResponseModel> insertData(@Field("action") String action,
                                       @Field("folioventa") String folioventa,
                                       @Field("cantidad_productos") String cantidad_productos,
                                       @Field("total") String total);

    @FormUrlEncoded
    @POST("ventas.php")
    Call<ResponseModel> updateData(@Field("action") String action,
                                       @Field("id") String id,
                                       @Field("folioventa") String folioventa,
                                       @Field("cantidad_productos") String cantidad_productos,
                                       @Field("total") String total);

    @FormUrlEncoded
    @POST("ventas.php")
    Call<ResponseModel> search(@Field("action") String action,
                                   @Field("query") String query,
                                   @Field("start") String start,
                                   @Field("limit") String limit);

    @FormUrlEncoded
    @POST("ventas.php")
    Call<ResponseModel> remove(@Field("action") String action, @Field("id") String id);
}
