package itcelaya.edu.mx.appagenda.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.concurrent.ExecutionException;

import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.ViewContact;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class ViewContactsAdapter extends BaseAdapter {

    private Context context;
    private List<ViewContact> contacts;

    public ViewContactsAdapter(Context context, List<ViewContact> contacts) {
        this.context = context;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return this.contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return this.contacts.get(position);
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
            rowView = inflater.inflate(R.layout.list_view_contacts, null);
        }
        TextView txtNomContact   = (TextView) rowView.findViewById(R.id.txtNomContact);
        final ViewContact item = this.contacts.get(position);
        txtNomContact.setText(item.getNomContact());
        rowView.setTag(item.getIdContact());
        return rowView;
    }
}
