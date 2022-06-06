package com.example.tiendita.RetrofitProd;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @GET("productos.php")
    Call<ResponseModel> retrieve();

    @FormUrlEncoded
    @POST("productos.php")
    Call<ResponseModel> insertData(@Field("action") String action,
                                       @Field("producto") String producto,
                                       @Field("precio") String precio,
                                       @Field("descripcion") String descripcion);

    @FormUrlEncoded
    @POST("productos.php")
    Call<ResponseModel> updateData(@Field("action") String action,
                                       @Field("id") String id,
                                       @Field("producto") String producto,
                                       @Field("precio") String precio,
                                       @Field("descripcion") String descripcion);

    @FormUrlEncoded
    @POST("productos.php")
    Call<ResponseModel> search(@Field("action") String action,
                                   @Field("query") String query,
                                   @Field("start") String start,
                                   @Field("limit") String limit);

    @FormUrlEncoded
    @POST("productos.php")
    Call<ResponseModel> remove(@Field("action") String action, @Field("id") String id);
}
