package itcelaya.edu.mx.appagenda.Adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.util.List;
import java.util.concurrent.ExecutionException;

import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.CardView.TarjetaContactos;
import itcelaya.edu.mx.appagenda.EditarEvento;
import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.R;
import itcelaya.edu.mx.appagenda.Utils.SmsDialog;

/**
 * Created by emmanuel-nova on 04/04/17.
 */

public class EventoCardAdapter extends RecyclerView.Adapter<EventoCardAdapter.EventoViewHolder> {

    private Context context;
    private List<Evento> evento;

    public static class EventoViewHolder extends RecyclerView.ViewHolder {

        public ImageView imv_tipoEvento;
        public TextView txt_evento_name;
        public TextView txt_evento_descripcion;
        public TextView txt_evento_fechaHora;



        public EventoViewHolder(View itemView) {
            super(itemView);
            imv_tipoEvento = (ImageView) itemView.findViewById(R.id.imv_tipoEvento);
            txt_evento_name = (TextView) itemView.findViewById(R.id.txt_evento_name);
            txt_evento_descripcion = (TextView) itemView.findViewById(R.id.txt_evento_descripcion);
            txt_evento_fechaHora = (TextView) itemView.findViewById(R.id.txt_evento_fechaHora);
        }
    }

    public EventoCardAdapter(Context context, List<Evento> evento) {
        this.context = context;
        this.evento = evento;
    }

    @Override
    public  EventoCardAdapter.EventoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tarjeta_eventos,parent,false);
        return new EventoCardAdapter.EventoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final EventoViewHolder holder, int position) {
        final Evento lista = evento.get(position);
        final String idEvento = Integer.toString(lista.getIdEvento());
        final BDAgenda objBD  = new BDAgenda(context,"Agenda",null,1);;
        final SQLiteDatabase objSQL =  objBD.getWritableDatabase();
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),R.mipmap.logo);
        holder.imv_tipoEvento.setImageBitmap(bitmap);
        holder.txt_evento_name.setText(evento.get(position).getNomEvento());
        holder.txt_evento_descripcion.setText(evento.get(position).getDesEvento());
        holder.txt_evento_fechaHora.setText(evento.get(position).getFechaEvento());
        holder.imv_tipoEvento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popup = new PopupMenu(context,holder.imv_tipoEvento);
                popup.inflate(R.menu.menu_evento);
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mnuEditar:
                                Intent intent = new Intent(context,EditarEvento.class);
                                Bundle datos = new Bundle();
                                datos.putString("idEvento",idEvento);
                                intent.putExtras(datos);
                                context.startActivity(intent);
                                break;
                            case R.id.mnuBorrar:
                                String [] args = new String[] {idEvento};
                                objSQL.execSQL("DELETE FROM contactos WHERE idEvento = ?",args);
                                objSQL.execSQL("DELETE FROM evento WHERE idEvento = ?",args);
                                Toast.makeText(context,"Evento eliminado",Toast.LENGTH_LONG).show();
                                break;
                            case R.id.mnuContactos:
                                Intent inten = new Intent(context,TarjetaContactos.class);
                                Bundle dato = new Bundle();
                                dato.putString("idEvento",idEvento);
                                inten.putExtras(dato);
                                context.startActivity(inten);
                                break;
                        }
                        return false;
                    }
                });
                //displaying the popup
                popup.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return evento.size();
    }

    /*
   Permite limpiar todos los elementos del recycler
    */
    public void c(){
        evento.clear();
        notifyDataSetChanged();
    }

    /*
    Añade una lista completa de items
  */
    public void a(List<Evento> lista){
        evento.addAll(lista);
        notifyDataSetChanged();
    }
}
