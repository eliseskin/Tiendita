package com.example.tiendita.Views;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tiendita.Category.CategoriaActivity;
import com.example.tiendita.Clients.ClientesActivity;
import com.example.tiendita.HelpersCategory.Utils;
import com.example.tiendita.Products.ProductosActivity;
import com.example.tiendita.R;
import com.example.tiendita.Sales.VentasActivity;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class MenuActivity  extends AppCompatActivity {
    LinearLayout viewCatCard, viewProdCard, viewClientCard, viewSalesCard;

    private void initializeWidgets() {
        viewCatCard = findViewById(R.id.viewCatCard);
        viewProdCard = findViewById(R.id.viewProdCard);
        viewClientCard = findViewById(R.id.viewClientCard);
        viewSalesCard = findViewById(R.id.viewSalesCard);

        viewCatCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(MenuActivity.this, CategoriaActivity.class);
            }
        });

        viewProdCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(MenuActivity.this, ProductosActivity.class);
            }
        });

        viewClientCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(MenuActivity.this, ClientesActivity.class);
            }
        });

        viewSalesCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.openActivity(MenuActivity.this, VentasActivity.class);
            }
        });
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
        setContentView(R.layout.activity_tablesmenu);

        this.initializeWidgets();
    }
}
