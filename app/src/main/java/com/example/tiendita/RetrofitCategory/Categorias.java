package com.example.tiendita.RetrofitCategory;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Categorias implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("categoria")
    private String categoria;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return getCategoria();
    }
}
