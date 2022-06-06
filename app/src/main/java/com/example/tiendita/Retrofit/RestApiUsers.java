package com.example.tiendita.Retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApiUsers {
    @GET("usuarios.php")
    Call<ResponseModelUser> retrieve();

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<ResponseModelUser> insertData(@Field("action") String action,
                                       @Field("usuario") String usuario,
                                       @Field("password") String password,
                                       @Field("nombre") String nombre);

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<ResponseModelUser> updateData(@Field("action") String action,
                                       @Field("id") String id,
                                       @Field("usuario") String usuario,
                                       @Field("password") String password,
                                       @Field("nombre") String nombre);

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<ResponseModelUser> search(@Field("action") String action,
                                   @Field("query") String query,
                                   @Field("start") String start,
                                   @Field("limit") String limit);

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<ResponseModelUser> remove(@Field("action") String action, @Field("id") String id);

    @FormUrlEncoded
    @POST("usuarios.php")
    Call<ResponseModelUser> login(@Field("action") String action,
                                  @Field("usuario") String usuario,
                                  @Field("password") String password);
}
