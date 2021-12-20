package com.lexandroid.movieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.L;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.lexandroid.movieapp.login.LoginAdapter;

public class LoginActivity extends AppCompatActivity {

    //Animation
    ImageView logo, splashBg, tmdb;
    TextView pwrdBy;
    LottieAnimationView lottieAnimationView;

    //Login & Signup
    TabLayout tabLayout;
    ViewPager viewPager;
    FloatingActionButton google, google2, google3;
    float v = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introductory);

        //logo = findViewById(R.id.logo_splash_screen);
        splashBg = findViewById(R.id.imgSplash);
        tmdb = findViewById(R.id.logo_tmdb);
        pwrdBy = findViewById(R.id.pwrdBy);


        Animation aniFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out_animation);


        //Slide away animation

        splashBg.animate().translationY(-3600).setDuration(500).setStartDelay(3000);
        pwrdBy.animate().translationY(2400).setDuration(500).setStartDelay(3000);
        tmdb.animate().translationY(2400).setDuration(500).setStartDelay(3000);

//        //Fade out animation
//        splashBg.startAnimation(aniFadeOut);
//        pwrdBy.startAnimation(aniFadeOut);
//        tmdb.startAnimation(aniFadeOut);



        //Login & Signup
        tabLayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        google = findViewById(R.id.fab_google);
        google2 = findViewById(R.id.fab_google2);
        google3 = findViewById(R.id.fab_google3);

        tabLayout.addTab(tabLayout.newTab().setText("Login"));
        tabLayout.addTab(tabLayout.newTab().setText("Signup"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final LoginAdapter adapter = new LoginAdapter(getSupportFragmentManager(), this, tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));


        google.setTranslationY(300);
        google2.setTranslationY(300);
        google3.setTranslationY(300);
        tabLayout.setTranslationY(300);

        google.setAlpha(v);
        google2.setAlpha(v);
        google3.setAlpha(v);
        tabLayout.setAlpha(v);

        google.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3400).start();
        google2.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3600).start();
        google3.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3800).start();

        tabLayout.animate().translationY(0).alpha(1).setDuration(1000).setStartDelay(3100).start();




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(LoginActivity.this, "Please Continue as Guest", Toast.LENGTH_LONG).show();
                Snackbar snackbar = Snackbar.make(tabLayout, "Please Continue As Guest", Snackbar.LENGTH_INDEFINITE).setAction("OKAY", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
                snackbar.show();
            }
        }, 5000);

    }

}