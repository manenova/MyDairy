package itcelaya.edu.mx.appagenda.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.R;
import itcelaya.edu.mx.appagenda.Utils.HoraEvento;
import itcelaya.edu.mx.appagenda.Utils.SmsDialog;

/**
 * Created by emmanuel-nova on 04/04/17.
 */

public class ContactosCardAdapter extends RecyclerView.Adapter<ContactosCardAdapter.ContactosViewHolder> {

    private List<Contactos> listaContactos;
    Context contexto;
    public static String phone_number="";

    public static class ContactosViewHolder extends RecyclerView.ViewHolder {

        public ImageView imv_logo;
        public TextView txt_contact_name;
        public TextView txt_contact_phone;
        public TextView txt_contact_email;

        public ContactosViewHolder(View itemView) {
            super(itemView);
            imv_logo = (ImageView) itemView.findViewById(R.id.imv_logo);
            txt_contact_name = (TextView) itemView.findViewById(R.id.txt_contact_name);
            txt_contact_phone = (TextView) itemView.findViewById(R.id.txt_contact_phone);
            txt_contact_email = (TextView) itemView.findViewById(R.id.txt_contact_email);
        }
    }

    public ContactosCardAdapter(List<Contactos> listaContactos, Context contexto) {
        this.listaContactos = listaContactos;
        this.contexto = contexto;
    }

    @Override
    public ContactosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_contactos,parent,false);
        return new ContactosViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ContactosViewHolder holder, int position) {
        try {
            final Contactos lista = listaContactos.get(position);
            String imagen ="http://www.facsa.ulg.ac.be/upload/docs/image/jpeg/2016-12/user.jpg";
            //Bitmap bitmap = BitmapFactory.decodeResource(contexto.getResources(),R.mipmap.logo);
            Bitmap bitmap = Ion.with(contexto).load(imagen).withBitmap().asBitmap().get();
            holder.imv_logo.setImageBitmap(bitmap);
            holder.txt_contact_name.setText(listaContactos.get(position).getNombContact());
            holder.txt_contact_phone.setText(listaContactos.get(position).getTelContact());
            holder.txt_contact_email.setText(listaContactos.get(position).getEmailContact());
            Linkify.addLinks(holder.txt_contact_email,Linkify.EMAIL_ADDRESSES);
            Linkify.addLinks(holder.txt_contact_phone,Linkify.PHONE_NUMBERS);
            holder.imv_logo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    PopupMenu popup = new PopupMenu(contexto,holder.imv_logo);
                    popup.inflate(R.menu.menu_linkify);
                    popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.mnuSms:
                                    phone_number=lista.getTelContact();
                                    FragmentManager manager = ((Activity) contexto).getFragmentManager();
                                    DialogFragment Sms = new SmsDialog();
                                    Sms.show(manager,"ENVIAR SMS");
                                    break;
                                case R.id.mnuCall:
                                    try {
                                        String URI = "tel:"+lista.getTelContact();
                                        Intent objCALL = new Intent(Intent.ACTION_CALL,Uri.parse(URI));
                                        contexto.startActivity(objCALL);
                                    }catch (SecurityException p){
                                        p.printStackTrace();
                                    }
                                    break;
                            }
                            return false;
                        }
                    });
                    //displaying the popup
                    popup.show();
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return listaContactos.size();
    }




}
