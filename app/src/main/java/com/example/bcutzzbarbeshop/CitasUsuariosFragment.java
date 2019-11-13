package com.example.bcutzzbarbeshop;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class CitasUsuariosFragment extends Fragment {

    ArrayList<String> listaInfoCitas = new ArrayList<>();
    ArrayAdapter adaptador;
    ListView listViewCitasCliente;
    String hora, dia, servicio, nomServicio, nomCliente;
    RequestQueue rq;

    public CitasUsuariosFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_citas_usuarios,
                container, false);
        listViewCitasCliente = (ListView) view.findViewById(R.id.lvCitasCliente);
        SharedPreferences prefs =
                getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        String nombreUsuario = prefs.getString("usuario", "");
        cargarCitasUsuario("https://probanditoito.000webhostapp.com/cargarCitasUsuarios.php?nomUsuario=" + nombreUsuario);

        return view;
    }

    private void cargarCitasUsuario(String URL) {

        hora = "";
        dia = "";
        nomServicio = "";
        nomCliente = "";
        servicio = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject;
                listaInfoCitas.clear();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        hora = jsonObject.getString("hora");
                        dia = jsonObject.getString("dia");
                        nomServicio = jsonObject.getString("nomServicio");
                        servicio = jsonObject.getString("servicio");

                        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");
                        // el que formatea
                        SimpleDateFormat parseador = new SimpleDateFormat("yyyy-MM-dd");

                        Date date2 = null;
                        try {
                            date2 = parseador.parse(dia);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        String fecha2 = formateador.format(date2);

                        String duracionServ = "";
                        if (servicio.equals("1")) {
                            duracionServ = "15 min";
                        } else if (servicio.equals("2")) {
                            duracionServ = "30 min";
                        } else if (servicio.equals("3")) {
                            duracionServ = "45 min";
                        }

                        listaInfoCitas.add("Tiene cita el día " + fecha2 + " a las hora " + hora + " para " + nomServicio + " (Duración: " + duracionServ + ")");
                    } catch (JSONException e) {
                    }
                    adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaInfoCitas);
                    listViewCitasCliente.setAdapter(adaptador);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (listaInfoCitas.size() == 0) {
                    listaInfoCitas.add("No tienes ninguna cita reservada");
                }
            }
        }
        );
        rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);

    }
}
