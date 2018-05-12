package mx.edu.ittepic.a100_tepicenses_dijeron;

/**
 * Created by yairnava on 11/05/18.
 */

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class LienzoJuegoActivity extends AppCompatActivity {
    Bundle datos;
    @Override
    public void onCreate(Bundle instanceState){
        super.onCreate(instanceState);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        datos = getIntent().getExtras();
        setContentView(new LienzoJuego(this, size.x, size.y,datos.getString("id"),datos.getString("usuario")));

    }
}