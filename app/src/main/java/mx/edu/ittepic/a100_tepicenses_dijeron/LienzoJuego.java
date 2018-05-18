package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by yairnava on 11/05/18.
 */

public class LienzoJuego extends View {
    int anchoP, altoP, puntospartida;
    String idusuario,nusuario;
    Botones botones[], puntero;
    CountDownTimer timer;
    Boolean turno,rra,rrb,rrc,rrd;
    int seg;
    List<String[]> datos = new ArrayList<String[]>();
    ConexionWebRegistro cw,cwRespuestas, conexionweb;
    List<Integer> numpregunta;
    String pregunta,preguntar2,o1,o2,o3,o4,p1,p2,p3,p4;
    Handler handler;

    public LienzoJuego(final Context context, final int anchopantalla, final int altopantalla, String id, String usuario) {
        super(context);
        handler = new Handler();
        puntospartida = 0;
        pregunta = "Pregunta R1";
        preguntar2 = "Pregunta R2";
        o1 = "R1";
        o2 = "R2";
        o3 = "R3";
        o4 = "R4";
        p1 = "pts1";
        p2 = "pts2";
        p3 = "pts3";
        p4 = "pts4";
        anchoP = anchopantalla;
        altoP = altopantalla;
        idusuario = id;
        nusuario = usuario;
        seg = 20;
        turno = false;
        numpregunta = new ArrayList<>();
        rra = rrb = rrc = rrd = false;
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
                //turno = false;
                enviarRespuesta(idusuario,"0","0");
                invalidate();
            }
        };
        inicio(-1);
        new Runnable(){
            @Override
            public void run(){
                try{
                    conexionweb = new ConexionWebRegistro(LienzoJuego.this);
                    conexionweb.agregarVariable("id", idusuario);
                    URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/consultarespuesta.php");
                    conexionweb.execute(direccion);
                }catch (MalformedURLException error){}
                handler.postDelayed(this, 1000);
            }
        }.run();
    }

    protected void onDraw(Canvas c) {
        Paint p = new Paint();
        //Encabezados
        p.setColor(Color.WHITE);
        p.setTextSize(30);
        c.drawText("Usuario:",10,50,p);
        c.drawText(nusuario,130,50,p);
        c.drawText("Puntos Partida:",270,480,p);
        c.drawText(""+puntospartida,500,480,p);
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
        if(rrb == true){
            c.drawText(o1,170,260,p);
            c.drawText(p1,520,260,p);
        }
        if(rrc == true){
            c.drawText(o2,170,310,p);
            c.drawText(p2,520,310,p);
        }
        if(rrd == true){
            c.drawText(o3,170,360,p);
            c.drawText(p3,520,360,p);
        }
        if(rra == true){
            c.drawText(o4,170,410,p);
            c.drawText(p4,520,410,p);
        }

        //Pregunta
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        c.drawText(pregunta,150,540,p);
        c.drawText(preguntar2,150,570,p);

        //Respuestas
        p.setColor(Color.WHITE);
        p.setTextSize(25);
        p.setFakeBoldText(true);
        if (!rra) {
            c.drawText(o4, 100, 645, p);
        }
        if(!rrb) {
            c.drawText(o1, 480, 645, p);
        }
        if(!rrc){
            c.drawText(o2,100,735,p);
        }
        if(!rrd){
            c.drawText(o3,480,735,p);
        }

        //Botones
            c.drawBitmap(botones[4].img, botones[4].x,botones[4].y,p);
            for(int i=0 ; i<botones.length ; i++) {
                c.drawBitmap(botones[i].img, botones[i].x, botones[i].y, p);
            }

    }

    public boolean onTouchEvent(MotionEvent e){
        switch (e.getAction()){
            case MotionEvent.ACTION_DOWN:
                for(int i=0 ; i<botones.length ; i++) {
                    if(botones[i].estaEnArea(e.getX(),e.getY())){
                        puntero = botones[i];
                        //Boton A
                        if(puntero == botones[0]){
                            if(!rra){
                                puntospartida += Integer.parseInt(datos.get(4)[1].trim());
                                timer.cancel();
                                timer.onFinish();
                                enviarRespuesta(idusuario,"4",datos.get(4)[1]);
                            }
                            rra = true;
                        }
                        //Boton B
                        if(puntero == botones[1]){
                            if(!rrb){
                                puntospartida += Integer.parseInt(datos.get(1)[1].trim());
                                timer.cancel();
                                timer.onFinish();
                                enviarRespuesta(idusuario,"1",datos.get(1)[1]);
                            }
                            rrb = true;
                        }
                        //Boton C
                        if(puntero == botones[2]){
                            if(!rrc){
                                puntospartida += Integer.parseInt(datos.get(2)[1].trim());
                                timer.cancel();
                                timer.onFinish();
                                enviarRespuesta(idusuario,"2",datos.get(2)[1]);
                            }
                            rrc = true;
                        }
                        //Boton D
                        if(puntero == botones[3]){
                            if(!rrd){
                                puntospartida += Integer.parseInt(datos.get(3)[1].trim());
                                timer.cancel();
                                timer.onFinish();
                                enviarRespuesta(idusuario,"3",datos.get(3)[1]);
                            }
                            rrd = true;
                        }
                        if(puntero == botones[4]){
                            if(!turno){
                                turno = true;
                                rra = rrb = rrc = rrd = false;
                                enviarRespuesta(idusuario,"5","0");
                                //inicio();
                                timer.start();
                            }
                        }
                    }
                }
                break;
        }
        return true;
    }

    private void inicio(int _pregunta){
        try {
            cw = new ConexionWebRegistro(LienzoJuego.this);
            cw.agregarVariable("id", idusuario);
            cw.agregarVariable("pregunta", _pregunta+"");
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/preguntas.php");
            cw.execute(direccion);
        }catch(MalformedURLException e){
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    private void enviarRespuesta(String id, String respuesta, String puntosPartida){
        try{
            cwRespuestas = new ConexionWebRegistro(LienzoJuego.this);
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/actualizarRespuestasTurno.php");
            cwRespuestas.agregarVariable("id",id);
            cwRespuestas.agregarVariable("respuesta",respuesta);
            cwRespuestas.agregarVariable("puntos",puntosPartida);
            cwRespuestas.execute(direccion);
        }catch(MalformedURLException e){
            Toast.makeText(getContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    public void actualizarVariables(String[] pdiv){
        System.out.println("tamaño: "+pdiv.length+" pregunta: "+pdiv[0]);
        pregunta = pdiv[0];//pregunta a mostrar R1
        preguntar2 = pdiv[1];//pregunta a mostrar R2

        o1 = datos.get(1)[0];
        p1 = datos.get(1)[1];
        o2 = datos.get(2)[0];
        p2 = datos.get(2)[1];
        o3 = datos.get(3)[0];
        p3 = datos.get(3)[1];
        o4 = datos.get(4)[0];
        p4 = datos.get(4)[1];
    }

    public void procesarRespuesta(String respuesta) {
        if (respuesta.equals("OK")){
            Toast.makeText(getContext(), respuesta, Toast.LENGTH_SHORT).show();
            return;
        }
        if (respuesta.equals("TRUE")){
            enviarRespuesta(idusuario, "5", "0");
            inicio(numpregunta.get(numpregunta.size()-1));
            return;
        }
        if (respuesta.equals("FALSE")){
            System.out.println("FALSE");
            return;
        }
        int num = Integer.parseInt(respuesta.split("¬")[1]);
        limpiarVariables();
        if (numpregunta.contains(num)){
            inicio(num);
            return;
        }
        numpregunta.add(num);
        String[] partes = respuesta.split("¬")[0].split("#");
        for (int i = 0; i < partes.length; i++){
            datos.add(partes[i].split(":"));
        }
        String[] pdiv = datos.get(0)[0].split(",");
        actualizarVariables(pdiv);
        invalidate();
    }
    private void limpiarVariables(){
        datos.clear();
        pregunta = "";
        preguntar2 = "";
        o1 = "";
        p1 = "";
        o2 = "";
        p2 = "";
        o3 = "";
        p3 = "";
        o4 = "";
        p4 = "";
    }

}
