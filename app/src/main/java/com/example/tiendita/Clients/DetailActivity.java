package com.example.tiendita.Clients;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.HelpersClient.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitClient.Clientes;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

@SuppressWarnings("deprecation")
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView nombre, direccion, telefono, correo;
    private FloatingActionButton editFAB;
    private Clientes receivedClientes;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets() {
        nombre = findViewById(R.id.nombrec);
        direccion = findViewById(R.id.direccion);
        telefono = findViewById(R.id.telefono);
        correo = findViewById(R.id.correo);
        editFAB = findViewById(R.id.editFABCL);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout = findViewById(R.id.mCollapsingToolbarLayoutCl);
    }

    private void receiveAndShowData() {
        receivedClientes = Utils.receiveClientes(getIntent(), DetailActivity.this);

        if (receivedClientes != null) {
            nombre.setText(receivedClientes.getNombre());
            direccion.setText(receivedClientes.getDireccion());
            telefono.setText(receivedClientes.getTelefono());
            correo.setText(receivedClientes.getCorreo());

            mCollapsingToolbarLayout.setTitle(receivedClientes.getNombre());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_clientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editCl:
                Utils.sendClientesActivity(this, receivedClientes, CRUDActivity.class);
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
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.editFABCL) {
            Utils.sendClientesActivity(this, receivedClientes, CRUDActivity.class);
            finish();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_clientes);

        initializeWidgets();
        receiveAndShowData();
    }
}
