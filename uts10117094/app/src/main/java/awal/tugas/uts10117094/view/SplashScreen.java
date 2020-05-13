package awal.tugas.uts10117094.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import awal.tugas.uts10117094.R;

//tanggal pengerjaan: 5/7/2020
//nim: 10117094
//nama: Muhammad Walidin
//kelas: IF-3
public class SplashScreen extends AppCompatActivity {
    private ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT < 16) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }

        iv =findViewById(R.id.logo);

        Animation myanim = AnimationUtils.loadAnimation(this, R.anim.trans);
        iv.startAnimation(myanim);
        final Intent i = new Intent(this, awal.tugas.uts10117094.view.IntroActivity.class);
        Thread timer = new Thread() {
            public void run(){
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();
    }
}
