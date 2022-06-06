package com.example.tiendita.Views;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

import com.example.tiendita.Helpers.Utils;
import com.example.tiendita.MainActivity;
import com.example.tiendita.R;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;

public class DashboardActivity extends AppCompatActivity {
    LinearLayout viewUsersCard;
    LinearLayout addUsersCard;
    LinearLayout third;
    LinearLayout closeCard;

    private void initializeWidgets(){
        viewUsersCard = findViewById(R.id.viewUsersCard);
        addUsersCard = findViewById(R.id.addUsersCard);
        third = findViewById(R.id.third);
        closeCard = findViewById(R.id.closeCard);

        viewUsersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openActivity(DashboardActivity.this, UsuariosActivity.class);

            }
        });
        addUsersCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openActivity(DashboardActivity.this, CRUDUsersActivity.class);

            }
        });
        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openActivity(DashboardActivity.this, MenuActivity.class);
            }
        });
        closeCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.openActivity(DashboardActivity.this, MainActivity.class);
                finish();
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
        setContentView(R.layout.activity_dashboard);

        this.initializeWidgets();
    }
}
