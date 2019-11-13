package com.example.impossiblelife.Sprites;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import com.example.impossiblelife.GameView;

import java.util.Random;

public class Sprite {
    private int numero;
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private GameView gameView;
    private Bitmap bmp;
    private static final int BMP_COLUMNS = 3;
    private static final int BMP_ROWS = 4;
    private static final int[] DIRECTION_TO_ANIMATION_MAP = {0, 1, 3, 2};
    private int width,height;


    private int currentFrame = 2;
    boolean moverIzq, moverDer, moverUp;


    public Sprite (GameView gameView,  Bitmap bmp){
        this.gameView = gameView;
        this.bmp = bmp;
        width = bmp.getWidth() / BMP_COLUMNS;
        height = bmp.getHeight() / BMP_ROWS;
        Random rnd = new Random();
        x = (int) (gameView.getWidth()/2-width/2);
        y= (int) (gameView.getHeight()-(gameView.getHeight()/4.75f));
        xSpeed = 10;
        ySpeed = 10;

    }

    public void update(){

        if(x<0){
            x=1;
        }else if(x>gameView.getWidth()-width-xSpeed){
            x=gameView.getWidth()-width-xSpeed;
        }else {
            if (moverIzq) {
                ySpeed=-10;
                //xSpeed=+xSpeed;
                if(xSpeed>0){
                    xSpeed=-xSpeed;
                }
                x +=xSpeed;
            } else if (moverDer) {
                ySpeed=10;
                if(xSpeed<0){
                    xSpeed=-xSpeed;
                }
                x += xSpeed;
            }else if (moverUp) {
                ySpeed=30;
                if(xSpeed<0){
                    xSpeed=-xSpeed;
                }
                if(ySpeed>0){
                    ySpeed=-ySpeed;
                }

                if(y>1200) {
                    y += ySpeed;
                }

                Log.d("AVE",String.valueOf(y));
                Log.d("AVER",String.valueOf(ySpeed));

            }

            currentFrame = ++currentFrame % BMP_COLUMNS;
        }
    }


    public void moverDer(){
        moverDer=true;
    }
    public void moverIzq(){
        moverIzq=true;
    }
    public void moverUp() {
        moverUp=true;
    }
    public void noMover(){
        moverIzq=false;
        moverDer=false;
        moverUp=false;
        y=(int) (gameView.getHeight()-(gameView.getHeight()/4.75f));
    }
    public void onDraw(Canvas canvas){
        if(moverDer || moverIzq ||moverUp) {
            //if(numero%2==0){
                update();
            //}
            //numero++;
        }
        int srcX = currentFrame * width;
        int srcY = getAnimationRow() * height;
        Rect src = new Rect(srcX, srcY, srcX + width, srcY + height);
        Rect dst = new Rect(x, y, x + width, y + height);
        canvas.drawBitmap(bmp, src, dst, null);
    }

    // direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    private int getAnimationRow() {
        double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2);
        int direction = (int) Math.round(dirDouble) % BMP_ROWS;
        return DIRECTION_TO_ANIMATION_MAP[direction];
    }

    public Rect getArea(){
        return new Rect(x, y,x + width, y + height);
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
