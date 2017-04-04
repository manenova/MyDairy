package itcelaya.edu.mx.appagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import itcelaya.edu.mx.appagenda.Model.Emails;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class EventoAdapter extends BaseAdapter {

    private Context context;
    private List<Evento> evento;

    public EventoAdapter(Context context, List<Evento> evento) {
        this.context = context;
        this.evento = evento;
    }

    @Override
    public int getCount() {
        return this.evento.size();
    }

    @Override
    public Object getItem(int position) {
        return this.evento.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_view_evento, null);
        }

        TextView txtNombreEvento   = (TextView) rowView.findViewById(R.id.txtNombreEvento);
        TextView txtDescripcionEvento   = (TextView) rowView.findViewById(R.id.txtDescripcionEvento);
        TextView txtFechaHoraEvento   = (TextView) rowView.findViewById(R.id.txtFechaHoraEvento);
        final Evento item = this.evento.get(position);
        txtNombreEvento.setText(item.getNomEvento());
        txtDescripcionEvento.setText(item.getDesEvento());
        txtFechaHoraEvento.setText(item.getFechaEvento());
        rowView.setTag(item.getIdEvento());
        return rowView;
    }
}
