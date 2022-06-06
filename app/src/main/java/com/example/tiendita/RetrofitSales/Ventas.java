package com.example.tiendita.RetrofitSales;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Ventas implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("folioventa")
    private String folioventa;
    @SerializedName("cantidad_productos")
    private String catidad_productos;
    @SerializedName("total")
    private String total;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getFolioventa() {
        return folioventa;
    }

    public void setFolioventa(String folioventa) {
        this.folioventa = folioventa;
    }

    public String getCatidad_productos() {
        return catidad_productos;
    }

    public void setCatidad_productos(String catidad_productos) {
        this.catidad_productos = catidad_productos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return getFolioventa();
    }
}
