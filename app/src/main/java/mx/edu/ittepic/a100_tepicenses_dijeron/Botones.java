package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.graphics.Bitmap;

/**
 * Created by yairnava on 11/05/18.
 */

public class Botones {
    Bitmap img;
    int x,y;

    public Botones(Bitmap i, int _x, int _y){
        img = i;
        x = _x;
        y = _y;
    }

    public boolean estaEnArea(float xP, float yP){
        int x2 = x+img.getWidth();
        int y2 = y+img.getHeight();

        if(xP >= x && xP <= x2){
            if(yP >= y && yP <= y2){
                return true;
            }
        }
        return false;
    }
}