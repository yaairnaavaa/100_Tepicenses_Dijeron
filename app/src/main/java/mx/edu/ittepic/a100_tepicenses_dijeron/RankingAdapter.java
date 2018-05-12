package mx.edu.ittepic.a100_tepicenses_dijeron;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RankingAdapter extends BaseAdapter{

    private Context context;
    String[] datos;

    public RankingAdapter(Context context, String[] datos) {
        this.context = context;
        this.datos = datos;
    }

    @Override
    public int getCount() {
        return datos.length;
    }

    @Override
    public Object getItem(int position) {
        return datos[position];
    }

    @Override
    public long getItemId(int position) {
        return Long.parseLong(position+"");
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {

        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.plantillaranking, viewGroup, false);
        }

        ImageView copa = view.findViewById(R.id.copa);
        TextView nombre = view.findViewById(R.id.nombre);
        TextView puntos = view.findViewById(R.id.puntos);

        Object item = getItem(position);
        if (position==0) {
            copa.setImageResource(R.drawable.oro);
        }
        if (position==1) {
            copa.setImageResource(R.drawable.plata);
        }
        if (position==2) {
            copa.setImageResource(R.drawable.bronce);
        }
        nombre.setText(item.toString().split(",")[0]);
        puntos.setText(item.toString().split(",")[1]);

        return view;
    }
}
