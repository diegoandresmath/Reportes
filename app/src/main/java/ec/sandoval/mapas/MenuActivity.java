package ec.sandoval.mapas;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ec.sandoval.srv.DialogoReporte;

public class MenuActivity extends AppCompatActivity {
    private Button btnMapa, btnCoordenadas, btnReporte, btnReporteOpcional;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        btnLanzarMapas();
        btnCoordenadas();
        btnReporte();
        btnReporteOpcional();
    }

    public void btnLanzarMapas() {
        Log.d("DLC", "MenuActivity.btnLanzarMapas");
        btnMapa = (Button) findViewById(R.id.btnMapas);
        btnMapa.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "MenuActivity.btnLanzarMapas.onClick -> antes");
                        //startActivity(new Intent(MainActivity.this,MapasActivity.class));
                        Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "MenuActivity.btnLanzarMapas.onClick -> despues");
                    }
                }
        );
    }

    public void btnCoordenadas() {
        Log.d("DLC", "MenuActivity.btnCoordenadas");
        btnCoordenadas = (Button) findViewById(R.id.btnCoordenadas);
        btnCoordenadas.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "MenuActivity.btnCoordenadas.onClick -> antes");
                        //startActivity(new Intent(MainActivity.this,MapasActivity.class));
                        Intent intent = new Intent(getApplicationContext(), CoordenadasActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "MenuActivity.btnCoordenadas.onClick -> despues");
                    }
                }
        );
    }

    public void btnReporte() {
        Log.d("DLC", "MainActivity.btnReporte");
        btnReporte = (Button) findViewById(R.id.btnReporte);
        btnReporte.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "MainActivity.btnReporte.onClick");
                        //DialogoReporte dialogReport = new DialogoReporte();
                        //dialogReport.show(MenuActivity.this.getSupportFragmentManager(),"NoticeDialogFragment");
                        Intent intent = new Intent(getApplicationContext(), RangoActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    public void btnReporteOpcional() {
        Log.d("DLC", "MenuActivity.btnReporteOpcional");
        btnReporteOpcional = (Button) findViewById(R.id.btnReporteOpcional);
        btnReporteOpcional.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "MenuActivity.btnReporteOpcional.onClick -> antes");
                        //startActivity(new Intent(MainActivity.this,MapasActivity.class));
                        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "MenuActivity.btnReporteOpcional.onClick -> despues");
                    }
                }
        );
    }
}
