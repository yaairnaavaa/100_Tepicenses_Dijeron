package mx.edu.ittepic.a100_tepicenses_dijeron;

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
    Registro punteroR;
    MainActivity punteroM;
    Principal punteroP;
    Ranking punteroRan;
    UsuariosDisponibles punteroUD;
    LienzoJuego punteroLJ;

    public ConexionWebRegistro(Registro p){
        punteroR = p;
        variables = new ArrayList<String[]>();
    }
    public ConexionWebRegistro(MainActivity p){
        punteroM = p;
        variables = new ArrayList<String[]>();
    }
    public ConexionWebRegistro(Principal p){
        punteroP = p;
        variables = new ArrayList<String[]>();
    }
    public ConexionWebRegistro(Ranking p){
        punteroRan = p;
        variables = new ArrayList<String[]>();
    }
    public ConexionWebRegistro(UsuariosDisponibles p){
        punteroUD = p;
        variables = new ArrayList<String[]>();
    }
    public ConexionWebRegistro(LienzoJuego p){
        punteroLJ = p;
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
            if (punteroRan == null && punteroUD == null) {
                publishProgress("Intentando conectar");
            }
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
                        respuesta += linea;
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

    protected void onProgressUpdate(String... r){
        if (punteroR != null) {
            punteroR.cambiarMensaje(r[0]);
        }
        if (punteroM != null) {
            punteroM.cambiarMensaje(r[0]);
        }
        if (punteroP != null) {
            punteroP.cambiarMensaje(r[0]);
        }
        if (punteroLJ != null) {
            //punteroLJ.cambiarMensaje(r[0]);
        }
    }

    protected void onPostExecute(String respuesta){
        if (punteroR != null) {
            punteroR.procesarRespuesta(respuesta);
        }
        if (punteroM != null) {
            punteroM.procesarRespuesta(respuesta);
        }
        if (punteroP != null) {
            punteroP.procesarRespuesta(respuesta);
        }
        if (punteroRan != null) {
            punteroRan.procesarRespuesta(respuesta);
        }
        if (punteroUD != null) {
            punteroUD.procesarRespuesta(respuesta);
        }
        if (punteroLJ != null) {
            //punteroLJ.procesarRespuesta(respuesta);
        }
    }
}

