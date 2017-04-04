package itcelaya.edu.mx.appagenda.BD;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class BDAgenda extends SQLiteOpenHelper {

    String table_evento = "CREATE TABLE evento (" +
            "idEvento INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nomEvento VARCHAR(50)," +
            "desEvento VARCHAR(100)," +
            "fechaEvento VARCHAR(16))";

    String table_contacto = "CREATE TABLE contactos (" +
            "idContact INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "idEvento INTEGER, " +
            "nombContact VARCHAR(50), " +
            "telContact VARCHAR(10)," +
            "emailContact VARCHAR(50)," +
            "CONSTRAINT fk1_evento FOREIGN KEY (idEvento) REFERENCES evento (idEvento) )";

    public BDAgenda(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(table_evento);
        db.execSQL(table_contacto);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
