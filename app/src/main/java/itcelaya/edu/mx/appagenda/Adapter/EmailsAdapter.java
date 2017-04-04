package itcelaya.edu.mx.appagenda.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import itcelaya.edu.mx.appagenda.Model.Emails;
import itcelaya.edu.mx.appagenda.Model.ViewContact;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class EmailsAdapter extends BaseAdapter {

    private Context context;
    private List<Emails> emails;

    public EmailsAdapter(Context context, List<Emails> emails) {
        this.context = context;
        this.emails = emails;
    }

    @Override
    public int getCount() {
        return this.emails.size();
    }

    @Override
    public Object getItem(int position) {
        return this.emails.get(position);
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
            rowView = inflater.inflate(R.layout.list_view_emails, null);
        }
        TextView txtEmailContact   = (TextView) rowView.findViewById(R.id.txtEmailContact);
        final Emails item = this.emails.get(position);
        txtEmailContact.setText(item.getEmail());
        rowView.setTag(item.getEmail());
        return rowView;
    }
}
