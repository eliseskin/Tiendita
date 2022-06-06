package com.example.tiendita.RetrofitSales;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("result")
    List<Ventas> ventas;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public List<Ventas> getResult() {
        return ventas;
    }

    public void setResult(List<Ventas> ventas) {
        this.ventas = ventas;
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
