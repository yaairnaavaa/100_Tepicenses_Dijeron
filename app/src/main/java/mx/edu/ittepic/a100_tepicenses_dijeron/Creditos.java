package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Creditos extends AppCompatActivity {
    ImageView regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creditos);
        setTitle("Créditos");

        regresar = findViewById(R.id.atras);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent entrar = new Intent(Creditos.this, Principal.class);
                startActivity(entrar);

            }
        });
    }
}
