package com.example.bcutzzbarbeshop;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;


public class DondeEstamosFragment extends Fragment {

    TextView direccion;

    public DondeEstamosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_donde_estamos,
                container, false);


        //Cargo en el Mapa la dirección del local
        String url = "https://www.google.com/maps/place/Bcutzz,+Plaza+Kiyosu,+11405+Jerez+de+la+Frontera,+Cádiz/@36.6863483,-6.1201658,17z/data=!4m2!3m1!1s0xd0dc6e631cb8e13:0xca9111fae5a75f1";
        WebView v = (WebView) view.findViewById(R.id.webViewMapa);
        v.getSettings().setJavaScriptEnabled(true);
        v.loadUrl(url);

        return view;
    }


}
