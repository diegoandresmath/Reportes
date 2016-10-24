package ec.sandoval.srv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import ec.sandoval.ent.Ubicacion;

/**
 * Created by ddelacruz on 21/10/2016.
 */

public class DataBase extends SQLiteOpenHelper {
    public DataBase(Context context) {
        super(context, Constants.DATABASE_NAME, null, Constants.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder ubicacion = new StringBuilder();
        ubicacion.append("CREATE TABLE ubicacion ( ")
                .append("ID INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append("fecha DATETIME DEFAULT CURRENT_TIMESTAMP, ")
                .append("latitud NUMERIC NOT NULL, ")
                .append("longitud NUMERIC NOT NULL)");

        db.execSQL(ubicacion.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ubicacion");
        onCreate(db);
    }

    public void insertarUbicacion(Ubicacion ubicacion) throws Exception {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("latitud", ubicacion.getLatitud());
        contentValues.put("longitud", ubicacion.getLongitud());

        Log.d("DLC", "Latitud: " + ubicacion.getLatitud() + "Longitud: " + ubicacion.getLongitud());

        db.insert("ubicacion", null, contentValues);
    }

    public Cursor obtenerInformacion(SQLiteDatabase db) {
        String columnasUbicacion[] = {"fecha", "latitud", "latitud"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT fecha, latitud, latitud FROM ubicacion");

        Cursor cursor = db.query("ubicacion", columnasUbicacion, null, null, null, null, null);
        return cursor;
    }
}
