package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

import static java.security.AccessController.getContext;

public class Principal extends AppCompatActivity {
    Button botoniniciar;
    Button botoncreditos;
    Button botonranking;
    Button botonsalir;
    TextView bienvenida,puntuacion;
    ConexionWebRegistro cw, conexionWeb;
    ProgressDialog dialogo;
    static ProgressDialog progress;
    boolean ok, isShow;
    Bundle datos;
    Handler handler;
    Runnable runnable;
    boolean dismiss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        handler = new Handler();
        progress = null;
        dismiss = false;
        isShow = false;
        botoncreditos = findViewById(R.id.creditos);
        botonranking = findViewById(R.id.ranking);
        botonsalir = findViewById(R.id.cerrarsesion);
        botoniniciar = findViewById(R.id.iniciar);
        bienvenida = findViewById(R.id.usrbienvenido);
        puntuacion = findViewById(R.id.usrpuntuacion);
        ok = false;
        datos = this.getIntent().getExtras();
        bienvenida.setText(datos.getString("usuario"));
        puntuacion.setText("Puntuación: "+datos.getString("puntos"));

        /*botoniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar = new Intent(Principal.this, LienzoJuegoActivity.class);
                iniciar.putExtra("id", datos.getString("id"));
                iniciar.putExtra("usuario", datos.getString("usuario"));
                startActivity(iniciar);
            }
        });*/

        botoniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar = new Intent(Principal.this, UsuariosDisponibles.class);
                iniciar.putExtra("id", datos.getString("id"));
                startActivity(iniciar);
            }
        });

        botoncreditos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creditos = new Intent(Principal.this, Creditos.class);
                startActivity(creditos);
            }
        });

        botonranking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ranking = new Intent(Principal.this, Ranking.class);
                startActivity(ranking);
            }
        });

        botonsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesion();
            }
        });

        runnable = new Runnable(){
            @Override
            public void run(){
                try{
                    conexionWeb = new ConexionWebRegistro(Principal.this);
                    conexionWeb.agregarVariable("id", datos.getString("id"));
                    URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/consultasolicitudpendiente.php");
                    conexionWeb.execute(direccion);
                }catch (MalformedURLException err){}
                handler.postDelayed(this, 5000);
            }
        };
        runnable.run();
    }

    private void cerrarSesion(){
        AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
        builder.setTitle("¿Seguro que desea cerrar sesión?");
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder
            .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    handler.removeCallbacks(runnable);
                    logout();
                }
            })
            .setCancelable(false)
            .show();
    }
    private void logout(){
        try {
            cw = new ConexionWebRegistro(Principal.this);
            cw.agregarVariable("id", datos.getString("id"));
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/cerrarsesion.php");
            dialogo = ProgressDialog.show(Principal.this, "Espere", "Cerrando Sesión...");
            cw.execute(direccion);
        }catch(MalformedURLException e){
            //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onBackPressed(){
        cerrarSesion();
    }
    public void procesarRespuesta(String respuesta) {
        System.out.println("respuesta");

        if (dialogo != null) {
            dialogo.dismiss();
        }
        if (respuesta.equals("ERROR_0")){
            respuesta = "Error de conexión";
        }
        if (respuesta.equals("ERROR_1")){
            respuesta = "Error de conexión";
        }
        if (respuesta.equals("ERROR_2")){
            respuesta = "Usuario no registrado";
        }
        if (respuesta.equals("LOGOUT")){
            respuesta = "Sesión cerrada";
            ok = true;
            Intent salir = new Intent(Principal.this, MainActivity.class);
            startActivity(salir);
            Principal.this.finish();
        }
        if (respuesta.contains("stat:")){
            if (respuesta.split(":").length == 1){
                //respuesta = "";
                System.out.println("============= vacio =============");
                return;
            }
            if(respuesta.split(":")[1].equals("1")){
                //respuesta = "";
                if (progress != null) {
                    progress.dismiss();
                }
                if (!dismiss) {
                    Intent iniciar = new Intent(Principal.this, LienzoJuegoActivity.class);
                    iniciar.putExtra("id", datos.getString("id"));
                    iniciar.putExtra("usuario", datos.getString("usuario"));
                    startActivity(iniciar);
                    dismiss = true;
                    //this.finish();
                }
                return;
            }
            if (respuesta.split(":")[1].equals("0")) {
                AlertDialog.Builder newSolicitud = new AlertDialog.Builder(this);
                newSolicitud.setTitle("Solicitud de juego")
                        .setMessage("Usted tiene una solicitud pendiente para jugar")
                        .setPositiveButton("Iniciar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                respuestaSolicitud(datos.getString("id"), "1");
                                //dialog.dismiss();
                                //dismiss = true;
                            }
                        })
                        .setNegativeButton("Rechazar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                respuestaSolicitud(datos.getString("id"), "0");
                                //dialog.dismiss();
                            }
                        })
                        .setCancelable(false);
                        if (!isShow) {
                            newSolicitud.show();
                            isShow = true;
                        }
                respuesta = "";
            }
        }
        //Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
    }

    public void cambiarMensaje(String s) {
        if (dialogo != null) {
            dialogo.setMessage(s);
        }
    }

    private void respuestaSolicitud(String id, String opcion){
        try{
            cw = new ConexionWebRegistro(this);
            cw.agregarVariable("id", id);
            cw.agregarVariable("opcion", opcion);
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/respuestasolicitud.php");
            cw.execute(direccion);
        }catch (MalformedURLException err){}
    }
    protected void onStop(){
        handler.removeCallbacks(runnable);
        super.onStop();
    }
    protected void onPause(){
        handler.removeCallbacks(runnable);
        super.onPause();
    }
    protected void onResume(){
        runnable.run();
        isShow = false;
        dismiss = false;
        super.onResume();
    }
    protected void onDestroy(){
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }
}
