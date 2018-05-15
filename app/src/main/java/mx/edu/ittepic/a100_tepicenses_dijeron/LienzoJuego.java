package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by yairnava on 11/05/18.
 */

public class LienzoJuego extends View {
    int anchoP, altoP;
    String idusuario,nusuario;
    Botones botones[], puntero;
    CountDownTimer timer;
    Boolean turno;
    int seg;

    //Agregar
    ConexionWebRegistro cw;
    List<Integer> numpregunta;
    String[] opciones,respuestas;
    String pregunta,o1,o2,o3,o4,p1,p2,p3,p4;
    //Agregar


    public LienzoJuego(final Context context, final int anchopantalla, final int altopantalla, String id, String usuario) {
        super(context);
        pregunta = "Pregunta";
        o1 = "R1";
        o2 = "R2";
        o3 = "R3";
        o4 = "R4";
        p1 = "pts";
        p2 = "pts";
        p3 = "pts";
        p4 = "pts";
        anchoP = anchopantalla;
        altoP = altopantalla;
        idusuario = id;
        nusuario = usuario;
        seg = 20;
        turno = false;
        numpregunta = new ArrayList<>();


        setBackgroundResource(R.drawable.fondo);

        int nombreArchivos [] = {R.drawable.botona,R.drawable.botonb,R.drawable.botonc,R.drawable.botond,R.drawable.botonturno};
        int coorx [] = {50,250,450,650,300};
        int coory [] = {820,820,820,820,1000};
        botones = new Botones[5];

        for(int i=0 ; i< botones.length ; i++){
            botones[i] = new Botones(BitmapFactory.decodeResource(getResources(),nombreArchivos[i]),coorx[i],coory[i]);
        }
        puntero = null;

        timer = new CountDownTimer(21000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                seg--;
                invalidate();
            }
            @Override
            public void onFinish() {
                seg = 20;
                turno = false;
                invalidate();
            }
        };

        inicio();

    }

    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        //Encabezados
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        c.drawText("Usuario:",10,50,p);
        c.drawText(nusuario,130,50,p);
        c.drawText("Tiempo:",anchoP-200,50,p);
        c.drawText(""+seg,anchoP-80,50,p);

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
        c.drawText(o1,170,260,p);
        c.drawText(p1,520,260,p);

        c.drawText(o2,170,310,p);
        c.drawText(p2,520,310,p);

        c.drawText(o3,170,360,p);
        c.drawText(p3,520,360,p);

        c.drawText(o4,170,410,p);
        c.drawText(p4,520,410,p);

        //Pregunta
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        c.drawText(pregunta,150,540,p);

        //Respuestas
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        c.drawText(o1,100,645,p);
        c.drawText(o2,480,645,p);
        c.drawText(o3,100,735,p);
        c.drawText(o4,480,735,p);

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
                            timer.cancel();
                            timer.onFinish();
                        }
                        if(puntero == botones[1]){
                            timer.cancel();
                            timer.onFinish();
                        }
                        if(puntero == botones[2]){
                            timer.cancel();
                            timer.onFinish();
                        }
                        if(puntero == botones[3]){
                            timer.cancel();
                            timer.onFinish();
                        }
                        if(puntero == botones[4]){
                            if(!turno){
                                turno = true;
                                inicio();
                                timer.start();
                            }
                        }
                        break;
                    }
                }
                break;
        }
        return true;
    }

    private void inicio(){
        try {
            cw = new ConexionWebRegistro(LienzoJuego.this);
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/preguntas.php");
            cw.execute(direccion);
        }catch(MalformedURLException e){
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    public void procesarRespuesta(String respuesta) {
        List<String[]> datos = new ArrayList<String[]>();
        int num = Integer.parseInt(respuesta.split("&")[1]);
        if (numpregunta.contains(num)){
            inicio();
            return;
        }
        numpregunta.add(num);
        String[] partes = respuesta.split("&")[0].split("#");
        for (int i = 0; i < partes.length; i++){
            datos.add(partes[i].split(":"));
        }

        pregunta = datos.get(0)[0];//pregunta a mostrar
        o1 = datos.get(1)[0];
        p1 = datos.get(1)[1];
        o2 = datos.get(2)[0];
        p2 = datos.get(2)[1];
        o3 = datos.get(3)[0];
        p3 = datos.get(3)[1];
        o4 = datos.get(4)[0];
        p4 = datos.get(4)[1];
        invalidate();

    }

}
