package com.example.tiendita.RetrofitProd;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Productos implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("producto")
    private String producto;
    @SerializedName("precio")
    private String precio;
    @SerializedName("descripcion")
    private String descripcion;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return getProducto();
    }
}
