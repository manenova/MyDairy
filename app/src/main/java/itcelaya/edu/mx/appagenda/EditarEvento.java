package itcelaya.edu.mx.appagenda;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
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
import itcelaya.edu.mx.appagenda.Adapter.ContactosAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.Model.Contact;
import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.Evento;
import itcelaya.edu.mx.appagenda.Utils.FechaEvento;
import itcelaya.edu.mx.appagenda.Utils.HoraEvento;

import static itcelaya.edu.mx.appagenda.CrearEvento.contactos_evento;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class EditarEvento extends Activity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

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

    SQLiteDatabase objSQL;
    BDAgenda objBD;
    private String idEvento;
    private Evento objE;
    public List<Contactos> contac_evento = new ArrayList<>();
    private ContactosAdapter contactosAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_evento);
        ButterKnife.inject(this);
        objBD = new BDAgenda(this,"Agenda",null,1);
        objSQL = objBD.getWritableDatabase();
        Bundle datos= getIntent().getExtras();
        idEvento = datos.getString("idEvento");
        registerForContextMenu(listContact);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getEventoDatos();
        getContactosDatos();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        listContact.setAdapter(null);
        reloadContactos();
        contactosAdapter = new ContactosAdapter(this, contac_evento);
        listContact.setAdapter(contactosAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        for(int i=0;i<contactos_evento.size();i++)
            contactos_evento.remove(i);
        finish();
    }

    private void reloadContactos(){
        Iterator<Contact> itr = contactos_evento.iterator();
        while(itr.hasNext()){
            Contact temp = itr.next();
            contac_evento.add(new Contactos(0,Integer.parseInt(idEvento),temp.getNomContacto(),temp.getTeleContacto(),temp.getEmailContacto()));
        }
        for(int i=0;i<contactos_evento.size();i++)
            contactos_evento.remove(i);
    }

    private void getEventoDatos(){
        String [] args = new String[] {idEvento};
        Cursor c_evento = objSQL.rawQuery("SELECT * FROM evento WHERE idEvento = ?",args);
        while (c_evento.moveToNext())
            objE = new Evento(c_evento.getInt(0),c_evento.getString(1),c_evento.getString(2),c_evento.getString(3));
        edtNomEvento.setText(objE.getNomEvento());
        edtDesEvento.setText(objE.getDesEvento());
        String delimiter = " ";
        String [] temp = objE.getFechaEvento().split(delimiter, 2);
        edtFechaEvento.setText(temp[0]);
        edtHoraEvento.setText(temp[1]);

    }

    private void getContactosDatos(){
        for (int i=0;i<contac_evento.size();i++)
            contac_evento.remove(i);
        String [] args = new String[] {idEvento};
        Cursor c_contactos = objSQL.rawQuery("SELECT * FROM contactos WHERE idEvento = ?",args);
        while(c_contactos.moveToNext())
            contac_evento.add(new Contactos(c_contactos.getInt(0),c_contactos.getInt(1),c_contactos.getString(2),c_contactos.getString(3),c_contactos.getString(4)));
        contactosAdapter = new ContactosAdapter(this,contac_evento);
        listContact.setAdapter(contactosAdapter);
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
        Intent intAcerca = new Intent(this,ViewContacts.class);
        startActivity(intAcerca);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
        int anio, mes, dia;
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

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listContact) {
            menu.setHeaderTitle("Opciones");
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_deletecontact, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Adapter adapter = listContact.getAdapter();
        Contactos contact = (Contactos) adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.mnuDeleteContact:
                System.out.println("Valor position :"+info.position);
                    deleteContacto(info.position,Integer.toString(contact.getIdContact()),contact.getNombContact());
                break;
        }
        return true;
    }

    private void deleteContacto(int positionContact,String idContact,String nomContact){
        if(idContact.equals("0")){
            for (int i=0;i<contac_evento.size();i++) {
                Contactos temp = contac_evento.get(i);
                if (Integer.toString(temp.getIdContact()).equals(idContact) && temp.getNombContact().equals(nomContact) && positionContact == i)
                    contac_evento.remove(i);
            }
        }else{
            String [] args = new String[] {idEvento,idContact};
            objSQL.execSQL("DELETE FROM contactos WHERE idEvento = ? AND idContact = ?",args);
        }
        listContact.setAdapter(null);
        getContactosDatos();
    }

    @OnClick(R.id.btnSaveEvento)
    public void updateEvent(){
        String nomEvento = edtNomEvento.getText().toString();
        String desEvento = edtDesEvento.getText().toString();
        String fechaEvento = edtFechaEvento.getText().toString() + " "+edtHoraEvento.getText().toString();
        if(!nomEvento.equals("") && !desEvento.equals("") && !fechaEvento.equals("")){
            try{
                ContentValues valores = new ContentValues();
                valores.put("nomEvento",nomEvento);
                valores.put("desEvento",desEvento);
                valores.put("fechaEvento",fechaEvento);
                String [] args = new String[] {idEvento};
                objSQL.update("evento",valores,"idEvento = ?",args); //Actualizar Evento
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=ON");

                Iterator<Contactos> itr = contac_evento.iterator();
                while(itr.hasNext()){
                    Contactos temp = itr.next();
                    if(Integer.toString(temp.getIdContact()).equals("0")){
                        String SQL_CONTACT = "INSERT INTO contactos (idEvento, nombContact, telContact, emailContact) VALUES ('"+idEvento+"','"+temp.getNombContact()+"','"+temp.getTelContact()+"','"+temp.getEmailContact()+"')";
                        objSQL.execSQL(SQL_CONTACT);
                    }else{
                        ContentValues valores_contacto = new ContentValues();
                        valores.put("nomContact",temp.getNombContact());
                        valores.put("telContact",temp.getTelContact());
                        valores.put("emailContact",temp.getEmailContact());
                        String [] args_contacto = new String[] {idEvento,Integer.toString(temp.getIdContact())};
                        objSQL.update("contactos",valores_contacto,"idEvento = ? AND idContact = ?",args_contacto); //Actualizar Contacto
                    }
                }
                objSQL.execSQL("PRAGMA FOREIGN_KEYS=OFF");
            }catch(SQLiteException e){
                e.printStackTrace();
            }
            Toast.makeText(this,"Evento Actualizado Correctamente",Toast.LENGTH_LONG).show();
        }else
            Toast.makeText(this,"Campos Vacios",Toast.LENGTH_LONG).show();
    }
}
