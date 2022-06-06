package com.example.tiendita.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.Helpers.Utils;
import com.example.tiendita.R;
import com.example.tiendita.Retrofit.Usuarios;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

@SuppressWarnings("deprecation")
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView usuario, nombre;
    private FloatingActionButton editFAB;
    private Usuarios receivedUsuarios;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets() {
        usuario = findViewById(R.id.usuario);
        nombre = findViewById(R.id.nombre);
        editFAB = findViewById(R.id.editFAB);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout = findViewById(R.id.mCollapsingToolbarLayout);
    }

    private void receiveAndShowData() {
        receivedUsuarios = Utils.receiveUsuarios(getIntent(), DetailActivity.this);

        if (receivedUsuarios != null) {
            usuario.setText(receivedUsuarios.getUsuario());
            nombre.setText(receivedUsuarios.getNombre());

            mCollapsingToolbarLayout.setTitle(receivedUsuarios.getNombre());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                Utils.sendUsuariosActivity(this, receivedUsuarios, CRUDUsersActivity.class);
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
        if (id == R.id.editFAB) {
            Utils.sendUsuariosActivity(this, receivedUsuarios, CRUDUsersActivity.class);
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
        setContentView(R.layout.activity_detail);

        initializeWidgets();
        receiveAndShowData();
    }
}
