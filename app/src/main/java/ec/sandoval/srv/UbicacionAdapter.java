package ec.sandoval.srv;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ec.sandoval.ent.Ubicacion;
import ec.sandoval.mapas.R;

/**
 * Created by ddelacruz on 23/10/2016.
 */

public class UbicacionAdapter extends ArrayAdapter {
    List lista = new ArrayList();

    public UbicacionAdapter(Context context, int resource) {
        super(context, resource);
    }

    public void add(Ubicacion ubicacion) {
        lista.add(ubicacion);
        super.add(ubicacion);
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return lista.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        UbicacionHolder ubicacionHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.report_ruta, parent, false);
            ubicacionHolder = new UbicacionHolder();
            ubicacionHolder.txtFecha = (TextView) row.findViewById(R.id.lblReporteFecha);
            ubicacionHolder.txtLatitud = (TextView) row.findViewById(R.id.lblReporteLatitud);
            ubicacionHolder.txtLongitud = (TextView) row.findViewById(R.id.lblReporteLongitud);

            row.setTag(ubicacionHolder);
        } else {
            ubicacionHolder = (UbicacionHolder) row.getTag();
        }

        Ubicacion ubicacion = (Ubicacion) getItem(position);

        ubicacionHolder.txtFecha.setText(ubicacion.getFecha().toString());
        ubicacionHolder.txtLatitud.setText(ubicacion.getLatitud().toString());
        ubicacionHolder.txtLongitud.setText(ubicacion.getLongitud().toString());
        return row;
    }

    static class UbicacionHolder {
        TextView txtFecha, txtLongitud, txtLatitud;
    }
}
