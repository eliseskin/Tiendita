package com.example.tiendita.RetrofitCategory;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @GET("categorias.php")
    Call<ResponseModel> retrieve();

    @FormUrlEncoded
    @POST("categorias.php")
    Call<ResponseModel> insertData(@Field("action") String action,
                                       @Field("categoria") String categoria);

    @FormUrlEncoded
    @POST("categorias.php")
    Call<ResponseModel> updateData(@Field("action") String action,
                                       @Field("id") String id,
                                       @Field("categoria") String categoria);

    @FormUrlEncoded
    @POST("categorias.php")
    Call<ResponseModel> search(@Field("action") String action,
                                   @Field("query") String query,
                                   @Field("start") String start,
                                   @Field("limit") String limit);

    @FormUrlEncoded
    @POST("categorias.php")
    Call<ResponseModel> remove(@Field("action") String action, @Field("id") String id);
}
