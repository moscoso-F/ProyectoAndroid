package com.example.bcutzzbarbeshop;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;


public class SelectServFragment extends Fragment {

    //private Spinner spinner;
    ListView listServicios;
    ArrayList<String> listaInfoServ;
    ArrayAdapter<String> listaServicios;
    String seleccionado;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_select_serv,
                container, false);

        listServicios = (ListView) view.findViewById(R.id.listViewServicio);
        rellenarList();
        listaServicios = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_activated_1, listaInfoServ);
        listServicios.setAdapter(listaServicios);

        Button button = (Button) view.findViewById(R.id.btnAceptarServicio);
        button.setEnabled(false);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFragmento();
            }
        });


        //Evento que detecta el servicio que ha seleccionado el cliente y activa el boton cuando selecciona alguno

        listServicios.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adpterView, View view, int position,
                                    long id) {
                for (int i = 0; i < listServicios.getChildCount(); i++) {
                    if (position == i) {
                        listServicios.getChildAt(i).setBackgroundColor(Color.parseColor("#95353535"));
                        seleccionado = String.valueOf(i + 1);
                        button.setEnabled(true);
                        button.setBackground(getResources().getDrawable(R.drawable.buttonapp));
                    } else {
                        listServicios.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                    }
                }
            }
        });
        return view;
    }

    private void cambiarFragmento() {

        Bundle bundle = new Bundle();
        bundle.putString("servicioRecibido", seleccionado); // Put anything what you want

        CalendarFragment2 fragment2 = new CalendarFragment2();
        fragment2.setArguments(bundle);

        getFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedor, fragment2)
                .commit();
    }

    private void rellenarList() {
        listaInfoServ = new ArrayList<String>();

        listaInfoServ.add("Afeitado");
        listaInfoServ.add("Pelado");
        listaInfoServ.add("Pelado + afeitado");

    }
}
