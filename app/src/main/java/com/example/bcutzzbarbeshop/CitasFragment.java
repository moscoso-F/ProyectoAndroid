package com.example.bcutzzbarbeshop;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;


public class CitasFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_citas,
                container, false);
        Button button = (Button) view.findViewById(R.id.btnAceptarDia);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Recibido", Toast.LENGTH_LONG).show();
                //cambiarFragmento();
            }
        });

        CalendarView calendario = (CalendarView) view.findViewById(R.id.calendarCita);

        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today);
        c.add(Calendar.WEEK_OF_MONTH, -0); // Subtract 6 weeks
        long minDate = c.getTime().getTime(); // Twice!
        calendario.setMinDate(minDate);


        c.setTime(today);
        c.add(Calendar.WEEK_OF_MONTH, +12); // Subtract 6 weeks
        long maxDate = c.getTime().getTime(); // Twice!
        calendario.setMaxDate(maxDate);

        return view;
    }

    private void cambiarFragmento() { //Con este metodo cambiamos de fragmento

        SelectServFragment newFragmento = new SelectServFragment();
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedor, newFragmento);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

}
