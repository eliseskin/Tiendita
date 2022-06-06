package com.example.tiendita.Sales;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.HelpersSales.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitSales.Ventas;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

@SuppressWarnings("deprecation")
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView folio, cantidad, total;
    private FloatingActionButton editFAB;
    private Ventas receivedVentas;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets() {
        folio = findViewById(R.id.folioventa);
        cantidad = findViewById(R.id.cantidad);
        total = findViewById(R.id.total);
        editFAB = findViewById(R.id.editFABV);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout = findViewById(R.id.mCollapsingToolbarLayoutV);
    }

    private void receiveAndShowData() {
        receivedVentas = Utils.receiveVentas(getIntent(), DetailActivity.this);

        if (receivedVentas != null) {
            folio.setText(receivedVentas.getFolioventa());
            cantidad.setText(receivedVentas.getCatidad_productos());
            total.setText(receivedVentas.getTotal());

            mCollapsingToolbarLayout.setTitle(receivedVentas.getFolioventa());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_ventas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editV:
                Utils.sendVentasActivity(this, receivedVentas, CRUDActivity.class);
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
        if (id == R.id.editFABV) {
            Utils.sendVentasActivity(this, receivedVentas, CRUDActivity.class);
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
        setContentView(R.layout.activity_detail_venta);

        initializeWidgets();
        receiveAndShowData();
    }
}
