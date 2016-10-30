package ec.sandoval.srv;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        String columnasUbicacion[] = {"fecha", "latitud", "longitud"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT fecha, latitud, longitud FROM ubicacion");

        Cursor cursor = db.query("ubicacion", columnasUbicacion, null, null, null, null, null);
        return cursor;
    }

    public Cursor obtenerInformacionRango(SQLiteDatabase db, String inicio, String fin) {
        Log.d("DLC", "DataBase.obtenerInformacionRango ->");
        String columnasUbicacion[] = {"fecha", "latitud", "longitud"};
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT fecha, latitud, longitud FROM ubicacion WHERE fecha >= ")
                .append(inicio)
                .append(" AND fecha <= ")
                .append(fin);

        Cursor cursor = db.query("ubicacion", columnasUbicacion, null, null, null, null, null);
        Log.d("DLC", "DataBase.obtenerInformacionRango <-");
        return cursor;
    }

    public List<Ubicacion> obtenerListadoUbications() {
        Ubicacion ubicacion = null;
        List<Ubicacion> resultado = null;

        StringBuilder consulta = new StringBuilder();
        consulta.append("SELECT id, fecha, latitud, longitud ")
                .append("FROM ubicacion ")
                .append(" ORDER BY id");

        Log.d(Constants.TAG_DLC, "DataBase.obtenerUbications");

        try {
            Cursor cursor = this.getReadableDatabase().rawQuery(consulta.toString(), null);

            if (cursor.getCount() > 0) {

                resultado = new ArrayList<Ubicacion>();
                cursor.moveToFirst();
                while (!cursor.isClosed()) {
                    ubicacion = new Ubicacion(cursor.getInt(0), cursor.getString(1), cursor.getFloat(2), cursor.getFloat(3));
                    resultado.add(ubicacion);
                    cursor.moveToNext();
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.d(Constants.TAG_DLC, "DataBase.obtenerUbications.CATCH" + e);
        }
        return resultado;
    }
}
