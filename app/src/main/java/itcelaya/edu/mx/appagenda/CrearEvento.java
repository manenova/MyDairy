package itcelaya.edu.mx.appagenda;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Contacts;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import itcelaya.edu.mx.appagenda.Adapter.ContactAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.Model.Contact;
import itcelaya.edu.mx.appagenda.Utils.Constant;
import itcelaya.edu.mx.appagenda.Utils.ContactUtil;
import itcelaya.edu.mx.appagenda.Utils.FechaEvento;
import itcelaya.edu.mx.appagenda.Utils.HoraEvento;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class CrearEvento extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    @InjectView(R.id.imbFecha)
    ImageButton imbFecha;
    @InjectView(R.id.edtFechaEvento)
    EditText edtFechaEvento;
    @InjectView(R.id.edtHoraEvento)
    EditText edtHoraEvento;
    @InjectView(R.id.listContact)
    ListView listContact;
    @InjectView(R.id.edtNomEvento)
    EditText edtNomEvento;
    @InjectView(R.id.edtDesEvento)
    EditText edtDesEvento;
    ContactAdapter contactAdapter;

    private int anio, mes, dia;
    public static List<Contact> contactos_evento = new ArrayList<>();
    SQLiteDatabase objSQL;
    BDAgenda objBD;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        ButterKnife.inject(this);
        objBD = new BDAgenda(this,"Agenda",null,1);
        objSQL = objBD.getWritableDatabase();
        registerForContextMenu(listContact);
    }

    @OnClick(R.id.imbFecha)
    public void getFechaEvento(){
        edtFechaEvento.setText("");
        DialogFragment fechaFragment = new FechaEvento();
        fechaFragment.show(getFragmentManager(),"Fecha Evento");
    }

    @OnClick(R.id.imbHora)
    public void getHoraEvento(){
        edtHoraEvento.setText("");
        DialogFragment horaFragment = new HoraEvento();
        horaFragment.show(getFragmentManager(),"Hora Evento");
    }

    @OnClick(R.id.btnAddContact)
    public void initPickContacts(){
        Intent intent = new Intent(this,ViewContacts.class);
        Bundle array = new Bundle();
        array.putString("optionActivity","CrearEvento");
        intent.putExtras(array);
        startActivity(intent);
    }

    @OnClick(R.id.btnSaveEvento)
    public void saveEvent(){
        String nomEvento = edtNomEvento.getText().toString();
        String desEvento = edtDesEvento.getText().toString();
        String fechaEvento = edtFechaEvento.getText().toString() + " "+edtHoraEvento.getText().toString();
        String idEvento="";
        if(!nomEvento.equals("") && !desEvento.equals("") && !fechaEvento.equals("")){
            try{
                String SQL = "INSERT INTO evento (nomEvento,desEvento,fechaEvento) VALUES ('"+nomEvento+"','"+desEvento+"','"+fechaEvento+"')";
                objSQL.execSQL(SQL);
                String [] args = new String[] {nomEvento,desEvento};
                Cursor c = objSQL.rawQuery("SELECT idEvento FROM evento WHERE nomEvento = ? AND desEvento = ?",args);
                while(c.moveToNext()) {
                    idEvento = c.getString(0);
                }
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=ON");
                Iterator<Contact> itr = contactos_evento.iterator();
                while(itr.hasNext()){
                    Contact temp = itr.next();
                    String SQL_CONTACT = "INSERT INTO contactos (idEvento, nombContact, telContact, emailContact) VALUES ('"+idEvento+"','"+temp.getNomContacto()+"','"+temp.getTeleContacto()+"','"+temp.getEmailContacto()+"')";
                    objSQL.execSQL(SQL_CONTACT);
                }
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=OFF");
            }catch(SQLiteException e){
                e.printStackTrace();
            }
            Toast.makeText(this,"Evento Agregado Correctamente",Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"Campos Vacios",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listContact.setAdapter(null);
        contactAdapter = new ContactAdapter(this, contactos_evento);
        listContact.setAdapter(contactAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i=0;i<contactos_evento.size();i++)
            contactos_evento.remove(i);
        finish();
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        anio = year;
        mes = monthOfYear+1;
        dia = dayOfMonth;
        edtFechaEvento.append(String.valueOf(anio));
        edtFechaEvento.append("-");
        String strMes = (mes < 10) ? "0"+String.valueOf(mes) : String.valueOf(mes);
        edtFechaEvento.append(strMes);
        edtFechaEvento.append("-");
        String strDia = (dia < 10) ? "0"+String.valueOf(dia) : String.valueOf(dia);
        edtFechaEvento.append(strDia);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        String strMinute = (minute < 10) ? "0"+String.valueOf(minute) : String.valueOf(minute);
        edtHoraEvento.setText(String.valueOf(hourOfDay)+":"+strMinute);

    }
}
