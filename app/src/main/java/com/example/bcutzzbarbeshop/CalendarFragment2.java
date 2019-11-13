package com.example.bcutzzbarbeshop;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import static android.provider.ContactsContract.CommonDataKinds.Website.URL;


public class CalendarFragment2 extends Fragment implements DatePickerDialog.OnDateSetListener {


    TextView dateTextView;
    DatePickerDialog datePickerDialog;
    int Year, Month, Day, Hour, Minute;
    Calendar calendar;
    String diaRec, horaRec, servicioRecibido, numSerRecibido, horaSelec, horaElegida, nombreServicio;

    RequestQueue rq, rq2;
    Button dateButton, btnSiguiente;
    ListView listViewHoras;

    ArrayList<String> listaInfoServ = new ArrayList<>();
    ArrayList<String> listaDiasLibres = new ArrayList<>();
    ArrayAdapter adaptador;


    public CalendarFragment2() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendar_fragment2, container, false);
        listViewHoras = (ListView) view.findViewById(R.id.listViewHoras);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);
        dateTextView = view.findViewById(R.id.txbAc2);
        dateButton = view.findViewById(R.id.button3);
        btnSiguiente = view.findViewById(R.id.btnSiguiente);
        btnSiguiente.setEnabled(false);
        calendar = Calendar.getInstance();

        Year = calendar.get(Calendar.YEAR);
        Month = calendar.get(Calendar.MONTH);
        Day = calendar.get(Calendar.DAY_OF_MONTH);
        Hour = calendar.get(Calendar.HOUR_OF_DAY);
        Minute = calendar.get(Calendar.MINUTE);

        Bundle bundle = this.getArguments();

        SharedPreferences prefs =
                getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        //Recibimos el servicio seleccionado en el anterior fragment

        if (bundle != null) {
            servicioRecibido = bundle.getString("servicioRecibido");
        }


        dateButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datePickerDialog = DatePickerDialog.newInstance(CalendarFragment2.this, Year, Month, Day);
                datePickerDialog.setThemeDark(false);
                datePickerDialog.showYearPickerFirst(false);
                datePickerDialog.setTitle("Date Picker");

                configurarDias(datePickerDialog);
            }
        });

        btnSiguiente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String nombreUsuario, fecha, hora, servicio;

                nombreUsuario = prefs.getString("usuario", "");
                fecha = dateTextView.getText().toString();
                hora = horaElegida;
                servicio = servicioRecibido;

                registrarCita("https://probanditoito.000webhostapp.com/insertarCita.php");

                modificarHoras("https://probanditoito.000webhostapp.com/asignarHoras.php");
                getActivity().onBackPressed();
                Intent intent = new Intent(getActivity(), CitaSolicitada.class);
                getActivity().startActivity(intent);

            }
        });

        //Activamos el boton de siguiente cuando el usuario selecciona algun elemento en el ListView
        listViewHoras.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adpterView, View view, int position,
                                    long id) {
                horaElegida = listaInfoServ.get(position);
                btnSiguiente.setEnabled(true);
                btnSiguiente.setBackground(getResources().getDrawable(R.drawable.buttonapp));
            }
        });

        cargarDiasLibres("https://probanditoito.000webhostapp.com/cargarDiasLibres.php");

        // Inflate the layout for this fragment
        return view;

    }
    /*
     *   Modificamos las horas que ha seleccionado el usuario y las indicamos como no disponibles en la bbdd
     **/
    private void modificarHoras(String URL) {

        SharedPreferences prefs =
                getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date2 = null;
                try {
                    date2 = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha2 = formateador.format(date2);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al insertar el nuevo dia " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date = null;
                try {
                    date = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha = formateador.format(date);
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("hora", horaElegida);
                parametros.put("dia", fecha);
                parametros.put("servicio", servicioRecibido);

                return parametros;


            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);

        SharedPreferences.Editor editor = prefs.edit();

        if (servicioRecibido.equals("1")) {
            nombreServicio = "barba (15 Min)";
        } else if (servicioRecibido.equals("2")) {
            nombreServicio = "pelado (30 Min)";
        } else if (servicioRecibido.equals("3")) {
            nombreServicio = "pelado + barba (45 Min)";
        }

        editor.putString("citaCliente", prefs.getString("usuario", "") + ", tienes cita el día " + dateTextView.getText().toString() + " a las " + horaElegida + " para " + nombreServicio);
        editor.commit();

    }


    /*
     *   Registramos la nueva cita solicitada por el cliente en la bbdd
     **/
    private void registrarCita(String URL) {

        SharedPreferences prefs =
                getContext().getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("response", response);

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date2 = null;
                try {
                    date2 = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha2 = formateador.format(date2);


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al insertar el nuevo dia " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date = null;
                try {
                    date = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha = formateador.format(date);

                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("nomCliente", prefs.getString("usuario", ""));
                parametros.put("hora", horaElegida);
                parametros.put("dia", fecha);
                parametros.put("servicio", servicioRecibido);


                Log.i("viraa", prefs.getString("usuario", "") + " " + fecha + " " + horaElegida + " " + servicioRecibido);

                return parametros;


            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        Log.i("string4", stringRequest.toString());

    }

    /*
     *   Comprobamos que el dia está en la bbdd
     **/
    private void buscarDia(String URL) {

        diaRec = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject = null;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        diaRec = jsonObject.getString("dni");

                    } catch (JSONException e) {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                RegistrarDia("https://probanditoito.000webhostapp.com/crearDia.php");

            }
        }
        );
        rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);
    }


    /*
     *   Si el dia no existe en la bbdd se registra y se añaden todas las horas de este dia como disponibles
     **/
    private void RegistrarDia(String URL) {

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date2 = null;
                try {
                    date2 = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha2 = formateador.format(date2);


                cargarHoras("https://probanditoito.000webhostapp.com/cargarHoras.php?diaSelec=" + fecha2 + "&tiempoServicio=" + servicioRecibido);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Error al insertar el nuevo dia " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
                // el que formatea
                SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

                Date date = null;
                try {
                    date = parseador.parse(dateTextView.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                String fecha = formateador.format(date);
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("Dia", fecha);
                return parametros;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);


        Log.i("string", stringRequest.toString());

    }


    /*
     *   Configuramos los dias disponibles en el DatePicker, desactivando los fines de semana y los
     *   dias que el administrador ha indicado
     **/
    private void configurarDias(DatePickerDialog datePickerDialog) {

        // Setting Min Date to today date
        Calendar min_date_c = Calendar.getInstance();
        datePickerDialog.setMinDate(min_date_c);


        // Setting Max Date to next 2 years
        Calendar max_date_c = Calendar.getInstance();
        max_date_c.set(Calendar.MONTH, Month + 3);
        datePickerDialog.setMaxDate(max_date_c);
        Log.d("NOSE", String.valueOf(max_date_c));


        //Disable all SUNDAYS and SATURDAYS between Min and Max Dates
        for (Calendar loopdate = min_date_c; min_date_c.before(max_date_c); min_date_c.add(Calendar.DATE, 1), loopdate = min_date_c) {
            int dayOfWeek = loopdate.get(Calendar.DAY_OF_WEEK);
            String dia = String.valueOf(loopdate.get(Calendar.DAY_OF_MONTH));
            String mes = String.valueOf(loopdate.get(Calendar.MONTH) + 1);
            String año = String.valueOf(loopdate.get(Calendar.YEAR));

            String diaBuscar = año + "-" + mes + "-" + dia;
            Log.d("DIA", diaBuscar);
            if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY) {
                Calendar[] disabledDays = new Calendar[1];
                disabledDays[0] = loopdate;
                datePickerDialog.setDisabledDays(disabledDays);
            } else {
                //Conectar con la BBDD y comprobar cada dia
            }
        }

        Calendar[] disabledDays2 = new Calendar[1];
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        for (int i = 0; i < listaDiasLibres.size(); i++) {
            try {
                date = (formatter.parse(listaDiasLibres.get(i)));
                Calendar myCal = new GregorianCalendar();
                myCal.setTime(date);

                disabledDays2[0] = myCal;
                datePickerDialog.setDisabledDays(disabledDays2);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        datePickerDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialogInterface) {

                Toast.makeText(getContext(), "Datepicker cancelado", Toast.LENGTH_SHORT).show();
            }
        });

        datePickerDialog.show(requireFragmentManager(), "DatePickerDialog");
    }


    private void cargarDiasLibres(String URL) {

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject;
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        listaDiasLibres.add(jsonObject.getString("diaLibre"));
                    } catch (JSONException e) {

                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }
        );
        rq2 = Volley.newRequestQueue(getContext());
        rq2.add(jsonArrayRequest);
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;

        dateTextView.setText(date);

        buscarDia("https://probanditoito.000webhostapp.com/buscarDia.php?dia=" + date);

        SimpleDateFormat parseador = new SimpleDateFormat("dd/MM/yyyy");
        // el que formatea
        SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");

        Date date2 = null;
        try {
            date2 = parseador.parse(dateTextView.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String fecha2 = formateador.format(date2);
        cargarHoras("https://probanditoito.000webhostapp.com/cargarHoras.php?diaSelec=" + fecha2 + "&tiempoServicio=" + servicioRecibido);
    }

    /*
     *   Una vez seleccionado el día en el DatePicker cargamos solamente las horas disponibles de ese dia segun el servicio
     **/
    private void cargarHoras(String URL) {
        horaRec = "";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONObject jsonObject;
                listaInfoServ.clear();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        jsonObject = response.getJSONObject(i);
                        horaRec = jsonObject.getString("horaEntrada");
                        Log.d("horita", horaRec);
                        listaInfoServ.add(horaRec);
                    } catch (JSONException e) {

                    }

                    adaptador = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, listaInfoServ);
                    listViewHoras.setAdapter(adaptador);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }
        );
        rq = Volley.newRequestQueue(getContext());
        rq.add(jsonArrayRequest);

    }
}
