package ec.sandoval.srv;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.Toast;

import ec.sandoval.ent.Ubicacion;
import ec.sandoval.mapas.R;

/**
 * Created by ddelacruz on 23/10/2016.
 */

public class BackGroudTask extends AsyncTask<String, Ubicacion, String> {
    Context context;
    UbicacionAdapter ubicacionAdapter;
    Activity activity;
    ListView listView;

    public BackGroudTask(Context context) {
        this.context = context;
        activity = (Activity) context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... params) {
        String metodo = params[0];
        DataBase dataBase = new DataBase(context);

        if (metodo.equals("get_info")) {
            listView = (ListView) activity.findViewById(R.id.lvRuta);
            SQLiteDatabase db = dataBase.getReadableDatabase();
            Cursor cursor = dataBase.obtenerInformacion(db);
            ubicacionAdapter = new UbicacionAdapter(context, R.layout.report_ruta);
            String fecha;
            Float latitud, longitud;

            while (cursor.moveToNext()) {
                fecha = cursor.getString(cursor.getColumnIndex("fecha"));
                latitud = cursor.getFloat(cursor.getColumnIndex("latitud"));
                longitud = cursor.getFloat(cursor.getColumnIndex("longitud"));

                Ubicacion ubicacion = new Ubicacion(fecha, latitud, longitud);
                publishProgress(ubicacion);
            }
            return "get_info";

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Ubicacion... values) {
        ubicacionAdapter.add(values[0]);

    }

    @Override
    protected void onPostExecute(String s) {
        if (s.equals("get_info")) {
            listView.setAdapter(ubicacionAdapter);
        } else {
            Toast.makeText(context, s, Toast.LENGTH_LONG).show();
        }
    }
}
