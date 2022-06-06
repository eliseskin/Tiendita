package com.example.tiendita;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendita.Helpers.Utils;
import com.example.tiendita.Retrofit.ResponseModelUser;
import com.example.tiendita.Retrofit.RestApiUsers;
import com.example.tiendita.Views.DashboardActivity;
import com.rengwuxian.materialedittext.MaterialEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@SuppressWarnings("deprecation")
public class MainActivity extends AppCompatActivity {

    MaterialEditText user, pass;
    Button login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = findViewById(R.id.user);
        pass = findViewById(R.id.pass);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tex_user = user.getText().toString();
                String tex_password = pass.getText().toString();
                if (TextUtils.isEmpty(tex_user) || TextUtils.isEmpty(tex_password)) {
                    Toast.makeText(MainActivity.this, "Campos Incompletos", Toast.LENGTH_LONG).show();
                } else {
                    login(tex_user, tex_password);
                }
            }
        });
    }
    private void login(final String user, final String pass) {
        final ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Accediendo a cuenta");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();

        RestApiUsers api = Utils.getClient().create(RestApiUsers.class);
        Call<ResponseModelUser> update = api.login("LOGIN", user, pass);
        update.enqueue(new Callback<ResponseModelUser>() {
            @Override
            public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                Log.d("RETRORFIT", "Response: "+response.body().getResult());
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Utils.show(MainActivity.this, response.body().getMessage());
                    Utils.openActivity(MainActivity.this, DashboardActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    Utils.showInfoDialog(MainActivity.this, "UNSUCCESSFULL",
                            "Responde bien."+
                                    " DATOS DE USUARIO INCORRECTOS ResponseCode: "+myResponseCode);
                } else if (myResponseCode.equalsIgnoreCase("3")) {
                    Utils.showInfoDialog(MainActivity.this, "NO MYSQL CONNECTION",
                            "Tu codigo PHP"+
                                    " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                }
            }

            @Override
            public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                Utils.showInfoDialog(MainActivity.this, "FAILURE",
                        "Fallo durante la Actualizacion." + " ERROR Message: "+ t.getMessage());
            }
        });
    }

}