package com.example.tiendita.RetrofitCategory;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("result")
    List<Categorias> categorias;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public List<Categorias> getResult() {
        return categorias;
    }

    public void setResult(List<Categorias> categorias) {
        this.categorias = categorias;
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
