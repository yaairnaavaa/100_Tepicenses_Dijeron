package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


/**
 * Created by yairnava on 11/05/18.
 */

public class LienzoJuego extends View {
    int anchoP, altoP;
    Botones botones[], puntero;

    public LienzoJuego(final Context context, final int anchopantalla, final int altopantalla) {
        super(context);
        anchoP = anchopantalla;
        altoP = altopantalla;
        setBackgroundResource(R.drawable.fondo);

        int nombreArchivos [] = {R.drawable.botona,R.drawable.botonb,R.drawable.botonc,R.drawable.botond,R.drawable.botonturno};
        int coorx [] = {50,250,450,650,300};
        int coory [] = {820,820,820,820,1000};
        botones = new Botones[5];

        for(int i=0 ; i< botones.length ; i++){
            botones[i] = new Botones(BitmapFactory.decodeResource(getResources(),nombreArchivos[i]),coorx[i],coory[i]);
        }
        puntero = null;
    }

    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        //Encabezados
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        c.drawText("Usuario:",10,50,p);
        c.drawText("Usuario1",130,50,p);
        c.drawText("Tiempo:",anchoP-200,50,p);
        c.drawText("20",anchoP-80,50,p);

        p.setFakeBoldText(true);
        c.drawText("Turno: "+"NombreJugador",270,130,p);
        p.setFakeBoldText(false);

        c.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.puntuacionrespuestas),0,150,p);
        c.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.respuestaspregunta),0,500,p);


        //Tabla Respuesta - Puntuacion
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        p.setFakeBoldText(true);
        c.drawText("Respuesta",170,210,p);
        c.drawText("Puntos",500,210,p);

        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(false);
        c.drawText("Respuesta 1",170,260,p);
        c.drawText("100",520,260,p);

        c.drawText("Respuesta 2",170,310,p);
        c.drawText("75",520,310,p);

        c.drawText("Respuesta 3",170,360,p);
        c.drawText("50",520,360,p);

        c.drawText("Respuesta 4",170,410,p);
        c.drawText("25",520,410,p);

        //Pregunta
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        c.drawText("Pregunta a realizar",150,540,p);

        //Respuestas
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        c.drawText("Respuesta A",100,645,p);
        c.drawText("Respuesta B",480,645,p);
        c.drawText("Respuesta C",100,735,p);
        c.drawText("Respuesta D",480,735,p);

        //Botones
        for(int i=0 ; i<botones.length ; i++){
            c.drawBitmap(botones[i].img, botones[i].x,botones[i].y,p);
        }

    }

    public boolean onTouchEvent(MotionEvent e){
        e.getAction();
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(int i=0 ; i<botones.length ; i++) {
                    if(botones[i].estaEnArea(e.getX(),e.getY())){
                        puntero = botones[i];
                        if(puntero == botones[0]){
                            Toast.makeText(getContext(),"Opcion A",Toast.LENGTH_SHORT).show();
                        }
                        if(puntero == botones[1]){
                            Toast.makeText(getContext(),"Opcion B",Toast.LENGTH_SHORT).show();
                        }
                        if(puntero == botones[2]){
                            Toast.makeText(getContext(),"Opcion C",Toast.LENGTH_SHORT).show();
                        }
                        if(puntero == botones[3]){
                            Toast.makeText(getContext(),"Opcion D",Toast.LENGTH_SHORT).show();
                        }
                        if(puntero == botones[4]){
                            Toast.makeText(getContext(),"Opcion Turno",Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
                break;
        }
        return true;
    }
}
