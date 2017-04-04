package itcelaya.edu.mx.appagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import itcelaya.edu.mx.appagenda.Model.Contact;
import itcelaya.edu.mx.appagenda.Model.Emails;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class ContactAdapter extends BaseAdapter {

    private Context context;
    private List<Contact> contact ;

    public ContactAdapter(Context context, List<Contact> contact) {
        this.context = context;
        this.contact = contact;
    }

    @Override
    public int getCount() {
        return this.contact.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contact.get(position);
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
            rowView = inflater.inflate(R.layout.list_view_contacto_evento, null);
        }
        TextView txtNombreContacto   = (TextView) rowView.findViewById(R.id.txtNombreContacto);
        TextView txtEmailContacto   = (TextView) rowView.findViewById(R.id.txtEmailContacto);
        TextView txtTelefonoContacto   = (TextView) rowView.findViewById(R.id.txtTelefonoContacto);
        final Contact item = this.contact.get(position);
        txtNombreContacto.setText(item.getNomContacto());
        txtEmailContacto.setText(item.getEmailContacto());
        txtTelefonoContacto.setText(item.getTeleContacto());
        rowView.setTag(item.getNomContacto());
        return rowView;
    }
}
