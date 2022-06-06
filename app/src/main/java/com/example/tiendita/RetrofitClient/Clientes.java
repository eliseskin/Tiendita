package com.example.tiendita.RetrofitClient;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clientes implements Serializable {
    @SerializedName("id")
    private String mId;
    @SerializedName("nombre")
    private String nombre;
    @SerializedName("direccion")
    private String direccion;
    @SerializedName("telefono")
    private String telefono;
    @SerializedName("correo")
    private String correo;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return getNombre();
    }
}
