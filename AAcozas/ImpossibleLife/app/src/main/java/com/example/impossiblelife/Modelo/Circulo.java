package com.example.impossiblelife.Modelo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Circulo extends Figura{


    float varX, varY, radio;
    String nombre;
    int color;


    public Circulo(float varX, float varY, float radio, String nombre, int color) {
        this.varX = varX;
        this.varY = varY;
        this.radio = radio;
        this.nombre = nombre;
        this.color = color;
    }

    public float getVarX() {
        return varX;
    }

    public void setVarX(float varX) {
        this.varX = varX;
    }

    public float getVarY() {
        return varY;
    }

    public void setVarY(float varY) {
        this.varY = varY;
    }

    public float getRadio() {
        return radio;
    }

    public void setRadio(float radio) {
        this.radio = radio;
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getColor() {return color;}

    public void setColor(int color) { this.color = color; }

    @Override
    public void dibujar(Canvas canvas) {
            Paint p = new Paint();
            p.setColor(color);
            p.setStyle(Paint.Style.FILL);
            canvas.drawCircle(varX,varY,radio,p);
        }


    @Override
    public boolean comprobarSiTocoDentro(Float x, Float y) {
        boolean devolver= false;

        if(Math.pow(getRadio(),2)>(Math.pow(x-getVarX(),2)+(Math.pow(y-getVarY(),2)))){

            devolver= true;

        }

        return devolver;
    }

    @Override
    public void actualizar(Float x, Float y) {
        setVarX(getVarX()+x);
        setVarY(getVarY()+y);
    }
}
