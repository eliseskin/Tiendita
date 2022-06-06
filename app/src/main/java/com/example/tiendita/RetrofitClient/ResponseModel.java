package com.example.tiendita.RetrofitClient;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModel {
    @SerializedName("result")
    List<Clientes> clientes;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public List<Clientes> getResult() {
        return clientes;
    }

    public void setResult(List<Clientes> clientes) {
        this.clientes = clientes;
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
