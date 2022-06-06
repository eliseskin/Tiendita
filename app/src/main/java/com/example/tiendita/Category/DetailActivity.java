package com.example.tiendita.Category;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import com.example.tiendita.HelpersCategory.Utils;
import com.example.tiendita.R;
import com.example.tiendita.RetrofitCategory.Categorias;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

@SuppressWarnings("deprecation")
public class DetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView categoria;
    private FloatingActionButton editFAB;
    private Categorias receivedCategorias;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    private void initializeWidgets() {
        categoria = findViewById(R.id.categoria);
        editFAB = findViewById(R.id.editFABC);
        editFAB.setOnClickListener(this);
        mCollapsingToolbarLayout = findViewById(R.id.mCollapsingToolbarLayoutC);
    }

    private void receiveAndShowData() {
        receivedCategorias = Utils.receiveCategorias(getIntent(), DetailActivity.this);

        if (receivedCategorias != null) {
            categoria.setText(receivedCategorias.getCategoria());

            mCollapsingToolbarLayout.setTitle(receivedCategorias.getCategoria());
            mCollapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(R.color.white));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_page_cat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_editC:
                Utils.sendCategoriasActivity(this, receivedCategorias, CRUDActivity.class);
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
        if (id == R.id.editFABC) {
            Utils.sendCategoriasActivity(this, receivedCategorias, CRUDActivity.class);
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
        setContentView(R.layout.activity_detail_cat);

        initializeWidgets();
        receiveAndShowData();
    }
}
