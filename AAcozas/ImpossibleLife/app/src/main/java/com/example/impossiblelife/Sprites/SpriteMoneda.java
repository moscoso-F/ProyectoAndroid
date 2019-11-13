package com.example.impossiblelife.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.impossiblelife.GameView;

import java.util.Random;

public class SpriteMoneda {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private static final int BMP_COLUMNS = 8;
    private static final int BMP_ROWS = 1;
    private int width;
    private int height;
    private int currentFrame = 2;
    private int numero;

    public SpriteMoneda (GameView gameView,  Bitmap bmp){
        this.gameView = gameView;
        this.bmp = bmp;
        width = bmp.getWidth() / BMP_COLUMNS;
        height = bmp.getHeight() / BMP_ROWS;
        Random rnd = new Random();
        x = (int) (gameView.getWidth()/2-width/2);
        y= (int) (gameView.getHeight()-(gameView.getHeight()/4.3f));
        xSpeed = 1;

    }
    public void onDraw(Canvas canvas){
        if(numero%4==0) {
            update();
            Log.d("mpare", String.valueOf(numero));
        }
        numero++;
        int srcX = currentFrame * width;
        int srcY = 0;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }



    public void update(){

            currentFrame = ++currentFrame % BMP_COLUMNS;
        }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
