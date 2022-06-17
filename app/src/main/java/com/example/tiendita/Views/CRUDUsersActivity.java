package com.example.tiendita.Views;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.Category.CategoriaActivity;
import com.example.tiendita.Helpers.Utils;
import com.example.tiendita.R;
import com.example.tiendita.Retrofit.ResponseModelUser;
import com.example.tiendita.Retrofit.RestApiUsers;
import com.example.tiendita.Retrofit.Usuarios;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CRUDUsersActivity extends AppCompatActivity {
    private EditText usuarioTxt, passwordTxt, nombreTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String id = null;
    private Usuarios receivedUsuarios;
    private Context c = CRUDUsersActivity.this;

    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSave);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxt);
        usuarioTxt = findViewById(R.id.usuarioTxt);
        passwordTxt = findViewById(R.id.passwordTxt);
        nombreTxt = findViewById(R.id.nombreTxt);
    }

    private void insertData() {
        String usuario, password, nombre;
        if (Utils.validateUser(usuarioTxt, passwordTxt, nombreTxt)) {
            usuario = usuarioTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nombre = nombreTxt.getText().toString();

            RestApiUsers api = Utils.getClient().create(RestApiUsers.class);
            Call<ResponseModelUser> insertData = api.insertData("INSERT", usuario, password, nombre);
            Utils.showProgressBar(mProgressBar);

            insertData.enqueue(new Callback<ResponseModelUser>() {
                @Override
                public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                    Log.d("RETROFIT", "response :" +response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Utils.show(c, "SUCCESS: \n 1. Agregado Exitoso. \n 2. ResponseCode: "+myResponseCode);
                        Utils.openActivity(c, UsuariosActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        Utils.showInfoDialog(CRUDUsersActivity.this, "UNSUCCESSFULL",
                                "Responde bien. \n 1. CONEXION A SERVIDOR EXITOSA \n 2. INSERTAMOS"+
                                " DATOS EN POSICION PERO SE ENCONTRO ResponseCode: " + myResponseCode+
                                " \n 3. Mas probable es problema es el codigo PHP.");
                    } else if ( myResponseCode.equalsIgnoreCase("3")) {
                        Utils.showInfoDialog(CRUDUsersActivity.this, "NO MYSQL CONNECTION",
                                "Tu codigo PHP"+
                                " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                    }
                    Utils.hideProgressBar(mProgressBar);
                }

                @Override
                public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                    Utils.hideProgressBar(mProgressBar);
                    Utils.showInfoDialog(CRUDUsersActivity.this, "FAILURE",
                            "Fallo durante la Insercion." + " ERROR Message: "+ t.getMessage());
                }
            });
        }
    }

    private void updateData() {
        String usuario, password, nombre;
        if (Utils.validateUser(usuarioTxt, passwordTxt, nombreTxt)) {
            usuario = usuarioTxt.getText().toString();
            password = passwordTxt.getText().toString();
            nombre = nombreTxt.getText().toString();

            Utils.showProgressBar(mProgressBar);
            RestApiUsers api = Utils.getClient().create(RestApiUsers.class);
            Call<ResponseModelUser> update = api.updateData("UPDATE", id, usuario, password, nombre);
            update.enqueue(new Callback<ResponseModelUser>() {
                @Override
                public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                    Log.d("RETRORFIT", "Response: "+response.body().getResult());
                    Utils.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Utils.show(c, response.body().getMessage());
                        Utils.openActivity(c, UsuariosActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        Utils.showInfoDialog(CRUDUsersActivity.this, "UNSUCCESSFULL",
                                "Responde bien."+
                                " ACTUALIZAMOS DATOS EN POSICION PERO SE ENCONTRO ResponseCode: "+myResponseCode+
                                " \n 3. Mas probable es problema es el codigo PHP.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        Utils.showInfoDialog(CRUDUsersActivity.this, "NO MYSQL CONNECTION",
                                "Tu codigo PHP"+
                                        " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                    }
                }

                @Override
                public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                    Utils.hideProgressBar(mProgressBar);
                    Utils.showInfoDialog(CRUDUsersActivity.this, "FAILURE",
                            "Fallo durante la Actualizacion." + " ERROR Message: "+ t.getMessage());
                }
            });
        }
    }

    private void deleteData() {
        RestApiUsers api = Utils.getClient().create(RestApiUsers.class);
        Call<ResponseModelUser> del = api.remove("DELETE", id);

        Utils.showProgressBar(mProgressBar);
        del.enqueue(new Callback<ResponseModelUser>() {
            @Override
            public void onResponse(Call<ResponseModelUser> call, Response<ResponseModelUser> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: "+response.body());
                Utils.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Utils.show(c, response.body().getMessage());
                    Utils.openActivity(c, UsuariosActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    Utils.showInfoDialog(CRUDUsersActivity.this, "UNSUCCESSFULL",
                            "Responde bien. \n 1. CONEXION A SERVIDOR EXITOSA \n 2. INSERTAMOS"+
                                    " DATOS EN POSICION PERO SE ENCONTRO ResponseCode: " + myResponseCode+
                                    " \n 3. Mas probable es problema es el codigo PHP.");
                } else if (myResponseCode.equalsIgnoreCase("3")) {
                    Utils.showInfoDialog(CRUDUsersActivity.this, "NO MYSQL CONNECTION",
                            "Tu codigo PHP"+
                                    " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                }
            }

            @Override
            public void onFailure(Call<ResponseModelUser> call, Throwable t) {
                Utils.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                Utils.showInfoDialog(CRUDUsersActivity.this, "FAILURE",
                        "Fallo durante la Eliminacion." + " ERROR Message: "+ t.getMessage());
            }
        });
    }

    @Override
    public void onBackPressed() {
        Utils.showInfoDialog(this, "Warning", "Esta seguro de salir?");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (receivedUsuarios == null) {
            getMenuInflater().inflate(R.menu.new_item_user, menu);
            headerTxt.setText("Agrega nuevo usuario");
        } else {
            getMenuInflater().inflate(R.menu.edit_item_user, menu);
            headerTxt.setText("Edita exitente usuario");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertUserItem:
                if (com.example.tiendita.Helpers.Utils.helper.userAdmin() == 0) {
                    Utils.show(c, "Debe de ser administrador para esta operacion");
                    Utils.openActivity(c, CategoriaActivity.class);
                    finish();
                } else {
                    insertData();
                }
                return true;
            case R.id.editUserItem:
                if (com.example.tiendita.Helpers.Utils.helper.userAdmin() == 0) {
                    Utils.show(c, "Debe de ser administrador para esta operacion");
                    Utils.openActivity(c, CategoriaActivity.class);
                    finish();
                } else {
                    if (receivedUsuarios != null) {
                        updateData();
                    } else {
                        Utils.show(this, "Editar funciona en modo editar");
                    }
                }
                return true;
            case R.id.deleteUserItem:
                if (com.example.tiendita.Helpers.Utils.helper.userAdmin() == 0) {
                    Utils.show(c, "Debe de ser administrador para esta operacion");
                    Utils.openActivity(c, CategoriaActivity.class);
                    finish();
                } else {
                    if (receivedUsuarios != null) {
                        deleteData();
                    } else {
                        Utils.show(this, "Eliminar funciona en modo eliminar");
                    }
                }
            case R.id.viewAllUserItem:
                Utils.openActivity(this, UsuariosActivity.class);
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onResume() {
        super.onResume();
        Object o = Utils.receiveUsuarios(getIntent(), c);
        if (o != null) {
            receivedUsuarios = (Usuarios) o;
            id = receivedUsuarios.getId();
            usuarioTxt.setText(receivedUsuarios.getUsuario());
            passwordTxt.setText(receivedUsuarios.getPassword());
            nombreTxt.setText(receivedUsuarios.getNombre());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        this.initializeWidgets();
    }

}
