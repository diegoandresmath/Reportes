package ec.sandoval.mapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.analytics.CampaignTrackingReceiver;
import com.google.android.gms.analytics.HitBuilders;

import ec.sandoval.ent.Ubicacion;
import ec.sandoval.srv.DataBase;

public class CoordenadasActivity extends AppCompatActivity {
    private Button btnRegresarMenu, btnGuardar;
    private EditText txtLat, txtLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coordenadas);

        txtLat = (EditText) findViewById(R.id.txtLatitud);
        txtLong = (EditText) findViewById(R.id.txtLongitud);

        btnRegresarMenu();
        btnGuardarCoordenadas();
    }

    public void btnRegresarMenu() {
        Log.d("DLC", "CoordenadasActivity.btnLanzarDialogo");
        btnRegresarMenu = (Button) findViewById(R.id.btnRegresarMapas);
        btnRegresarMenu.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "CoordenadasActivity.btnLanzarMapas.onClick -> antes");
                        //startActivity(new Intent(MainActivity.this,MapasActivity.class));
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "CoordenadasActivity.btnLanzarMapas.onClick -> despues");
                    }
                }
        );
    }

    public void btnGuardarCoordenadas() {
        btnGuardar = (Button) findViewById(R.id.btnGuardarCoordenadas);
        btnGuardar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        registrarCoordenadas();
                        startActivity(new Intent(CoordenadasActivity.this, MenuActivity.class));
                    }
                }
        );
    }

    private void registrarCoordenadas() {
        Ubicacion ubicacion = new Ubicacion();
        ubicacion.setLatitud(Float.parseFloat(txtLat.getText().toString()));
        ubicacion.setLongitud(Float.parseFloat(txtLong.getText().toString()));
        Log.d("DLC registrar", "Latitud: " + ubicacion.getLatitud() + " Longitud: " + ubicacion.getLongitud());

        DataBase dataBase = new DataBase(this);

        try {
            dataBase.insertarUbicacion(ubicacion);
            dataBase.close();
            Toast.makeText(getApplicationContext(), "Coordenadas OK.", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.d("DLC", "Error" + e.getMessage());
            Toast.makeText(getApplicationContext(), "Coordenadas FAIL.", Toast.LENGTH_SHORT).show();
        }

    }

//    private void capturarCoordenadas(Ubicacion ubicacion) {
//        ubicacion.setLatitud(Float.parseFloat(txtLat.getText().toString()));
//        ubicacion.setLongitud(Float.parseFloat(txtLong.getText().toString()));
//        Log.d("DLC", "Latitud: " + ubicacion.getLatitud() + " Longitud: " + ubicacion.getLongitud());
//    }
}
