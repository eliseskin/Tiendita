package com.example.tiendita.RetrofitProd;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("result")
    List<Productos> productos;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public List<Productos> getResult() {
        return productos;
    }

    public void setResult(List<Productos> productos) {
        this.productos = productos;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
