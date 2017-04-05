package itcelaya.edu.mx.appagenda.CardView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import itcelaya.edu.mx.appagenda.AcercaDe;
import itcelaya.edu.mx.appagenda.Adapter.ContactosCardAdapter;
import itcelaya.edu.mx.appagenda.Adapter.EventoCardAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.CrearEvento;
import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 04/04/17.
 */

public class TarjetaEvento extends AppCompatActivity {

    private RecyclerView recicladorEvento;
    private RecyclerView.LayoutManager adminLayout;
    private String idEvento="";
    private EventoCardAdapter adaptador;
    public List<Evento> ListaEvento;
    private SQLiteDatabase objSQL;
    private BDAgenda objBD;
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarjeta_eventos);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        new HackingBackgroundTask().execute();
                    }
                }
        );
        objBD = new BDAgenda(this,"Agenda",null,1);
        objSQL = objBD.getWritableDatabase();
        recicladorEvento = (RecyclerView) findViewById(R.id.recicladorEvento);
        recicladorEvento.setHasFixedSize(true);
        getEventosDatos();
        adminLayout = new LinearLayoutManager(this);
        recicladorEvento.setLayoutManager(adminLayout);

    }

    private void getEventosDatos(){
        ListaEvento = new ArrayList<>();
        Cursor c = objSQL.rawQuery("SELECT * FROM evento",null);
        while(c.moveToNext())
            ListaEvento.add(new Evento(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
        adaptador = new EventoCardAdapter(this,ListaEvento);
        recicladorEvento.setAdapter(adaptador);
    }

    private class HackingBackgroundTask extends AsyncTask<Void, Void, List<Evento>> {
        static final int DURACION = 3 * 1000; // 3 segundos de carga
        @Override
        protected List doInBackground(Void... params) {
            // Simulación de la carga de items
            try {
                Thread.sleep(DURACION);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ListaEvento = new ArrayList<>();
            Cursor c = objSQL.rawQuery("SELECT * FROM evento",null);
            while(c.moveToNext())
                ListaEvento.add(new Evento(c.getInt(0),c.getString(1),c.getString(2),c.getString(3)));
            // Retornar en nuevos elementos para el adaptador
            return ListaEvento;
        }

        @Override
        protected void onPostExecute(List result) {
            super.onPostExecute(result);
            // Limpiar elementos antiguos
            adaptador.c();
            // Añadir elementos nuevos
           adaptador.a(result);
            // Parar la animación del indicador
            refreshLayout.setRefreshing(false);
        }

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

}
