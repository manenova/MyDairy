package itcelaya.edu.mx.appagenda.Utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;

import java.util.Calendar;

import itcelaya.edu.mx.appagenda.CrearEvento;

/**
 * Created by emmanuel-nova on 03/04/17.
 */

public class HoraEvento extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        return new TimePickerDialog(getActivity(), (CrearEvento) getActivity(), hour, minute, DateFormat.is24HourFormat(getActivity()));
    }

}
