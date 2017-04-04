package itcelaya.edu.mx.appagenda;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import itcelaya.edu.mx.appagenda.Adapter.ViewContactsAdapter;
import itcelaya.edu.mx.appagenda.Model.Contactos;
import itcelaya.edu.mx.appagenda.Model.ViewContact;
import itcelaya.edu.mx.appagenda.Utils.Constant;
import itcelaya.edu.mx.appagenda.Utils.ContactUtil;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class ViewContacts extends Activity {

    @InjectView(R.id.listContacts)
    ListView listContacts;

    List<ViewContact> item_contacts = new ArrayList<>();
    ArrayList <ContactUtil> list_contact_temp = new ArrayList<>();
    private ViewContactsAdapter contactsAdapter;
    private ProgressDialog progress;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_contacts);
        ButterKnife.inject(this);
        progress = new ProgressDialog(this);
        progress.setMessage("Loading Contacts");
        loadContacts();
        ListContact();
        registerForContextMenu(listContacts);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        progress.dismiss();
        progress = null;
    }

    private void loadContacts() {
        progress.show();
        int contContact=1;
        Cursor c_phone = getPhone();
        for(c_phone.moveToFirst(); !c_phone.isAfterLast(); c_phone.moveToNext()){
            String contactActual = c_phone.getString(0);
            String phoneActual = c_phone.getString(3);
            ArrayList<String> teleContact = new ArrayList<>();
            ArrayList<String> emailContact = new ArrayList<>();
            Cursor c = getPhone();
            Cursor e = getEmail();
            while(c.moveToNext()) {
                if (c.getString(0).equals(contactActual)) {
                    String phone = c.getString(3);
                    phone = phone.replace(" ","");
                    if (!existPhone(teleContact, phone))
                        teleContact.add(phone);
                }
            }
            while(e.moveToNext())
                if (e.getString(1).equals(contactActual))
                    if(!existPhone(emailContact,e.getString(2)))
                        emailContact.add(e.getString(2));
            if(!existContact(contactActual)) {
                    ContactUtil contactUtil = new ContactUtil();
                    contactUtil.setIdContact(contContact);
                    contactUtil.setNomContact(contactActual);
                    phoneActual=phoneActual.replace(" ","");
                    if (!existPhone(teleContact, phoneActual))
                        teleContact.add(phoneActual);
                    contactUtil.setTeleContact(teleContact);
                    contactUtil.setEmailContact(emailContact);
                    list_contact_temp.add(contactUtil);
                    contContact++;
            }
        }

        System.out.println("Tamaño lista :"+ list_contact_temp.size());
    }

    private boolean existContact(String nomContact){
        Iterator<ContactUtil> itr = list_contact_temp.iterator();
        while(itr.hasNext()){
            ContactUtil contactUtil = itr.next();
            String temp = contactUtil.getNomContact();
            if(temp.equals(nomContact))
                return true;
        }
       return false;
    }

    private boolean existPhone(ArrayList<String> teleContact ,String numberContact){
        for (int i=0;i<teleContact.size();i++)
             if(numberContact.equals(teleContact.get(i)))
                 return true;
        return false;
    }

    private boolean existEmail(ArrayList<String> emailContact,String mailContact){
        for(int i=0;i<emailContact.size();i++)
            if (mailContact.equals(emailContact.get(i)))
                return true;
        return false;
    }

    private Cursor getPhone(){
        String[] projeccion = new String[]
                {
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.Data._ID, ContactsContract.Data.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE
                };
        String selectionClause = ContactsContract.Data.MIMETYPE + "='" + ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE + "' AND " + ContactsContract.CommonDataKinds.Phone.NUMBER + " IS NOT NULL";
        String sortOrder = ContactsContract.Data.DISPLAY_NAME + " ASC";
        Cursor c = getContentResolver().query(ContactsContract.Data.CONTENT_URI, projeccion, selectionClause, null, sortOrder);
        return c;
    }

    private Cursor getEmail(){
        String[] projeccion = new String[]
                {
                        ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                        ContactsContract.Contacts.DISPLAY_NAME,
                        ContactsContract.CommonDataKinds.Email.DATA
                };
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, projeccion, null, null, null);
        return cursor;
    }

    private void ListContact(){
        Iterator<ContactUtil> itr = list_contact_temp.iterator();
        while(itr.hasNext()){
            ContactUtil contactUtil = itr.next();
            item_contacts.add(new ViewContact(contactUtil.getIdContact(),contactUtil.getNomContact(),contactUtil.getTeleContact(),contactUtil.getEmailContact()));
        }
        contactsAdapter = new ViewContactsAdapter(this,item_contacts);
        listContacts.setAdapter(contactsAdapter);
        progress.hide();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listContacts) {
            menu.setHeaderTitle("Opciones");
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_addcontact, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Adapter adapter = listContacts.getAdapter();
        ViewContact contact = (ViewContact) adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.mnuDatos:
                Constant.nomContacto = contact.getNomContact();
                ArrayList<String> emailContact = contact.getEmailContact();
                ArrayList<String> teleContact = contact.getTeleContact();
                Intent intent = new Intent(this,ViewEmails.class);
                Bundle array = new Bundle();
                array.putStringArrayList("emailContact",emailContact);
                array.putStringArrayList("teleContact",teleContact);
                intent.putExtras(array);
                startActivity(intent);
                break;
            case R.id.mnuExt:

                break;
        }
        return true;
    }

}

