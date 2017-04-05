package itcelaya.edu.mx.appagenda.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import itcelaya.edu.mx.appagenda.Adapter.ContactosCardAdapter;
import itcelaya.edu.mx.appagenda.BD.BDAgenda;
import itcelaya.edu.mx.appagenda.CrearEvento;
import itcelaya.edu.mx.appagenda.EditarEvento;
import itcelaya.edu.mx.appagenda.Model.Contact;
import itcelaya.edu.mx.appagenda.R;
import itcelaya.edu.mx.appagenda.ViewContacts;

/**
 * Created by emmanuel-nova on 05/04/17.
 */

public class ContactoDialog extends DialogFragment {

    private SQLiteDatabase objSQL;
    private BDAgenda objBD;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View dialoglayout = inflater.inflate(R.layout.dialog_datos_contact, null);
        final EditText editPhone = (EditText) dialoglayout.findViewById(R.id.editTelefono);
        final EditText editEmail = (EditText) dialoglayout.findViewById(R.id.editEmail);
        builder.setView(dialoglayout).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String phone = editPhone.getText().toString();
                String email = editEmail.getText().toString();
                if(ViewContacts.option_activityContact.equals("CrearEvento"))
                    CrearEvento.contactos_evento.add(new Contact(Constant.nomContacto,phone,email));
                if(ViewContacts.option_activityContact.equals("EditarEvento")) {
                    objBD = new BDAgenda(dialoglayout.getContext(),"Agenda",null,1);
                    objSQL = objBD.getWritableDatabase();
                    String SQL_CONTACT = "INSERT INTO contactos (idEvento, nombContact, telContact, emailContact) VALUES ('"+ EditarEvento.idEvento+"','"+Constant.nomContacto+"','"+phone+"','"+email+"')";
                    objSQL.execSQL(SQL_CONTACT);
                    System.out.println("Contacto Agregado a la Base ");
                }
                Constant.nomContacto="";
                Constant.phoneContacto="";
                Constant.emailContacto="";
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                ContactoDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

}
