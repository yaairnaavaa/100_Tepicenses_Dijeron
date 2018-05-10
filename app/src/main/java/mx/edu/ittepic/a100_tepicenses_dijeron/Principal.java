package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Principal extends AppCompatActivity {
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
                finish();
                Intent salir = new Intent(Principal.this, MainActivity.class);
                startActivity(salir);
            }
        });
    }
}
