package com.example.tiendita.Products;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.HelpersProd.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitProd.Productos;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

@SuppressWarnings("deprecation")
public class DetailActivity  extends AppCompatActivity implements View.OnClickListener {
    private TextView producto, precio, descripcion;
    private FloatingActionButton editFAB;
    private Productos receivedProductos;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets() {
        producto = findViewById(R.id.producto);
        precio = findViewById(R.id.preciop);
        descripcion = findViewById(R.id.descripcionp);
        editFAB = findViewById(R.id.editFABP);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout = findViewById(R.id.mCollapsingToolbarLayoutP);
    }

    private void receiveAndShowData() {
        receivedProductos = Utils.receiveProductos(getIntent(), DetailActivity.this);

        if (receivedProductos != null) {
            producto.setText(receivedProductos.getProducto());
            precio.setText(receivedProductos.getPrecio());
            descripcion.setText(receivedProductos.getDescripcion());

            mCollapsingToolbarLayout.setTitle(receivedProductos.getProducto());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_prod, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editP:
                Utils.sendProductosActivity(this, receivedProductos, CRUDActivity.class);
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
        if (id == R.id.editFABP) {
            Utils.sendProductosActivity(this, receivedProductos, CRUDActivity.class);
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
        setContentView(R.layout.activity_detail_prod);

        initializeWidgets();
        receiveAndShowData();
    }
}
