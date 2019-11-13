package com.example.impossiblelife.Activitys;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.impossiblelife.R;

public class ConfiguracionActivity extends AppCompatActivity {

    int numNivel, spriteSel;

    //SharedPreferences prefs =
           // getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();
        setContentView(R.layout.activity_configuracion);
        final TextView txtNvl= findViewById(R.id.lblNivel);
        final SeekBar seekBar=findViewById(R.id.seekBar);
        final Button btnGuardarNvl=findViewById(R.id.btnGuardarNvl);
        final Button btnMono=findViewById(R.id.btnMono);
        final Button btnCabra=findViewById(R.id.btnCabra);
        final Button btnPajaro=findViewById(R.id.btnPajaro);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        if(prefs.getInt("nivel", 0)==0){
            txtNvl.setText("Fácil");
            numNivel=0;
        }else if(prefs.getInt("nivel", 0)==1){
            txtNvl.setText("Medio");
            numNivel=1;
        }else if(prefs.getInt("nivel", 0)==2){
            txtNvl.setText("Difícil");
            numNivel=2;
        }

        //txtNvl.setText(String.valueOf(prefs.getInt("nivel", 0 )));
        seekBar.setProgress(prefs.getInt("nivel", 0));

        btnGuardarNvl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefs =
                     getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                SharedPreferences.Editor editor = prefs.edit();
                editor.putInt("nivel", numNivel);
                editor.putInt("spriteSel", spriteSel);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Nivel guardado",Toast.LENGTH_LONG).show();
            }
        });

        btnMono.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spriteSel=1;
                Toast.makeText(getApplicationContext(),"Mono",Toast.LENGTH_LONG).show();
            }
        });

        btnCabra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spriteSel=2;
                Toast.makeText(getApplicationContext(),"Cabra",Toast.LENGTH_LONG).show();
            }
        });

        btnPajaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spriteSel=3;
                Toast.makeText(getApplicationContext(),"Mapache",Toast.LENGTH_LONG).show();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(progress==0){
                    txtNvl.setText("Fácil");
                    numNivel=0;
                }else if(progress==1){
                    txtNvl.setText("Medio");
                    numNivel=1;
                }else if(progress==2){
                    txtNvl.setText("Difícil");
                    numNivel=2;
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}
