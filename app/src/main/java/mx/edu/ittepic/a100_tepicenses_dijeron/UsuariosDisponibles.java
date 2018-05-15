package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class UsuariosDisponibles extends AppCompatActivity {

    ListView listUsuariosDisp;
    ConexionWebRegistro cw;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuarios_disponibles);
        listUsuariosDisp = findViewById(R.id.listusuarios);
        setTitle("Usuarios disponibles");
        try {
            cw = new ConexionWebRegistro(UsuariosDisponibles.this);
            cw.agregarVariable("id", getIntent().getExtras().getString("id"));
            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/usuariosdisponibles.php");
            cw.execute(direccion);
        }catch(MalformedURLException e){
            Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_LONG).show();
        }
    }
    public void procesarRespuesta(String respuesta) {
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
                respuesta = "No hay usuarios disponibles";
                break;
            default:
                final String[] datos = respuesta.split("#");
                respuesta = "Lista usuarios";
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datos);
                listUsuariosDisp.setAdapter(adapter);
                Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
                break;
        }
    }
}
