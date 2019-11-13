package com.example.impossiblelife.Modelo;

import android.graphics.Canvas;

public class Rectangulo extends Figura {
    float varX, varY, ancho, alto;

    public Rectangulo(float varX, float varY, float ancho, float alto) {
        this.varX = varX;
        this.varY = varY;
        this.ancho = ancho;
        this.alto = alto;
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

    public float getAncho() {
        return ancho;
    }

    public void setAncho(float ancho) {
        this.ancho = ancho;
    }

    public float getAlto() {
        return alto;
    }

    public void setAlto(float alto) {
        this.alto = alto;
    }

    @Override
    public void dibujar(Canvas canvas) {

    }

    @Override
    public boolean comprobarSiTocoDentro(Float x, Float y) {
        boolean devolver= false;

        if(x>getVarX() && x< getVarX()+getAncho()
                && y>getVarY() && y<getVarY()+getAlto()){

            devolver= true;

        }

        return devolver;
    }

    @Override
    public void actualizar(Float x, Float y) {

    }
}
