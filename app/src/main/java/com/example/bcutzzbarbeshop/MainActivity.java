package com.example.bcutzzbarbeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Oculto la barra de tareas
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        //La muestro 3 segundos y desaparece
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent menuInicio = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(menuInicio);
                finish();
            }
        }, 2200);

    }
}
