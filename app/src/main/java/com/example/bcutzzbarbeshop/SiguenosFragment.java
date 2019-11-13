package com.example.bcutzzbarbeshop;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;


public class SiguenosFragment extends Fragment {

    Button btnFacebook, btnInstagram;
    ProgressBar pb_per;
    WebView mWebView;
    public SiguenosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_siguenos,
                container, false);
        mWebView = (WebView) view.findViewById(R.id.webViewSiguenos);


        // Añadimos una imagen que nos cargue por defecto en el WebView y segun el boton que pulse nos cargara una red social u otra
        mWebView.loadUrl("https://media.istockphoto.com/photos/red-and-blue-striped-barber-shop-pole-picture-id1083830398?k=6&m=1083830398&s=612x612&w=0&h=m0pQTxk0V0u4HvhID05iOW1hDvxXUsUU0FVOedCeJUA=");
        btnFacebook= view.findViewById(R.id.btnFacebook);
        btnInstagram= view.findViewById(R.id.btnInsta);

        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://m.facebook.com/profile.php?id=100002656393407&ref=content_filter";
                WebView v =(WebView) getActivity().findViewById(R.id.webViewSiguenos);
                v.getSettings().setJavaScriptEnabled(true);
                v.loadUrl(url);
            }
        });

        btnInstagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url="https://www.instagram.com/borjabcutzz/?igshid=a8e8lc5q2hlf";
                WebView v =(WebView) getActivity().findViewById(R.id.webViewSiguenos);
                v.getSettings().setJavaScriptEnabled(true);
                v.loadUrl(url);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

}
