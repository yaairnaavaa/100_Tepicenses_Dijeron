package mx.edu.ittepic.a100_tepicenses_dijeron;

/**
 * Created by yairnava on 11/05/18.
 */

/**
 * Created by yairnava on 11/05/18.
 */
import android.os.AsyncTask;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class ConexionWebRegistro extends AsyncTask<URL, String, String>{

    List<String[]> variables;
    Registro puntero;

    public ConexionWebRegistro(Registro p){
        puntero = p;
        variables = new ArrayList<String[]>();
    }

    public void agregarVariable(String nombre, String contenido){
        String[] temporal = {nombre, contenido};
        variables.add(temporal);
    }

    @Override
    protected String doInBackground(URL... urls) {

        String POST="";
        String respuesta="";

        for (String[] var: variables){
            try{
                POST += var[0]+"="+ URLEncoder.encode(var[1], "UTF-8")+" ";
            }catch(Exception e){
                return "ERROR_404_0";
            }
        }
        POST = POST.trim();
        POST = POST.replace(" ", "&");

        HttpURLConnection conexion = null;

        try{
            //publishProgress("Intentando conectar");
            conexion = (HttpURLConnection)urls[0].openConnection();
            conexion.setDoInput(true);
            conexion.setFixedLengthStreamingMode(POST.length());//Método para indicar la cantidad de bytes que se van a envia
            conexion.setRequestMethod("POST");
            conexion.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            OutputStream flujoSalida = new BufferedOutputStream(conexion.getOutputStream());
            flujoSalida.write(POST.getBytes());
            flujoSalida.flush();
            flujoSalida.close();

            if (conexion.getResponseCode() == 200) {
                InputStreamReader input = new InputStreamReader(conexion.getInputStream(), "UTF-8");
                BufferedReader flujoEntrada = new BufferedReader(input);
                String linea = "";
                do{
                    linea = flujoEntrada.readLine();
                    if (linea!=null){
                        respuesta += linea+"\n";
                    }
                }while(linea != null);
                flujoEntrada.close();
            }else
                return "ERROR_404_1";
        }catch (UnknownHostException e){
            return "ERROR_404_2";
        }catch (IOException er) {
            return "ERROR_404_1";
        }finally {
            if (conexion != null){
                conexion.disconnect();
            }
        }
        return respuesta;
    }

    //protected void onProgressUpdate(String... r){
       // puntero.cambiarMensaje(r[0]);
    //}

    //protected void onPostExecute(String respuesta){
      //  puntero.procesarRespuesta(respuesta);
    //}
}

