package ec.sandoval.mapas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

import ec.sandoval.ent.Ubicacion;
import ec.sandoval.exception.GPSNoHabilitadoException;
import ec.sandoval.srv.Constants;
import ec.sandoval.srv.DataBase;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, LocationListener {

    private GoogleMap mMap;
    private DataBase dataBase;
    private String inicio, fin;
    private List<Ubicacion> ubicacions;
    private TextView txtId;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.TAG_DLC, "MapsActivity.onCreate");
        this.dataBase = new DataBase(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        try {
            Log.d(Constants.TAG_DLC, "MapsActivity.onCreate.TRY");

            this.solicitarLocalizacion(this);
            //finish();

            //////this.txtId = (TextView) findViewById(R.id.idTxtCodigoPlanta);

            // Obtain the SupportMapFragment and get notified when the map is ready to be used.
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);

            //Obtener el listado de plantas
            this.ubicacions = this.dataBase.obtenerListadoUbications();

        } catch (GPSNoHabilitadoException e) {
            Log.d(Constants.TAG_DLC, "MapsActivity.onCreate.GPSNoHabilitadoException" + e);
            this.showSettingsAlert(this);
        } catch (Exception e) {
            Log.d(Constants.TAG_DLC, "MapsActivity.onCreate.Exception" + e);
            this.showErrorAlert(this);
        }

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(Constants.TAG_DLC, "MapsActivity.onMapReady");
        mMap = googleMap;

        List<LatLng> listadoPuntos = new ArrayList<>();
        LatLng posicion, posicionInicial = null;
        MarkerOptions marker = null;
        int i = 0;

        for (Ubicacion ubicacion : ubicacions) {
            posicion = new LatLng(ubicacion.getLatitud(), ubicacion.getLongitud());
            if (i++ == 0) {
                posicionInicial = posicion;
            }

            //mMap.moveCamera( CameraUpdateFactory.newLatLng(posicion) );
            mMap.setIndoorEnabled(true);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(posicionInicial, 20f));

            PolylineOptions line = new PolylineOptions().addAll(listadoPuntos).width(5).color(Color.RED);
            mMap.addPolyline(line);

            // Set a listener for marker click.
            mMap.setOnMarkerClickListener(this);
            mMap.addMarker(new MarkerOptions().position(posicion));
            listadoPuntos.add(posicion);
        }



        /*
        LatLng sydney = new LatLng(-0.2392094339, -78.51512969);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */

        //////////////////////////////////////////

    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, marker.getTitle(), Toast.LENGTH_LONG).show();

        Bundle argumentos = new Bundle();
        argumentos.putString(Constants.TAG_DLC, marker.getTitle());

        /*
        DialogPlantaFragment dialog = new DialogPlantaFragment();
        dialog.setArguments(argumentos);
        dialog.show(getSupportFragmentManager(), "DialogPlantaFragnent");
        dialog.setCancelable(false);
        */

        return false;
    }

    public void solicitarLocalizacion(Context context) throws Exception {
        Log.d(Constants.TAG_DLC, "MapsActivity.solicitarLocalizacion");

        this.locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        // getting GPS status
        Boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!isGPSEnabled) {

            // no gps provider is enabled
            throw new GPSNoHabilitadoException();

        }

        Log.d(Constants.TAG_DLC, "MapsActivity.solicitarLocalizacion.requestLocationUpdates");
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MapsActivity.MIN_TIME_BW_UPDATES, MapsActivity.MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
    }

    public void showSettingsAlert(final Context context) {
        Log.d(Constants.TAG_DLC, "MapsActivity.showSettingsAlert");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("GPS is settings");

        // Setting Dialog Message
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");

        // On pressing Settings button
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                context.startActivity(intent);
            }
        });

        // on pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }

    public void showErrorAlert(Context context) {
        Log.d(Constants.TAG_DLC, "MapsActivity.showErrorAlert");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);

        // Setting Dialog Title
        alertDialog.setTitle("DLC");

        // Setting Dialog Message
        alertDialog.setMessage("No es posible acceder a esta opción al momento.");

        // On pressing Settings button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        alertDialog.show();
    }
}
