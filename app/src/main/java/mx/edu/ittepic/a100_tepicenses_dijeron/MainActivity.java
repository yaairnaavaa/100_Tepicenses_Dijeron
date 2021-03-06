package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    Button botonlogin;
    TextView registro;
    EditText usuario,contraseña;
    ConexionWebRegistro cw;
    ProgressDialog dialogo;

    MediaPlayer mp;//Variable para reproducr cancion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        botonlogin = findViewById(R.id.entrar);
        registro = findViewById(R.id.registro);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);

        //Reproducir Cancion Intro
        if (mp == null){
            mp = MediaPlayer.create(this,R.raw.intro);
            mp.start();
            mp.setLooping(true);
        }
        //Reproducir Cancion Intro

        botonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usuario.getText().toString().isEmpty() || contraseña.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Campos vacios", Toast.LENGTH_LONG).show();
                    return;
                }
                try {
                    cw = new ConexionWebRegistro(MainActivity.this);
                    cw.agregarVariable("usuario", usuario.getText().toString());
                    cw.agregarVariable("contrasena", contraseña.getText().toString());
                    URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/login.php");
                    dialogo = ProgressDialog.show(MainActivity.this, "Espere", "Intentando iniciar sesión...");
                    cw.execute(direccion);
                }catch(MalformedURLException e){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });

        registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registro = new Intent(MainActivity.this, Registro.class);
                startActivity(registro);
                mp.stop();//Detener Cancion
            }
        });
    }

    public void procesarRespuesta(String respuesta) {
        dialogo.dismiss();
        final String[] datos = respuesta.split(",");
        switch (respuesta) {
            case "ERROR_404_0":
                respuesta = "Error de conexión";
                break;
            case "ERROR_404_1":
                respuesta = "Error de conexión";
                break;
            case "ERROR_404_2":
                respuesta = "Error de conexión";
                break;
            case "ERROR_0":
                respuesta = "Error de conexión";
                break;
            case "ERROR_1":
                respuesta = "Error de conexión";
                break;
            case "ERROR_2":
                respuesta = "Datos Incorrectos";
                break;
            default:
                respuesta = "Bienvenido "+datos[1];
                Intent entrar = new Intent(MainActivity.this, Principal.class);
                entrar.putExtra("id",datos[0]+"");
                entrar.putExtra("usuario", datos[1]+"");
                entrar.putExtra("puntos", datos[2]+"");
                startActivity(entrar);
                MainActivity.this.finish();
                mp.stop();//Detener Cancion
                break;
        }
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
    }

    public void cambiarMensaje(String s) {
        dialogo.setMessage(s);
    }
}
