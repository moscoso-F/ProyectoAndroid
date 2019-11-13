package com.example.impossiblelife;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class HiloPantalla extends Thread {

    private SurfaceHolder sh;
    private GameView view;
    private boolean run;

    public HiloPantalla(SurfaceHolder sh, GameView view) {
        this.sh = sh;
        this.view = view;
        run = false;
    }


    public void run(){

        Canvas canvas=null;
        while(run){
            try{
                canvas = sh.lockCanvas(null);
                if(canvas != null){
                    synchronized (sh){
                        view.postInvalidate();
                    }
                }
            } finally {
                if(canvas != null){
                    sh.unlockCanvasAndPost(canvas);
                }
            }
        }

    }

    public void setRunning(boolean run) {
        this.run = run;
    }
}
