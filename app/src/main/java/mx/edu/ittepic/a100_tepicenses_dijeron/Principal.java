package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
    ConexionWebRegistro cw;
    ProgressDialog dialogo;
    boolean ok;
    Bundle datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
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

        botoniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar = new Intent(Principal.this, LienzoJuegoActivity.class);
                iniciar.putExtra("id", datos.getString("id"));
                iniciar.putExtra("usuario", datos.getString("usuario"));
                startActivity(iniciar);
            }
        });

       /* botoniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar = new Intent(Principal.this, UsuariosDisponibles.class);
                iniciar.putExtra("id", datos.getString("id"));
                startActivity(iniciar);
            }
        });*/

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
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }

    /*public boolean onKeyDown(int keyCode, KeyEvent evt){
        if (evt.getKeyCode() == KeyEvent.KEYCODE_BUTTON_START){
            logout();
        }
        return true;
    }*/
    @Override
    public void onBackPressed(){
        cerrarSesion();
    }
    public void procesarRespuesta(String respuesta) {
        AlertDialog.Builder alert =  new AlertDialog.Builder(this);

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
        if (respuesta.equals("OK")){
            respuesta = "Sesión cerrada";
            ok = true;
        }
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();

        Intent salir = new Intent(Principal.this, MainActivity.class);
        startActivity(salir);
        Principal.this.finish();
    }

    public void cambiarMensaje(String s) {
        dialogo.setMessage(s);
    }
}
