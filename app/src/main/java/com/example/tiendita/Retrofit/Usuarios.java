package com.example.tiendita.Retrofit;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class Usuarios implements Serializable {
    /**
     * Let' now come define instance fields for this class. We decorate them with
     * @SerializedName
     * attribute. Through this we are specifying the keys in our json data.
     */
    @SerializedName("id")
    private String mId;
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("password")
    private String password;
    @SerializedName("nombre")
    private String nombre;

    /**
     * Let's now come define our getter and setter methods.
     */
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @Override
    public String toString() {
        return getNombre();
    }
}
