package itcelaya.edu.mx.appagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import itcelaya.edu.mx.appagenda.Model.Emails;
import itcelaya.edu.mx.appagenda.Model.Phones;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class PhonesAdapter extends BaseAdapter {

    private Context context;
    private List<Phones> phones;

    public PhonesAdapter(Context context, List<Phones> phones) {
        this.context = context;
        this.phones = phones;
    }

    @Override
    public int getCount() {
        return this.phones.size();
    }

    @Override
    public Object getItem(int position) {
        return this.phones.get(position);
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
            rowView = inflater.inflate(R.layout.list_view_phones, null);
        }
        TextView txtPhonesContact   = (TextView) rowView.findViewById(R.id.txtPhonesContact);
        final Phones item = this.phones.get(position);
        txtPhonesContact.setText(item.getTelefono());
        rowView.setTag(item.getTelefono());
        return rowView;
    }
}
