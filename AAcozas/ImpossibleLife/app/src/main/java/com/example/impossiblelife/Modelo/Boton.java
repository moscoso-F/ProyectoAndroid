package com.example.impossiblelife.Modelo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Boton {
    float varX;
    float varY;
    float radio;

    public Boton(float varX, float varY, float radio) {
        this.varX = varX;
        this.varY = varY;
        this.radio = radio;
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


    public boolean comprobarSiTocoDentro(Float x, Float y){

        boolean devolver= false;

        if(Math.pow(getRadio(),2)>(Math.pow(x-getVarX(),2)+(Math.pow(y-getVarY(),2)))){

            devolver= true;

        }

        return devolver;
    }
}
