package com.example.impossiblelife;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import com.example.impossiblelife.Conexion.Conexion;
import com.example.impossiblelife.Modelo.Circulo;
import com.example.impossiblelife.Modelo.Figura;
import com.example.impossiblelife.Modelo.Rectangulo;
import com.example.impossiblelife.Modelo.Roca;
import com.example.impossiblelife.Sprites.Sprite;
import com.example.impossiblelife.Sprites.SpriteMoneda;
import com.example.impossiblelife.Utilidades.Utilidades;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {

    GameView gameView;
    private HiloPantalla thread;
    private Paint paint;
    private Path p;
    private ArrayList<Figura> figuras;
    private Bitmap nvl1, resizenvl1, btnIzquierda, btnIzquierdaR, btnDerecha, btnDerechaR, btnSalto,
            btnSaltoR, btnRetry, btnGuardar, btnGuardarR, btnRetryR, btnFin, btnFinR, sueloo, suelooR, roca, rocaR, rocaRota, rocaRotaR, gameOver,gameOverR,moneda, monedaR, vida1, vida1R, vida2, vida2R, vida3, vida3R;
    Rectangulo suelo;
    Rectangulo cuadradoRoca;
    Rectangulo cuadradoMon;
    Roca rocaso;
    DisplayMetrics metrics = this.getResources().getDisplayMetrics();
    Sprite sprite;
    private List<Sprite> sprites = new ArrayList<Sprite>();
    private List<SpriteMoneda> spritesMon = new ArrayList<SpriteMoneda>();
    private Figura figActiva;
    int bajarRoca, contadorRocaQuieta, contador2, contadorMuerte;
    int maxX = metrics.widthPixels;
    double gravedad=1.2;
    float ancho, alto;
    boolean monedaCreada, crearPiedra, monoTocado, pantallaReset, partidaGuardada;
    int contadorVidas, puntos, tocado, ira;
    int maxY = metrics.heightPixels;
    float i1 = 0f;
    float velocidadPiedra = 0f;
    private SoundPool soundPool;
    private int sonidoMoneda, sonidoMono, sonidoMuerte;
    MediaPlayer mysong;
    int spriteSel;
    float aceX, aceY, aceZ, aceX2;
    int contadorAce=0;

    SharedPreferences prefs =
            getContext().getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);


    SensorManager sensorManager;
    Sensor sensor;
    SensorEventListener sensorEventListener;

    public GameView(Context context) {
        super(context);
        paint = new Paint();
        p= new Path();
        setBackgroundColor(Color.WHITE);
        getHolder().addCallback(this);

        nvl1= BitmapFactory.decodeResource(getResources(), R.drawable.nvl1);
        btnIzquierda= BitmapFactory.decodeResource(getResources(), R.drawable.btnleft);
        btnDerecha= BitmapFactory.decodeResource(getResources(), R.drawable.btnright);
        btnSalto= BitmapFactory.decodeResource(getResources(), R.drawable.btnjump);
        btnGuardar= BitmapFactory.decodeResource(getResources(), R.drawable.btnguardar);
        roca= BitmapFactory.decodeResource(getResources(), R.drawable.roca1);
        rocaRota= BitmapFactory.decodeResource(getResources(), R.drawable.rocarota);
        gameOver= BitmapFactory.decodeResource(getResources(), R.drawable.gameovv);
        vida1=BitmapFactory.decodeResource(getResources(), R.drawable.vidauno);
        vida2=BitmapFactory.decodeResource(getResources(), R.drawable.vidados);
        vida3=BitmapFactory.decodeResource(getResources(), R.drawable.vidatres);
        btnRetry=BitmapFactory.decodeResource(getResources(), R.drawable.retry);
        btnFin=BitmapFactory.decodeResource(getResources(), R.drawable.exit);
        sueloo=BitmapFactory.decodeResource(getResources(), R.drawable.suelo);
        suelo= new Rectangulo(0, maxY,maxX, maxY-(maxY/6f));

        iniciarBotones(maxX, maxY);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            AudioAttributes audioAttributes= new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            soundPool= new SoundPool.Builder().setMaxStreams(1)
                    .setAudioAttributes(audioAttributes)
                    .build();
        }else{
            soundPool= new SoundPool(1, AudioManager.STREAM_MUSIC,0);
        }
        sonidoMoneda= soundPool.load(this.getContext(), R.raw.coin,1);
        sonidoMono= soundPool.load(this.getContext(), R.raw.monkey,1);
        sonidoMuerte= soundPool.load(this.getContext(), R.raw.death,1);
        mysong= MediaPlayer.create(getContext(), R.raw.musica);
        sensorManager = (SensorManager) getContext().getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        createSprites();
        createSpritesMon();
        //bmpBlood=BitmapFactory.decodeResource(getResources(), R.drawable.blood);

        thread = new HiloPantalla(getHolder(), this);
        thread.setRunning(true);
        thread.start();

        //btnIzquierda= new Boton(gameView.getWidth()/10f, gameView.getHeight()-(gameView.getHeight()/8f),);
        //int minX = getWidth()/10;
        //int maxX = metrics.widthPixels-getWidth()/8;

        //int minY = 1250; //Le indico esta cantidad que es la misma que puede saltar el muñeco, lo indico en Sprite
        //int maxY = (int) (metrics.widthPixels-suelo.getAlto());
        partidaGuardada=prefs.getBoolean("partidaGuardada", false);

        if(partidaGuardada){
            monedaCreada=false;
            rocaso= new Roca(prefs.getInt("posXRocaJ", 0), prefs.getInt("posYRocaJ", 0),ancho, alto);
            contadorVidas=prefs.getInt("vidasJ", 3);
            puntos=prefs.getInt("puntosJ", 0);
        }else{
            Random ran1 = new Random();
            float i1 = ran1.nextInt((int) ((maxX-ancho) - 1 + 1)) + 1;
            monedaCreada=false;
            rocaso= new Roca(i1, maxY/11f,ancho, alto);
            contadorVidas=3;
            puntos=0;
            partidaGuardada=false;
        }


        SharedPreferences.Editor editor = prefs.edit();

        editor.putBoolean("partidaGuardada", false);

        editor.commit();
    }

    private void createSprites() {
        spriteSel=prefs.getInt("spriteSel", 1);
        if(spriteSel==1) {
            sprites.add(createSprite(R.drawable.mono));
        }else if(spriteSel==2){
            sprites.add(createSprite(R.drawable.cabra));
        }else if(spriteSel==3){
            sprites.add(createSprite(R.drawable.mapashe));
        }
    }



    private void createSpritesMon() {spritesMon.add(createSpritesMon(R.drawable.monedaoro));}

    private Sprite createSprite(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new Sprite(this, bmp);
    }

    private SpriteMoneda createSpritesMon(int resouce) {
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), resouce);
        return new SpriteMoneda(this, bmp);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        spritesMon.remove(0);
        sprites.remove(0);
        mysong.stop();
        boolean retry = true;
        thread.setRunning(false);

        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        maxX = metrics.widthPixels;
        maxY = metrics.heightPixels;

        resizenvl1 = Bitmap.createScaledBitmap(nvl1, canvas.getWidth(), canvas.getHeight(), false);
        btnIzquierdaR = Bitmap.createScaledBitmap(btnIzquierda,(int)(canvas.getWidth()*0.15),(int)(canvas.getWidth()*0.15), false);
        btnDerechaR = Bitmap.createScaledBitmap(btnDerecha,(int)(canvas.getWidth()*0.15),(int)(canvas.getWidth()*0.15), false);
        btnSaltoR = Bitmap.createScaledBitmap(btnSalto,(int)(canvas.getWidth()*0.15),(int)(canvas.getWidth()*0.15), false);
        btnRetryR = Bitmap.createScaledBitmap(btnRetry,(int)(canvas.getWidth()*0.15),(int)(canvas.getWidth()*0.15), false);
        btnFinR = Bitmap.createScaledBitmap(btnFin,(int)(canvas.getWidth()*0.18),(int)(canvas.getWidth()*0.18), false);
        btnGuardarR = Bitmap.createScaledBitmap(btnGuardar,(int)(canvas.getWidth()*0.10),(int)(canvas.getWidth()*0.10), false);
        rocaR = Bitmap.createScaledBitmap(roca,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.23), false);
        rocaRotaR = Bitmap.createScaledBitmap(rocaRota,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.23), false);
        vida1R = Bitmap.createScaledBitmap(vida1,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.12), false);
        vida2R = Bitmap.createScaledBitmap(vida2,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.12), false);
        vida3R = Bitmap.createScaledBitmap(vida3,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.12), false);
        gameOverR = Bitmap.createScaledBitmap(gameOver,(int)(canvas.getWidth()*1),(int)(canvas.getHeight()*1), false);
        suelooR = Bitmap.createScaledBitmap(sueloo,(int)(canvas.getWidth()*1.2),(int)(canvas.getHeight()*0.18), false);

        ancho=rocaR.getWidth();
        alto=rocaR.getHeight();

        //monedaR=Bitmap.createScaledBitmap(moneda,(int)(canvas.getWidth()*0.23),(int)(canvas.getWidth()*0.23), false);
        canvas.drawBitmap(resizenvl1, 0,0,paint);
        paint.setColor(Color.RED);
        //canvas.drawRect(suelo.getVarX(), suelo.getVarY(), suelo.getAncho(), suelo.getAlto(),paint);
        canvas.drawBitmap(suelooR,-20,suelo.getAlto()-20,paint);
        canvas.drawBitmap(btnIzquierdaR,maxX/10f,maxY-(maxY/8f),paint);
        canvas.drawBitmap(btnDerechaR,maxX-(maxX/4f),maxY-(maxY/8f),paint);
        canvas.drawBitmap(btnSaltoR,maxX-(maxX/4f),maxY-(maxY/3.5f),paint);
        canvas.drawBitmap(btnGuardarR,250,45,paint);



        paint.setColor(Color.BLACK);
        paint.setTextSize(canvas.getWidth()/25);
        canvas.drawText("POINTS: "+String.valueOf(puntos), 25, canvas.getHeight()/12, paint);
        //canvas.drawBitmap(monedaR,cuadradoMon.getVarX(),cuadradoMon.getVarY(),paint);
        //cuadradoRoca= new Rectangulo(i1, maxY/11f,rocaR.getWidth(), rocaR.getHeight());
        //////////////////////////////////////////////////////rocaso= new Roca(i1, maxX/11f,ancho, alto);
        //bajarRoca= (int) cuadradoRoca.getVarY();

        if(contadorVidas==3){
            contadorMuerte=0;
            canvas.drawBitmap(vida3R,0,0,paint);
            pantallaReset=false;
            mysong.start();
        }else if(contadorVidas==2){
            canvas.drawBitmap(vida2R,0,0,paint);
            pantallaReset=false;
        }else if(contadorVidas==1){
            canvas.drawBitmap(vida1R,0,0,paint);
            pantallaReset=false;
        }


        //canvas.drawBitmap(rocaR,rocaso.getVarX(),rocaso.getVarY(),paint);



        //-------------------HASTA AQUÍ EL FONDO Y EL SUELO-------------------------------------------
        for(Figura figura: figuras){
            figura.dibujar(canvas);
        }
        for (Sprite sprite : sprites) {
            sprite.onDraw(canvas);
        }

        moverPiedra(canvas);
        crearMonedas(canvas);
        nuevaPiedra(canvas);

        if(contadorVidas==0){
            if(contadorMuerte==0) {
                mysong.pause();
                soundPool.play(sonidoMuerte, 1, 1, 0, 0, 1);
                contadorMuerte++;
                Conexion conn = new Conexion(getContext(), "bd_records", null, 1);
                SQLiteDatabase db = conn.getWritableDatabase();
                String insert = "INSERT INTO " + Utilidades.TABLA_RECORDS + " ( " + Utilidades.CAMPO_TIEMPO + "," +
                        Utilidades.CAMPO_NOMBRE + ")" + " VALUES ('" + puntos+ "','"
                        + prefs.getString("nick", "0") + "')";  //AÑADIIIIIIIIIIIIIIIIIIIIR TIEMPO Y NOOOOOOOOMBREEEEEEE
                db.execSQL(insert);
                db.close();
            }
            canvas.drawBitmap(gameOverR,0,0,paint);
            canvas.drawBitmap(btnRetryR,maxX-(maxX/6f),maxY-(maxY/2.55f),paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(canvas.getWidth()/25);
            canvas.drawText("¿REINTENTAR?", maxX-(maxX/3.6f),maxY-(maxY/3.8f),paint);
            canvas.drawBitmap(btnFinR,maxX-(maxX/1.7f),maxY-(maxY/6.6f),paint);
            paint.setColor(Color.WHITE);
            paint.setTextSize(canvas.getWidth()/25);
            canvas.drawText("SALIR", maxX-(maxX/1.8f),maxY-(maxY/50f),paint);
            bajarRoca=maxY/11;
            monoTocado=false;
            pantallaReset=true;




            SensorEventListener accelListener = new SensorEventListener() {
                public void onAccuracyChanged(Sensor sensor, int acc) { }

                public void onSensorChanged(SensorEvent event) {

                    if(contadorAce==0){
                        aceX2 = event.values[0];
                        contadorAce++;
                    }
                    aceX = event.values[0];
                    aceY = event.values[1];
                    aceZ = event.values[2];

                }
            };
            sensorManager.registerListener(accelListener, sensor,
                    SensorManager.SENSOR_DELAY_NORMAL);

            if (aceX>aceX2+20) {

                contadorVidas = 3;
                puntos = 0;
                mysong.start();
                Log.d("entro", String.valueOf(aceX));
            }
        }
    }

    private void crearMonedas(Canvas canvas) {

        if(!monedaCreada) {
            int minMX = 0;
            int maxMX = metrics.widthPixels - spritesMon.get(0).getWidth();

            int minMY = 1200;//(int) (metrics.heightPixels-suelo.getVarY()-200);
            int maxMY = (int) (suelo.getAlto()-spritesMon.get(0).getHeight());
            Log.d("PINTO", String.valueOf(maxMY));
            Random ranM1 = new Random();
            float iM1 = ranM1.nextInt(maxMX - minMX + 1) + minMX;
            Random ranM2 = new Random();
            float iM2 = ranM2.nextInt(maxMY - minMY + 1) + minMY;


            for (SpriteMoneda spriteMoneda : spritesMon) {
                spriteMoneda.setX((int) iM1);
                spriteMoneda.setY((int) iM2);
                spriteMoneda.onDraw(canvas);
                monedaCreada = true;
            }
        }else{
            for (SpriteMoneda spriteMoneda : spritesMon) {
                spriteMoneda.onDraw(canvas);
            }
            comprobarChoqueMoneda(spritesMon.get(0).getX(), spritesMon.get(0).getY(), sprites.get(0).getX(), sprites.get(0).getY());
        }
    }

    private void comprobarChoqueMoneda(int xMon, int yMon, int x1Sp, int y1Sp) {
        if(x1Sp+20+sprites.get(0).getWidth()/2 > xMon&& x1Sp+20+sprites.get(0).getWidth()/2< xMon + spritesMon.get(0).getWidth() && y1Sp > yMon && y1Sp < yMon + spritesMon.get(0).getHeight()
                || x1Sp-20+sprites.get(0).getWidth()/2 > xMon&& x1Sp-20+sprites.get(0).getWidth()/2< xMon + spritesMon.get(0).getWidth() && y1Sp > yMon && y1Sp < yMon + spritesMon.get(0).getHeight()){
            monedaCreada=false;
            puntos++;
            soundPool.play(sonidoMoneda, 1, 1, 0, 0, 1);
        }
    }

    private boolean comprobarChoque(float i1, Canvas canvas, int x2, int y2) {
        return x2 >i1 && x2 < i1+ancho && y2<bajarRoca+alto;
    }




    private void nuevaPiedra(Canvas canvas) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if(crearPiedra){



                    //Random ran2 = new Random();
                    //float i2 = ran2.nextInt(maxY - minY + 1) + minY;

                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.RED);
                    paint.setStrokeWidth(5);

                            bajarRoca= (int) (maxY/11f);
                            Random ran1 = new Random();
                            final int ale = ran1.nextInt((int) ((maxX-ancho) - 1 + 1)) + 1;
                            rocaso= new Roca(ale, maxX/11f, ancho, alto); //maxX/11 para que se vea la roca cuando se inicie
                            contadorRocaQuieta=0;
                            //cuadradoRoca= new Rectangulo(i1, maxY/11f,roca.getWidth(), roca.getHeight());
                            ;

                        }



                    crearPiedra=false;

                }




        }, 2000);

        }

    private void moverPiedra(Canvas canvas) {


        if(bajarRoca+alto>=suelo.getAlto()){ //si quito -rocaHeight "funciona"


            canvas.drawBitmap(rocaRotaR,rocaso.getVarX(),bajarRoca,paint);  //Para que la roca no caiga hasta abajo si toca el suelo
            if(contador2==0) {
                crearPiedra = true;
                contador2++;
            }

        }else {




            if(!monoTocado && comprobarChoque(rocaso.getVarX(), canvas, sprites.get(0).getX(), sprites.get(0).getY())){

                monoTocado=true;
                crearPiedra=true;
                if(contadorRocaQuieta<70) {
                    //bajarRoca= (int) ((int) suelo.getAlto()-alto-sprites.get(0).getHeight());
                    if(contadorRocaQuieta==0){
                        contadorVidas--;
                        soundPool.play(sonidoMono, 1, 1, 0, 0, 1);
                        tocado= (int) (bajarRoca);//(suelo.getAlto()-alto-sprites.get(0).getHeight());
                        ira= (int) rocaso.getVarX();
                        //contadorRocaQuieta++;
                        //crearPiedra=true;
                        //bajarRoca=maxY/11;
                    }
                    canvas.drawBitmap(rocaRotaR, rocaso.getVarX(), tocado, paint);
                    //canvas.drawBitmap(gameOver, maxX / 10f, maxY / 3, paint);
                    contadorRocaQuieta++;
                    //bajarRoca=maxY/11;

                }
            }else{
                if(!crearPiedra) {
                    if (!monoTocado) {
                        if(prefs.getInt("nivel", 0)==0){
                            velocidadPiedra=0.025f;
                        }else if(prefs.getInt("nivel", 0)==1){
                            velocidadPiedra=0.037f;
                        }else if(prefs.getInt("nivel", 0)==2){
                            velocidadPiedra=0.050f;
                        }
                        bajarRoca += 1 + bajarRoca * velocidadPiedra; // Aquí simulo la gravedad
                        Log.d("IRALOKO", String.valueOf(bajarRoca));
                        canvas.drawBitmap(rocaR, rocaso.getVarX(), bajarRoca, paint);
                        contador2=0;

                    } else {
                        contador2=0;
                        //bajarRoca=maxX/11; //Si quito esto la roca llega hasta abajo

                        canvas.drawBitmap(rocaRotaR, ira, tocado, paint);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                monoTocado = false;

                                //crearPiedra=true;
                                //bajarRoca = maxY / 11;
                                //tocado=maxX/11;
                            }

                        }, 2000);

                    }
                }

            }


        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: // move

                for(Figura figura: figuras){
                    if(figura.comprobarSiTocoDentro(event.getX(),event.getY())) {
                        figActiva = figura;
                    }

                }

                Circulo c=(Circulo) figActiva;
                Rectangulo r=(Rectangulo)figActiva;

                if (c.getNombre().equals("up")) {
                    c.setColor(Color.GRAY);
                    sprites.get(0).moverUp();
                }
                if (c.getNombre().equals("izquierda")) {
                    c.setColor(Color.GRAY);
                    sprites.get(0).moverIzq();
                }
                if (c.getNombre().equals("derecha")) {
                    c.setColor(Color.GRAY);
                    sprites.get(0).moverDer();
                }
                if (c.getNombre().equals("guardar")) {
                    c.setColor(Color.RED);
                    Toast.makeText(getContext(),"Partida Guardada",Toast.LENGTH_LONG).show();
                    SharedPreferences.Editor editor = prefs.edit();

                    editor.putBoolean("partidaGuardada", true);
                    editor.putInt("vidasJ", contadorVidas);
                    editor.putInt("puntosJ", puntos);

                    editor.putInt("posXRocaJ", (int) rocaso.getVarX());
                    editor.putInt("posYRocaJ", bajarRoca);

                    editor.putInt("posXMonoJ", sprites.get(0).getX());
                    editor.putInt("posYMonoJ", sprites.get(0).getY());

                    editor.putInt("posXMonedaJ", spritesMon.get(0).getX());
                    editor.putInt("posYMonedaJ", spritesMon.get(0).getY());

                    editor.commit();
                    ((Activity) getContext()).finish();
                }


                if(pantallaReset) {

                    if (c.getNombre().equals("reset") || aceX+10!=aceX2) {

                        contadorVidas = 3;
                        puntos = 0;
                        mysong.start();
                        Log.d("entro", String.valueOf(aceX));
                    }
                    if (c.getNombre().equals("fin")) {

                        ((Activity) getContext()).finish();

                    }
                }
                break;


            case MotionEvent.ACTION_MOVE:

                break;

            case MotionEvent.ACTION_CANCEL:

                break;

            case MotionEvent.ACTION_UP:
                Circulo ca=(Circulo) figActiva;
                ca.setColor(Color.TRANSPARENT);
                figActiva=null;
                sprites.get(0).noMover();
                break;


        }
        return true;
    }

    public void iniciarBotones(int maxX, int maxY){
        figuras=new ArrayList<>();
        figuras.add(new Circulo(85+(maxX/10f), 85+maxY-(maxY/8f), 85, "izquierda",Color.TRANSPARENT));
        figuras.add(new Circulo(85+(maxX-(maxX/4f)), 85+(maxY-(maxY/8f)), 85,"derecha",Color.TRANSPARENT));
        figuras.add(new Circulo(85+(maxX-(maxX/4f)), 85+(maxY-(maxY/3.5f)), 85,"up",Color.TRANSPARENT));
        figuras.add(new Circulo(85+maxX-(maxX/6f),85+maxY-(maxY/2.55f), 85,"reset",Color.TRANSPARENT));
        figuras.add(new Circulo(85+maxX-(maxX/1.7f),85+maxY-(maxY/6.6f), 85,"fin",Color.TRANSPARENT));
        figuras.add(new Circulo(55+250,55+45, 55,"guardar",Color.TRANSPARENT));
        //figuras.add(new Rectangulo(miX/3f,miY/3f,miX/3f,miY/3f));
    }


}
