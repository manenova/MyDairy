package itcelaya.edu.mx.appagenda;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import itcelaya.edu.mx.appagenda.Adapter.EmailsAdapter;
import itcelaya.edu.mx.appagenda.Adapter.PhonesAdapter;
import itcelaya.edu.mx.appagenda.Model.Contact;
import itcelaya.edu.mx.appagenda.Model.Emails;
import itcelaya.edu.mx.appagenda.Model.Phones;
import itcelaya.edu.mx.appagenda.Utils.Constant;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class ViewEmails extends Activity {

    @InjectView(R.id.listEmails)
    ListView listEmails;
    @InjectView(R.id.listPhones)
    ListView listPhones;

    List<Emails> item_emails = new ArrayList<>();
    EmailsAdapter emailsAdapter;
    List<Phones> item_phones = new ArrayList<>();
    PhonesAdapter phonesAdapter;
    private String option_item="";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_datos);
        ButterKnife.inject(this);
        Bundle datos= getIntent().getExtras();
        ArrayList<String> emailContact= datos.getStringArrayList("emailContact");
        ArrayList<String> teleContact = datos.getStringArrayList("teleContact");
        for (int i=0;i<emailContact.size();i++)
            item_emails.add(new Emails(emailContact.get(i)));
        for (int j=0;j<teleContact.size(); j++)
            item_phones.add(new Phones(teleContact.get(j)));
        emailsAdapter = new EmailsAdapter(this,item_emails);
        listEmails.setAdapter(emailsAdapter);
        phonesAdapter = new PhonesAdapter(this,item_phones);
        listPhones.setAdapter(phonesAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerForContextMenu(listEmails);
        registerForContextMenu(listPhones);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listEmails ) {
            option_item="Emails";
            menu.setHeaderTitle("Opciones");
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_actioncontact, menu);
        }
        if(v.getId() == R.id.listPhones){
            option_item="Phones";
            menu.setHeaderTitle("Opciones");
            MenuInflater inflater=getMenuInflater();
            inflater.inflate(R.menu.menu_actioncontact, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        if(option_item.equals("Emails")){
            Adapter adapter = listEmails.getAdapter();
            Emails emails = (Emails) adapter.getItem(info.position);
            switch (item.getItemId()) {
                case R.id.mnuSelect:
                    Constant.emailContacto = emails.getEmail();
                    break;
            }
        }
        if(option_item.equals("Phones")){
            Adapter adapter = listPhones.getAdapter();
            Phones phones = (Phones) adapter.getItem(info.position);
            switch (item.getItemId()) {
                case R.id.mnuSelect:
                    Constant.phoneContacto= phones.getTelefono();
                    break;
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeContextMenu();
        closeOptionsMenu();
        finish();
    }

    @OnClick(R.id.btnAddContacto)
    public void newContacto(){
        CrearEvento.contactos_evento.add(new Contact(Constant.nomContacto,Constant.phoneContacto,Constant.emailContacto));
        Constant.nomContacto="";
        Constant.phoneContacto="";
        Constant.emailContacto="";
        finish();
    }

}
