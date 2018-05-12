package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;

public class Ranking extends AppCompatActivity {
    ImageView regresar;
    ListView rankingList;
    ConexionWebRegistro cw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        setTitle("Ranking");
        rankingList = findViewById(R.id.rankingList);
        try {
            cw = new ConexionWebRegistro(Ranking.this);
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/consultaranking.php");
            cw.execute(direccion);
        }catch(MalformedURLException e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
        regresar = findViewById(R.id.atras);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void procesarRespuesta(String respuesta) {
        if (respuesta.equals("ERROR_0")){
            respuesta = "Error de conexión";
        }
        if (respuesta.equals("ERROR_1")){
            respuesta = "Error de conexión";
        }
        if (respuesta.equals("ERROR_2")){
            respuesta = "No hay usuarios";
        }
        final String[] datos = respuesta.split("#");
        respuesta = "Ranking";
        RankingAdapter adapter = new RankingAdapter(this, datos);
        rankingList.setAdapter(adapter);
        Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
    }
}
