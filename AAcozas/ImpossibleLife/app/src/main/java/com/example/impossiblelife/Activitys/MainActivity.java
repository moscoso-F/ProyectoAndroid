package com.example.impossiblelife.Activitys;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.impossiblelife.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);


        final Button botonPlay= findViewById(R.id.btnPlay);
        final Button botonFinish= findViewById(R.id.btnSalir);
        final Button botonRecords = findViewById(R.id.btnRecords);
        final Button botonConfiguracion = findViewById(R.id.btnConfiguracion);
        final TextView txtNick=findViewById(R.id.txtNick);

        botonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNick.getText().toString().equals("")){
                    Toast.makeText(getApplicationContext(),"Debes insertar un nick",Toast.LENGTH_LONG).show();
                }else {
                    SharedPreferences prefs =
                            getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("nick", txtNick.getText().toString());
                    editor.commit();
                    Intent i = new Intent(getApplicationContext(), JuegoActivity.class);
                    startActivity(i);
                }
            }
        });


        botonRecords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), RecordsActivity.class);
                startActivity(i);
            }
        });


        botonConfiguracion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ConfiguracionActivity.class);
                startActivity(i);
            }
        });



        botonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
