package com.example.bcutzzbarbeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InicioActivity extends AppCompatActivity implements Response.ErrorListener, Response.Listener<JSONObject> {


    RequestQueue rq;
    JsonRequest jrq;

    EditText cajaUser, cajaPwd;
    Button btnConsultar, btnRegistrarme;
    CheckBox cbxRecordar;
    boolean cbxMarcado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        cajaUser = (EditText) findViewById(R.id.txbUsuario);
        cajaPwd = (EditText) findViewById(R.id.txbContrasena);
        cbxRecordar = (CheckBox) findViewById(R.id.cbxRecordar);

        btnConsultar = (Button) findViewById(R.id.btnAcceder);
        btnRegistrarme = (Button) findViewById(R.id.btnRegistrarme);
        rq = Volley.newRequestQueue(getApplicationContext());


        btnConsultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iniciarSesion();
            }
        });

        btnRegistrarme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent i = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(i);
            }
        });

        //Guardo el usuario y contraseña si el usuario marca el checkbox

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        cbxMarcado = prefs.getBoolean("cbxMarcado", false);

        if (cbxMarcado) {
            cbxRecordar.setChecked(true);
            cajaPwd.setText(prefs.getString("contrasena", ""));
            cajaUser.setText(prefs.getString("usuario", ""));
        } else {
            cbxRecordar.setChecked(false);
        }
    }

    /*
     *   Comprobamos que existe el usuario y la contraseña en la BBDD y si es asi lo redirigimos al menú
     **/
    private void iniciarSesion() {

        String url = "https://probanditoito.000webhostapp.com/sesion2.php?usuario=" + cajaUser.getText().toString() + "&contrasena=" + cajaPwd.getText().toString();
        jrq = new JsonObjectRequest(Request.Method.GET, url, null, this, this);
        rq.add(jrq);

    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(getApplicationContext(), "Error en la autenticación " + error.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        Usuario usuario = new Usuario();
        JSONArray jsonArray = response.optJSONArray("datos");
        JSONObject jsonObject = null;

        try {
            jsonObject = jsonArray.getJSONObject(0);
            usuario.setUsuario(jsonObject.optString("usuario"));
            usuario.setContrasena(jsonObject.optString("contrasena"));
            usuario.setCodCliente(jsonObject.optString("codCliente"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (cbxRecordar.isChecked()) {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", cajaUser.getText().toString());
            editor.putString("contrasena", cajaPwd.getText().toString());
            editor.putBoolean("cbxMarcado", true);
            editor.commit();

            finish();
            Intent i = new Intent(getApplicationContext(), MenuuActivity.class);
            startActivity(i);
        } else {

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("usuario", cajaUser.getText().toString());
            editor.putBoolean("cbxMarcado", false);
            editor.commit();

            finish();
            Intent i = new Intent(getApplicationContext(), MenuuActivity.class);
            startActivity(i);
        }
    }
}
