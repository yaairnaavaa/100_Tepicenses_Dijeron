package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.RegionIterator;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Registro extends AppCompatActivity {
    ImageView regresar;
    Button registrar;
    ProgressDialog dialogo;
    EditText usuario,contraseña,confcontraseña;
    ConexionWebRegistro cw;
    boolean ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registro Usuario");
        ok = false;
        registrar = findViewById(R.id.registrar);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        confcontraseña = findViewById(R.id.confcontraseña);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!contraseña.getText().toString().equals(confcontraseña.getText().toString())){
                    procesarRespuesta("Las contraseñas no coinciden");
                    return;
                }
                try {
                    cw = new ConexionWebRegistro(Registro.this);
                    cw.agregarVariable("usuario", usuario.getText().toString());
                    cw.agregarVariable("contrasena", contraseña.getText().toString());

                    URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/crearusuario.php");
                    dialogo = ProgressDialog.show(Registro.this, "Espere", "Insertando Registro...");
                    cw.execute(direccion);
                }catch(MalformedURLException e){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        });

        regresar = findViewById(R.id.back);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent entrar = new Intent(Registro.this, MainActivity.class);
                startActivity(entrar);
            }
        });
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
        if (respuesta.equals("ERROR_3")){
            respuesta = "El usuario ya existe";
        }
        if (respuesta.equals("OK")){
            respuesta = "Registro exitoso";
            ok = true;
        }
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
        if (ok){
            Registro.this.finish();
        }
    }

    public void cambiarMensaje(String s) {
       dialogo.setMessage(s);
    }
}
