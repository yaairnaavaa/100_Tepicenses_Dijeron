package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class Ranking extends AppCompatActivity {
    ImageView regresar;
    ListView rankingList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle("Ranking");
        rankingList = findViewById(R.id.rankingList);

        String[] datos = {"Jugador1", "Jugador2", "Jugador3", "Jugador4",
                "Jugador5", "Jugador6", "Jugador7", "Jugador8","Jugador9",
                "Jugador1", "Jugador2", "Jugador3", "Jugador4",
                "Jugador5", "Jugador6", "Jugador7", "Jugador8","Jugador9",
                "Jugador1", "Jugador2", "Jugador3", "Jugador4",
                "Jugador5", "Jugador6", "Jugador7", "Jugador8","Jugador9"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, datos);
        rankingList.setAdapter(adapter);

        regresar = findViewById(R.id.atras);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent entrar = new Intent(Ranking.this, Principal.class);
                startActivity(entrar);

            }
        });
    }
}
