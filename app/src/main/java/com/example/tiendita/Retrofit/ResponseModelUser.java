package com.example.tiendita.Retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ResponseModelUser {
    @SerializedName("result")
    List<Usuarios> usuarios;
    @SerializedName("code")
    private String code;
    @SerializedName("message")
    private String message;

    public List<Usuarios> getResult() {
        return usuarios;
    }

    public void setResult(List<Usuarios> usuarios) {
        this.usuarios = usuarios;
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
