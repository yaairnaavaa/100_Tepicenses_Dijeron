package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registro Usuario");

        registrar = findViewById(R.id.registrar);
        usuario = findViewById(R.id.usuario);
        contraseña = findViewById(R.id.contraseña);
        confcontraseña = findViewById(R.id.confcontraseña);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    cw = new ConexionWebRegistro(Registro.this);
                    cw.agregarVariable("usuario", usuario.getText().toString());
                    cw.agregarVariable("contrasena", contraseña.getText().toString());

                    URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/crearusuario.php");
                    //dialogo = ProgressDialog.show(Registro.this, "Espere", "Insertando Registro...");
                    cw.execute(direccion);
                }catch(MalformedURLException e){
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
                }
                finish();
            }
        });

        regresar = findViewById(R.id.back);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent entrar = new Intent(Registro.this, Principal.class);
                startActivity(entrar);
            }
        });
    }

    public void procesarRespuesta(String respuesta) {
        AlertDialog.Builder alert =  new AlertDialog.Builder(this);
        //dialogo.dismiss();
        alert.setTitle("Mensaje del servidor").setMessage(respuesta);
        alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).show();
    }

    //public void cambiarMensaje(String s) {
       // dialogo.setMessage(s);
   // }


}
