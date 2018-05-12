package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import static java.security.AccessController.getContext;

public class Principal extends AppCompatActivity {
    Button botoniniciar;
    Button botoncreditos;
    Button botonranking;
    Button botonsalir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        botoncreditos = findViewById(R.id.creditos);
        botonranking = findViewById(R.id.ranking);
        botonsalir = findViewById(R.id.cerrarsesion);
        botoniniciar = findViewById(R.id.iniciar);

        botoniniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iniciar = new Intent(Principal.this, LienzoJuegoActivity.class);
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
                finish();
                Intent ranking = new Intent(Principal.this, Ranking.class);
                startActivity(ranking);
            }
        });

        botonsalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Principal.this);
                builder.setTitle("¿Seguro que desea cerrar sesión?");
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        Intent salir = new Intent(Principal.this, MainActivity.class);
                        startActivity(salir);
                    }
                });

                builder.show();

            }
        });
    }

    @Override
    public void onBackPressed(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert
            .setTitle("¿Desea cerrar la sesión?")
            .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Principal.super.onBackPressed();
                }
            })
            .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .setCancelable(false)
            .show();
    }
}
