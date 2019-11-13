package com.example.bcutzzbarbeshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RegistroActivity extends AppCompatActivity {

    RequestQueue rq;
    JsonRequest jrq2;
    String dniRec;
    EditText txbNombre, txbApellidos, txbDni, txbTfno, txbCorreo, txbFecha, txbNomUsu, txbCont, txbContRep;
    Button btnRegistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        txbNombre = (EditText) findViewById(R.id.txbNombre);
        txbApellidos = (EditText) findViewById(R.id.txbApellidos);
        txbDni = (EditText) findViewById(R.id.txbDNI);
        txbTfno = (EditText) findViewById(R.id.txbTfno);
        txbCorreo = (EditText) findViewById(R.id.txbCorreo);
        txbFecha = (EditText) findViewById(R.id.txbFecha);
        txbNomUsu = (EditText) findViewById(R.id.txbNomUsu);
        txbCont = (EditText) findViewById(R.id.txbCont);
        txbContRep = (EditText) findViewById(R.id.txbContRep);


        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);


        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (comprobarCampos()) {
                    buscarDni("https://probanditoito.000webhostapp.com/buscarDni.php?dni=" + txbDni.getText().toString());
                }
            }
        });

    }
    /*
    *   Comprobamos que el DNI introducido no está ya registrado
    **/
    private void buscarDni(String URL) {
        dniRec = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        dniRec = jsonObject.getString("dni");
                        Toast.makeText(getApplicationContext(), "ERROR-EL DNI YA ESTÁ REGISTRADO", Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        //Toast.makeText(getApplicationContext(), "DNI no encontrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                RegistrarUsuario("https://probanditoito.000webhostapp.com/insertarRegistro.php");
            }
        }
        );
        rq = Volley.newRequestQueue(this);
        rq.add(jsonArrayRequest);
    }




    /*
     *   Añadimos el usuario si previamente no existia el dni introducido
     **/


    private void RegistrarUsuario(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                Toast.makeText(getApplicationContext(), "Registro Insertado", Toast.LENGTH_SHORT).show();

                finish();
                Intent in = new Intent(getApplicationContext(), InicioActivity.class);
                startActivity(in);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error al insertar registro " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date = null;
                try {
                    date = parseador.parse(txbFecha.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha = formateador.format(date);
                Log.i("fecha", txbNombre.getText().toString() + "/" + txbApellidos.getText().toString() + "/" + txbDni.getText().toString() + "/" +
                        txbTfno.getText().toString() + "/" + txbCorreo.getText().toString() + "/" + fecha + "/" + txbNomUsu.getText().toString() + "/" + txbCont.getText().toString());
                //Toast.makeText(getApplicationContext(), fecha, Toast.LENGTH_SHORT).show();
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Nombre", txbNombre.getText().toString());
                parametros.put("Apellidos", txbApellidos.getText().toString());
                parametros.put("Dni", txbDni.getText().toString());
                parametros.put("Tfno", txbTfno.getText().toString());
                parametros.put("Correo", txbCorreo.getText().toString());
                parametros.put("FechaNac", fecha.toString());
                parametros.put("NomUsu", txbNomUsu.getText().toString());
                parametros.put("ContrUsu", txbCont.getText().toString());
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


        Log.i("string", stringRequest.toString());
    }


    /*
     *   Comprobamos que todos los campos introducidos son válidos
     **/


    private boolean comprobarCampos() {

        boolean comprobacion = false;

        if (txbNombre.getText().toString().equals("")) {
            txbNombre.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (esLetra(txbNombre)) {
                txbNombre.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            } else {
                txbNombre.setBackgroundResource(R.drawable.fondoroj);
                Toast.makeText(getApplicationContext(), "El nombre no debe contener números", Toast.LENGTH_SHORT).show();
                comprobacion = false;
            }
        }

        if (txbApellidos.getText().toString().equals("")) {
            txbApellidos.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (esLetra(txbApellidos)) {
                txbApellidos.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            } else {
                txbApellidos.setBackgroundResource(R.drawable.fondoroj);
                Toast.makeText(getApplicationContext(), "Los apellidos no deben contener números.", Toast.LENGTH_SHORT).show();
                comprobacion = false;
            }
        }

        if (txbDni.getText().toString().equals("")) {
            txbDni.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (txbDni.getText().toString().length() == 9) {
                if (comprobarDNI(txbDni.getText().toString())) {
                    txbDni.setBackgroundResource(R.drawable.button);
                    comprobacion = true;
                } else {
                    Toast.makeText(getApplicationContext(), "Debes introducir un DNI válido.", Toast.LENGTH_SHORT).show();
                    txbDni.setBackgroundResource(R.drawable.fondoroj);
                    comprobacion = false;
                }
            } else {
                Toast.makeText(getApplicationContext(), "Tamaño incorrecto. Debes introducir un DNI válido.", Toast.LENGTH_SHORT).show();
                txbDni.setBackgroundResource(R.drawable.fondoroj);
                comprobacion = false;
            }


        }

        if (txbTfno.getText().toString().equals("")) {
            txbTfno.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (txbTfno.getText().toString().length() != 9) {
                Toast.makeText(getApplicationContext(), "El télefono no tiene una longitud válida.", Toast.LENGTH_SHORT).show();
                comprobacion = false;
            } else {
                if (esNumero(txbTfno)) {
                    txbTfno.setBackgroundResource(R.drawable.button);
                    comprobacion = true;
                } else {
                    txbTfno.setBackgroundResource(R.drawable.fondoroj);
                    Toast.makeText(getApplicationContext(), "El teléfono no debe contener letras.", Toast.LENGTH_SHORT).show();
                    comprobacion = false;
                }
            }
        }


        if (txbCorreo.getText().toString().equals("")) {
            txbCorreo.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (emailValido(txbCorreo.getText().toString())) {
                txbCorreo.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            } else {
                txbCorreo.setBackgroundResource(R.drawable.fondoroj);
                Toast.makeText(getApplicationContext(), "Debes introducir un email válido.", Toast.LENGTH_SHORT).show();
                comprobacion = false;
            }

        }

        if (txbFecha.getText().toString().equals("")) {
            txbFecha.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            if (comprobarFecha(txbFecha.getText().toString())) {
                txbFecha.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            } else {
                Toast.makeText(getApplicationContext(), "Debes introducir una fecha válida.(dd/MM/aaaa).", Toast.LENGTH_SHORT).show();
                txbFecha.setBackgroundResource(R.drawable.fondoroj);
                comprobacion = false;
            }

        }

        if (txbNomUsu.getText().toString().equals("")) {
            txbNomUsu.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
        } else {
            txbNomUsu.setBackgroundResource(R.drawable.button);
            comprobacion = true;
        }

        if (txbCont.getText().toString().equals("")) {
            txbCont.setBackgroundResource(R.drawable.fondoroj);
            comprobacion = false;
            if (txbContRep.getText().toString().equals("")) {
                txbContRep.setBackgroundResource(R.drawable.fondoroj);
                comprobacion = false;
            } else {
                txbContRep.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            }
        } else {
            if (txbContRep.getText().toString().equals("")) {
                txbContRep.setBackgroundResource(R.drawable.fondoroj);
                comprobacion = false;
            } else {
                txbContRep.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            }
            if (!txbCont.getText().toString().equals(txbContRep.getText().toString())) {

                Toast.makeText(getApplicationContext(), "Las contraseñas deben ser iguales. Por favor, revise las contraseñas", Toast.LENGTH_SHORT).show();

                txbCont.setBackgroundResource(R.drawable.fondoroj);
                txbContRep.setBackgroundResource(R.drawable.fondoroj);
                comprobacion = false;
            } else {
                txbCont.setBackgroundResource(R.drawable.button);
                txbContRep.setBackgroundResource(R.drawable.button);
                comprobacion = true;
            }
        }
        return comprobacion;
    }

    public Boolean comprobarFecha(String date) {
        if (date == null || !date.matches("^(1[0-9]|0[1-9]|3[0-1]|2[1-9])/(0[1-9]|1[0-2])/[0-9]{4}$"))
            return false;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            format.parse(date);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }

    private boolean emailValido(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email)
                .matches();
    }

    public static boolean esNumero(EditText input) {

        String data = input.getText().toString().trim();
        for (int i = 0; i < data.length(); i++) {
            if (!Character.isDigit(data.charAt(i)))
                return false;

        }
        return true;
    }

    public static boolean esLetra(EditText input) {

        String data = input.getText().toString().trim();
        for (int i = 0; i < data.length(); i++) {
            if (!Character.isDigit(data.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean comprobarDNI(String dni) {
        boolean dniCorrecto = false;

        String num = dni.substring(0, dni.length() - 1);
        String let = dni.substring(dni.length() - 1).toUpperCase();

        int numero = Integer.parseInt(num) % 23;
        Log.d("num2", String.valueOf(numero));
        String cadLet = "TRWAGMYFPDXBNJZSQVHLCKE";


        String letra = cadLet.substring(numero, numero + 1);

        if (letra.equals(let)) {
            dniCorrecto = true;
        }

        return dniCorrecto;
    }
}
