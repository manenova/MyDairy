package itcelaya.edu.mx.appagenda.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Toast;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import itcelaya.edu.mx.appagenda.Adapter.ContactosAdapter;
import itcelaya.edu.mx.appagenda.Adapter.ContactosCardAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.EditarEvento;
import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 04/04/17.
 */

public class TarjetaContactos  extends AppCompatActivity {

    private RecyclerView reciclador;
    private RecyclerView.Adapter adaptador;
    private RecyclerView.LayoutManager adminLayout;
    private String idEvento="";
    public List<Contactos> ListaContactos;
    private SQLiteDatabase objSQL;
    private BDAgenda objBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_contactos);
        Bundle datos= getIntent().getExtras();
        idEvento = datos.getString("idEvento");
        objBD = new BDAgenda(this,"Agenda",null,1);
        objSQL = objBD.getWritableDatabase();
        reciclador = (RecyclerView) findViewById(R.id.reciclador);
        reciclador.setHasFixedSize(true);
        getContactosDatos();
        adminLayout = new LinearLayoutManager(this);
        reciclador.setLayoutManager(adminLayout);
        registerForContextMenu(reciclador);
    }



    private void getContactosDatos(){
        ListaContactos = new ArrayList<>();
        String [] args = new String[] {idEvento};
        Cursor c_contactos = objSQL.rawQuery("SELECT * FROM contactos WHERE idEvento = ? ",args);
        while(c_contactos.moveToNext())
            ListaContactos.add(new Contactos(c_contactos.getInt(0),c_contactos.getInt(1),c_contactos.getString(2),c_contactos.getString(3),c_contactos.getString(4)));
       adaptador = new ContactosCardAdapter(ListaContactos,this);
        reciclador.setAdapter(adaptador);
    }



}
