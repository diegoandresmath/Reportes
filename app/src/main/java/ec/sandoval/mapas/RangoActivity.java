package ec.sandoval.mapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class RangoActivity extends AppCompatActivity {
    private Button btnReporteAceptar, btnReporteCancelar;
    DatePicker dpDesde, dpHasta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rango);

        btnAceptar();
        btnCancelar();
    }

    public void btnAceptar() {
        Log.d("DLC", "RangoActivity.btnAceptar");
        btnReporteAceptar = (Button) findViewById(R.id.btnReporteAceptar);
        btnReporteAceptar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "RangoActivity.btnAceptar.onClick -> antes");
                        Intent intent = new Intent(getApplicationContext(), ReportActivity.class);
                        dpDesde = (DatePicker) findViewById(R.id.datePickerDesde);
                        dpHasta = (DatePicker) findViewById(R.id.datePickerHasta);
                        SimpleDateFormat simpleDateFormatDesde = new SimpleDateFormat("yyyy-MM-dd");
                        String desde = simpleDateFormatDesde.format(new Date(dpDesde.getYear() - 1900, dpDesde.getMonth(), dpDesde.getDayOfMonth()));
                        SimpleDateFormat simpleDateFormatHasta = new SimpleDateFormat("yyyy-MM-dd");
                        String hasta = simpleDateFormatHasta.format(new Date(dpHasta.getYear() - 1900, dpHasta.getMonth(), dpHasta.getDayOfMonth()));
                        Log.d("DLC", "RangoActivity.btnAceptar.onClick.desde: " + desde);
                        Log.d("DLC", "RangoActivity.btnAceptar.onClick.hasta: " + hasta);
                        intent.putExtra("desde", desde);
                        intent.putExtra("hasta", hasta);
                        startActivity(intent);
                        Log.d("DLC", "RangoActivity.btnAceptar.onClick -> despues");
                    }
                }
        );
    }

    public void btnCancelar() {
        Log.d("DLC", "RangoActivity.btnCancelar");
        btnReporteCancelar = (Button) findViewById(R.id.btnReporteCancelar);
        btnReporteCancelar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("DLC", "RangoActivity.btnCancelar.onClick -> antes");
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        startActivity(intent);
                        Log.d("DLC", "RangoActivity.btnCancelar.onClick -> despues");
                    }
                }
        );
    }
}
