package com.example.tiendita.Products;

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

import com.example.tiendita.HelpersProd.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitProd.ResponseModel;
import com.example.tiendita.RetrofitProd.RestApi;
import com.example.tiendita.RetrofitProd.Productos;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CRUDActivity extends AppCompatActivity {
    private EditText productoTxt, precioTxt, descripcionTxt;
    private TextView headerTxt;
    private ProgressBar mProgressBar;
    private String id = null;
    private Productos receivedProductos;
    private Context c = CRUDActivity.this;

    private void initializeWidgets() {
        mProgressBar = findViewById(R.id.mProgressBarSaveP);
        mProgressBar.setIndeterminate(true);
        mProgressBar.setVisibility(View.GONE);

        headerTxt = findViewById(R.id.headerTxtP);
        productoTxt = findViewById(R.id.productoTxt);
        precioTxt = findViewById(R.id.precioTxt);
        descripcionTxt = findViewById(R.id.descripcionTxt);
    }

    private void insertData() {
        String producto, precio, descripcion;
        if (Utils.validateProd(productoTxt, precioTxt, descripcionTxt)) {
            producto = productoTxt.getText().toString();
            precio = precioTxt.getText().toString();
            descripcion = descripcionTxt.getText().toString();

            RestApi api = Utils.getClient().create(RestApi.class);
            Call<ResponseModel> insertData = api.insertData("INSERT", producto, precio, descripcion);
            Utils.showProgressBar(mProgressBar);

            insertData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    Log.d("RETROFIT", "response :" +response.body().toString());
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equals("1")) {
                        Utils.show(c, "SUCCESS: \n 1. Agregado Exitoso. \n 2. ResponseCode: "+myResponseCode);
                        Utils.openActivity(c, ProductosActivity.class);
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        Utils.showInfoDialog(CRUDActivity.this, "UNSUCCESSFULL",
                                "Responde bien. \n 1. CONEXION A SERVIDOR EXITOSA \n 2. INSERTAMOS"+
                                        " DATOS EN POSICION PERO SE ENCONTRO ResponseCode: " + myResponseCode+
                                        " \n 3. Mas probable es problema es el codigo PHP.");
                    } else if ( myResponseCode.equalsIgnoreCase("3")) {
                        Utils.showInfoDialog(CRUDActivity.this, "NO MYSQL CONNECTION",
                                "Tu codigo PHP"+
                                        " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                    }
                    Utils.hideProgressBar(mProgressBar);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                    Utils.hideProgressBar(mProgressBar);
                    Utils.showInfoDialog(CRUDActivity.this, "FAILURE",
                            "Fallo durante la Insercion." + " ERROR Message: "+ t.getMessage());
                }
            });
        }
    }

    private void updateData() {
        String producto, precio, descripcion;
        if (Utils.validateProd(productoTxt, precioTxt, descripcionTxt)) {
            producto = productoTxt.getText().toString();
            precio = precioTxt.getText().toString();
            descripcion = descripcionTxt.getText().toString();

            Utils.showProgressBar(mProgressBar);
            RestApi api = Utils.getClient().create(RestApi.class);
            Call<ResponseModel> update = api.updateData("UPDATE", id, producto, precio, descripcion);
            update.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    Log.d("RETRORFIT", "Response: "+response.body().getResult());Utils.hideProgressBar(mProgressBar);
                    String myResponseCode = response.body().getCode();

                    if (myResponseCode.equalsIgnoreCase("1")) {
                        Utils.show(c, response.body().getMessage());
                        Utils.openActivity(c, ProductosActivity.class);
                        finish();
                    } else if (myResponseCode.equalsIgnoreCase("2")) {
                        Utils.showInfoDialog(CRUDActivity.this, "UNSUCCESSFULL",
                                "Responde bien."+
                                        " ACTUALIZAMOS DATOS EN POSICION PERO SE ENCONTRO ResponseCode: "+myResponseCode+
                                        " \n 3. Mas probable es problema es el codigo PHP.");
                    } else if (myResponseCode.equalsIgnoreCase("3")) {
                        Utils.showInfoDialog(CRUDActivity.this, "NO MYSQL CONNECTION",
                                "Tu codigo PHP"+
                                        " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                    }
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                    Utils.hideProgressBar(mProgressBar);
                    Utils.showInfoDialog(CRUDActivity.this, "FAILURE",
                            "Fallo durante la Actualizacion." + " ERROR Message: "+ t.getMessage());
                }
            });
        }
    }

    private void deleteData() {
        RestApi api = Utils.getClient().create(RestApi.class);
        Call<ResponseModel> del = api.remove("DELETE", id);

        Utils.showProgressBar(mProgressBar);
        del.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                Log.d("RETROFIT", "DELETE RESPONSE: "+response.body());
                Utils.hideProgressBar(mProgressBar);
                String myResponseCode = response.body().getCode();

                if (myResponseCode.equalsIgnoreCase("1")) {
                    Utils.show(c, response.body().getMessage());
                    Utils.openActivity(c, ProductosActivity.class);
                    finish();
                } else if (myResponseCode.equalsIgnoreCase("2")) {
                    Utils.showInfoDialog(CRUDActivity.this, "UNSUCCESSFULL",
                            "Responde bien. \n 1. CONEXION A SERVIDOR EXITOSA \n 2. INSERTAMOS"+
                                    " DATOS EN POSICION PERO SE ENCONTRO ResponseCode: " + myResponseCode+
                                    " \n 3. Mas probable es problema es el codigo PHP.");
                } else if (myResponseCode.equalsIgnoreCase("3")) {
                    Utils.showInfoDialog(CRUDActivity.this, "NO MYSQL CONNECTION",
                            "Tu codigo PHP"+
                                    " es incapaz de conectarse a base de datos mysql. Asegurese de poner correctamente los datos dase de datos");
                }
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Utils.hideProgressBar(mProgressBar);
                Log.d("RETROFIT", "ERROR: "+ t.getMessage());
                Utils.showInfoDialog(CRUDActivity.this, "FAILURE",
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
        if (receivedProductos == null) {
            getMenuInflater().inflate(R.menu.new_item_prod, menu);
            headerTxt.setText("Agrega nuevo producto");
        } else {
            getMenuInflater().inflate(R.menu.edit_item_prod, menu);
            headerTxt.setText("Edita exitente producto");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.insertUserItemP:
                insertData();
                return true;
            case R.id.editUserItemP:
                if (receivedProductos != null) {
                    updateData();
                } else {
                    Utils.show(this, "Editar funciona en modo editar");
                }
                return true;
            case R.id.deleteUserItemP:
                if (receivedProductos != null) {
                    deleteData();
                } else {
                    Utils.show(this, "Eliminar funciona en modo eliminar");
                }
            case R.id.viewAllUserItemP:
                Utils.openActivity(this, ProductosActivity.class);
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
        Object o = Utils.receiveProductos(getIntent(), c);
        if (o != null) {
            receivedProductos = (Productos) o;
            id = receivedProductos.getId();
            productoTxt.setText(receivedProductos.getProducto());
            precioTxt.setText(receivedProductos.getPrecio());
            descripcionTxt.setText(receivedProductos.getDescripcion());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud_prod);

        this.initializeWidgets();
    }
}
