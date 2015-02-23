package com.example.vicente.geovalla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Vicente on 21/02/2015.
 */
public class ChangeCenterActivity extends Activity {

    private LocationManager locManager;
    private LocationListener locListener;
    private Location loc;
    private EditText TxtLat;
    private EditText TxtLong;
    private Button BtnCambiarPosicion;
    private Button BtnObtenerPosicion;
    private Context context;

    //SHARED PREFERENCES
    private SharedPreferences prefs;

    //NECESARIO PARA MODIFICAR LOS STRINGS
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.position);
        context = getApplicationContext();

        TxtLat = (EditText) findViewById(R.id.editLat);
        TxtLong = (EditText) findViewById(R.id.editLong);
        BtnCambiarPosicion = (Button) findViewById(R.id.BtnCambiarPosition);
        BtnObtenerPosicion = (Button) findViewById(R.id.BtnObtenerPosition);
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        BtnCambiarPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //COMO OBTENER EL VALOR DE user y pass
                String Lat = TxtLat.getText().toString();
                String Alt = TxtLong.getText().toString();
                float LatParsed= Float.parseFloat(Lat);
                float AltParsed= Float.parseFloat(Alt);

                //COMO SOBREESCRIBIR user y pass
                editor = prefs.edit();
                editor.putFloat("Latitud", LatParsed);
                editor.putFloat("Longitud", AltParsed);
                editor.commit();
                Toast.makeText(getApplicationContext(), (int) prefs.getFloat("Latitud", (float) 45.0),
                        Toast.LENGTH_LONG).show();
            }
        });

        BtnObtenerPosicion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///////////////////////// SHARED PREFERENCES
                comenzarLocalizacion();
            }
        });
    }

    private void comenzarLocalizacion() {

        //Obtenemos una referencia al LocationManager
        context = getApplicationContext();
        locManager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida

        loc = locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        long lat = Double.doubleToLongBits(loc.getLatitude());
        long lon = Double.doubleToLongBits(loc.getAltitude());

        editor = prefs.edit();
        editor.putLong("Latitud", lat);
        editor.putLong("Longitud", lon);
        editor.commit();

        locListener = new LocationListener() {

            public void onLocationChanged(Location location) {

                long lat = Double.doubleToLongBits(location.getLatitude());
                long lon = Double.doubleToLongBits(location.getAltitude());

                editor = prefs.edit();
                editor.putLong("Latitud", lat);
                editor.putLong("Longitud", lon);
                editor.commit();

            }

            public void onProviderDisabled(String provider) {
                CharSequence text = "GPS provider disabled";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            public void onProviderEnabled(String provider) {
                context = getApplicationContext();
                CharSequence text = "GPS provider enabled";
                int duration = Toast.LENGTH_LONG;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Out of Service",
                                Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Temporarily Unavailable",
                                Toast.LENGTH_LONG).show();
                        break;
                    case LocationProvider.AVAILABLE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Available",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };
        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, locListener);
        Intent intent = new Intent(ChangeCenterActivity.this,SplashActivity.class);
        locManager.removeUpdates(locListener);
        startActivity(intent);
        finish();
    }

}
