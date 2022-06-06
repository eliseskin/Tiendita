package com.example.tiendita.Views;

import android.content.Context;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tiendita.MainActivity;
import com.example.tiendita.R;
import com.example.tiendita.Helpers.Utils;

import io.github.inflationx.viewpump.ViewPumpContextWrapper;


public class SplashActivity extends AppCompatActivity {
    private ImageView mLogo;
    private TextView mainTitle, subTitle;

    private void initializeWidgets() {
        mLogo = findViewById(R.id.mLogo);
        mainTitle = findViewById(R.id.mainTitle);
        subTitle = findViewById(R.id.subTitle);
    }

    private void showSplashAnimation() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.top_to_botton);
        mLogo.startAnimation(animation);

        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        mainTitle.startAnimation(fadeIn);
        subTitle.startAnimation(fadeIn);
    }

    private void goToDashboard() {
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(2000);
                    Utils.openActivity(SplashActivity.this, MainActivity.class);
                    finish();
                    super.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t.start();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.initializeWidgets();
        this.showSplashAnimation();
        this.goToDashboard();
    }
}
