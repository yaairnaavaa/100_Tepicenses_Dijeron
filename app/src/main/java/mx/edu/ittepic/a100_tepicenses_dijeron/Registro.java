package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Registro extends AppCompatActivity {
    ImageView regresar;
    Button registrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        setTitle("Registro Usuario");

        registrar = findViewById(R.id.registrar);
        registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent entrar = new Intent(Registro.this, MainActivity.class);
                startActivity(entrar);

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


}
