package com.example.bcutzzbarbeshop;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CitaSolicitada extends AppCompatActivity {

    TextView cita;
    Button btnCitaSol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cita_solicitada);


        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        String citaSol = prefs.getString("citaCliente", "ERROR");

        //Mostramos la cita completa para informar al usuario

        cita = findViewById(R.id.txbCitaTotal);
        btnCitaSol = findViewById(R.id.btnAceptarCitSol);
        cita.setText(citaSol);

        btnCitaSol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), MenuuActivity.class);
                startActivity(i);
            }
        });
    }
}
