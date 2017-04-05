package itcelaya.edu.mx.appagenda.Utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import itcelaya.edu.mx.appagenda.Adapter.ContactosCardAdapter;
import itcelaya.edu.mx.appagenda.R;

/**
 * Created by emmanuel-nova on 04/04/17.
 */

public class SmsDialog extends DialogFragment {



    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialoglayout = inflater.inflate(R.layout.dialog_sms, null);
        final EditText editSMS = (EditText) dialoglayout.findViewById(R.id.editSMS);
        builder.setView(dialoglayout).setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        SmsManager objSMS = SmsManager.getDefault();
                        objSMS.sendTextMessage(ContactosCardAdapter.phone_number,null,editSMS.getText().toString(),null,null);
                    }
                }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SmsDialog.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }

}
