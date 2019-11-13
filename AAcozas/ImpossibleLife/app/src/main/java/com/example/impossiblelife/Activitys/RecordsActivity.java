package com.example.impossiblelife.Activitys;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.impossiblelife.Conexion.Conexion;
import com.example.impossiblelife.Modelo.Record;
import com.example.impossiblelife.R;
import com.example.impossiblelife.Utilidades.Utilidades;


import java.util.ArrayList;

public class RecordsActivity extends AppCompatActivity {

    ListView listViewRecords;
    ArrayList<String> listaInfoRec;
    ArrayList<Record>listaRecords;

    Conexion conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        getSupportActionBar().hide();

        setContentView(R.layout.activity_records);

        conn= new Conexion(getApplicationContext(), "bd_records", null,1);

        listViewRecords=(ListView) findViewById(R.id.listViewRecords);

        consultarListaRecords();

        ArrayAdapter adaptador= new ArrayAdapter(this, android.R.layout.simple_list_item_1, listaInfoRec);
        listViewRecords.setAdapter(adaptador);

        listViewRecords.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String info="Puntos: "+listaRecords.get(position).getPuntos()+"\n";
                info+="Nick: "+listaRecords.get(position).getNombre()+"\n";

                Toast.makeText(getApplicationContext(),info, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void consultarListaRecords() {

        SQLiteDatabase db=conn.getReadableDatabase();
        Record record=null;
        listaRecords=new ArrayList<Record>();

        Cursor cursor=db.rawQuery("SELECT * FROM "+ Utilidades.TABLA_RECORDS+" ORDER BY "+Utilidades.CAMPO_TIEMPO+" DESC",null);

        while(cursor.moveToNext()){
            record= new Record();
            record.setPuntos(cursor.getString(1));
            record.setNombre(cursor.getString(0));

            listaRecords.add(record);
        }
        obtenerLista();
    }

    private void obtenerLista() {
        listaInfoRec=new ArrayList<String>();

        for (int x=0;x<listaRecords.size();x++){

            listaInfoRec.add(listaRecords.get(x).getPuntos()+" - "+listaRecords.get(x).getNombre());
        }

    }
}
