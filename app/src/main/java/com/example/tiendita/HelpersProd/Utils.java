package com.example.tiendita.HelpersProd;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendita.R;
import com.example.tiendita.RetrofitProd.Productos;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    private static final String base_url = "http://192.168.1.219/tiendita/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(base_url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

    public static void show(Context c, String message) {
        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }

    public static boolean validateProd(EditText... editTexts) {
        EditText productoTxt = editTexts[0];
        EditText precioTxt = editTexts[1];
        EditText descripcionTxt = editTexts[2];

        if (productoTxt.getText() == null || productoTxt.getText().toString().isEmpty()) {
            productoTxt.setError("Producto es requerido!");
            return false;
        }
        if (precioTxt.getText() == null || precioTxt.getText().toString().isEmpty()) {
            precioTxt.setError("Precio es requerido!");
            return false;
        }
        if (descripcionTxt.getText() == null || descripcionTxt.getText().toString().isEmpty()) {
            descripcionTxt.setError("Descripcion es requerido!");
            return false;
        }
        return true;
    }

    public static void clearEditTexts(EditText... editTexts) {
        for (EditText editText:editTexts) {
            editText.setText("");
        }
    }

    public static void openActivity(Context c, Class clazz) {
        Intent intent = new Intent(c, clazz);
        c.startActivity(intent);
    }

    public static void showInfoDialog(final AppCompatActivity activity, String title, String message) {
        new AlertDialog.Builder(activity)
                .setIcon(R.drawable.m_info)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("No", (dialog, which) -> {})
                //.setNeutralButton("Menu", (dialog, which) -> {openActivity(activity, DashboardActivity.class);})
                .setNegativeButton("Si", (dialog, which) -> activity.finish())
                .show();
    }

    public static void showProgressBar(ProgressBar pb) {
        pb.setVisibility(View.VISIBLE);
    }

    public static void hideProgressBar(ProgressBar pb) {
        pb.setVisibility(View.GONE);
    }

    public static void sendProductosActivity(Context c, Productos productos, Class clazz) {
        Intent i = new Intent(c,clazz);
        i.putExtra("PRODUCTOS_KEY", productos);
        c.startActivity(i);
    }

    public static Productos receiveProductos(Intent intent, Context c) {
        try {
            Productos productos = (Productos) intent.getSerializableExtra("PRODUCTOS_KEY");
            return productos;
        } catch (Exception e) {
            e.printStackTrace();
            show(c, "RECIVIENDO ERROR: "+e.getMessage());
        }
        return null;
    }
}
