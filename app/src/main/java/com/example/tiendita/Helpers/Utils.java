package com.example.tiendita.Helpers;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.tiendita.R;
import com.example.tiendita.Retrofit.Usuarios;

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

    public static boolean validateUser(EditText... editTexts) {
        EditText usuarioTxt = editTexts[0];
        EditText passwordTxt = editTexts[1];
        EditText nombreTxt = editTexts[2];

        if (usuarioTxt.getText() == null || usuarioTxt.getText().toString().isEmpty()) {
            usuarioTxt.setError("Usuario es requerido!");
            return false;
        }
        if (passwordTxt.getText() == null || passwordTxt.getText().toString().isEmpty()) {
            passwordTxt.setError("Password es requerido!");
            return false;
        }
        if (nombreTxt.getText() == null || nombreTxt.getText().toString().isEmpty()) {
            nombreTxt.setError("Nombre es requerido!");
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

    public static void sendUsuariosActivity(Context c, Usuarios usuarios, Class clazz) {
        Intent i = new Intent(c,clazz);
        i.putExtra("USUARIOS_KEY", usuarios);
        c.startActivity(i);
    }

    public static Usuarios receiveUsuarios(Intent intent, Context c) {
        try {
            Usuarios usuarios = (Usuarios) intent.getSerializableExtra("USUARIOS_KEY");
            return usuarios;
        } catch (Exception e) {
            e.printStackTrace();
            show(c, "RECIVIENDO-USUARIOS ERROR: "+e.getMessage());
        }
        return null;
    }

}
