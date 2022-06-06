package com.example.tiendita.RetrofitClient;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {
    @GET("clientes.php")
    Call<ResponseModel> retrieve();

    @FormUrlEncoded
    @POST("clientes.php")
    Call<ResponseModel> insertData(@Field("action") String action,
                                        @Field("nombre") String nombre,
                                        @Field("direccion") String direccion,
                                        @Field("telefono") String telefono,
                                        @Field("correo") String correo);


    @FormUrlEncoded
    @POST("clientes.php")
    Call<ResponseModel> updateData(@Field("action") String action,
                                       @Field("id") String id,
                                       @Field("nombre") String nombre,
                                       @Field("direccion") String direccion,
                                       @Field("telefono") String telefono,
                                       @Field("correo") String correo);

    @FormUrlEncoded
    @POST("clientes.php")
    Call<ResponseModel> search(@Field("action") String action,
                                   @Field("query") String query,
                                   @Field("start") String start,
                                   @Field("limit") String limit);

    @FormUrlEncoded
    @POST("clientes.php")
    Call<ResponseModel> remove(@Field("action") String action, @Field("id") String id);
}
