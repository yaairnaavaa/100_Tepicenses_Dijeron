package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class UsuariosDisponibles extends AppCompatActivity {

    ListView listUsuariosDisp;
    ConexionWebRegistro cw;
    List<String[]> datosUsuarios;
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
                this.finish();
                Toast.makeText(this, respuesta, Toast.LENGTH_LONG).show();
                return;
            case "OK":
                /*Principal.progress = ProgressDialog.show(this, "Enviando solicitud", "Esperando respuesta...");
                if (Principal.progress != null) {
                    this.finish();
                }*/
                this.finish();
                break;
            default:
                final String[] datos = respuesta.split("#");
                String[] usuarios = new String[datos.length];
                datosUsuarios = new ArrayList<String[]>();
                for(int i=0; i<datos.length; i++){
                    datosUsuarios.add(datos[i].split(","));
                    usuarios[i] = datosUsuarios.get(i)[1];
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, usuarios);
                listUsuariosDisp.setAdapter(adapter);
                listUsuariosDisp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try{
                            cw = new ConexionWebRegistro(UsuariosDisponibles.this);
                            cw.agregarVariable("idsol", getIntent().getExtras().getString("id"));
                            cw.agregarVariable("idrec", datosUsuarios.get(position)[0]);
                            URL direccion = new URL("https://tpdmagustin.000webhostapp.com/100TD/solicitudes.php");
                            cw.execute(direccion);
                        }catch(MalformedURLException err){}
                    }
                });
                break;
        }
    }
}
