package itcelaya.edu.mx.appagenda;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import itcelaya.edu.mx.appagenda.Adapter.EventoAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.Model.ViewContact;
import itcelaya.edu.mx.appagenda.Utils.Constant;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class Dashboard extends AppCompatActivity {

    @InjectView(R.id.listEventos)
    ListView listEventos;

    private ProgressDialog progress;
    private SQLiteDatabase objSQL;
    private BDAgenda objBD;
    private List<Evento> item_evento;
    private EventoAdapter eventoAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.inject(this);
        objBD = new BDAgenda(this,"Agenda",null,1);
        objSQL = objBD.getWritableDatabase();
        registerForContextMenu(listEventos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        progress = new ProgressDialog(this);
        progress.setMessage("Loading Events");
        progress.show();
        loadEvents();
        progress.hide();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listEventos.setAdapter(null);
        loadEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress.dismiss();
        progress = null;
    }

    private void loadEvents(){
        item_evento = new ArrayList<>();
        Cursor c = objSQL.rawQuery("SELECT * FROM evento",null);
        while(c.moveToNext())
            item_evento.add(new Evento(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        eventoAdapter = new EventoAdapter(this,item_evento);
        listEventos.setAdapter(eventoAdapter);
    }

    @Override
    public  boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_dashboard,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.itemAcercaDe:
                Intent intAcerca = new Intent(this,AcercaDe.class);
                startActivity(intAcerca);
                break;
            case R.id.itemAgregar:
                Intent intAgregar = new Intent(this,CrearEvento.class);
                startActivity(intAgregar);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listEventos) {
            menu.setHeaderTitle("Opciones");
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_evento, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Adapter adapter = listEventos.getAdapter();
        Evento event = (Evento) adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.mnuEditar:

                break;
            case R.id.mnuBorrar:
                String idEvento = Integer.toString(event.getIdEvento());
                String [] args = new String[] {idEvento};
                objSQL.execSQL("DELETE FROM contactos WHERE idEvento = ?",args);
                objSQL.execSQL("DELETE FROM evento WHERE idEvento = ?",args);
                Toast.makeText(this,"Evento eliminado",Toast.LENGTH_LONG).show();
                listEventos.setAdapter(null);
                loadEvents();
                break;
        }
        return true;
    }



}
