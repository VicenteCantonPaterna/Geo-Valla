package com.example.vicente.geovalla;

import android.graphics.drawable.Drawable;
import android.os.Vibrator;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.Menu;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.Location;
import android.location.LocationProvider;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;


public class MainActivity extends ActionBarActivity {

    private LocationManager locManager;
    private LocationListener locListener;
    private double Latitud;
    private double Longitud;
    private double RadioNormal;
    private double RadioEmergencia;
    private Context context;
    private SharedPreferences prefs;
    private ImageView imageSMS;
    private ImageView imageGPS;
    private ImageView imageRango;
    private Button configButt;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        imageSMS = (ImageView) findViewById(R.id.smsImage);
        imageGPS = (ImageView) findViewById(R.id.gpsImage);
        imageRango = (ImageView) findViewById(R.id.rangeImage);
        configButt = (Button) findViewById(R.id.BtnConfig);

        v = (Vibrator) getSystemService(getApplicationContext().VIBRATOR_SERVICE);
        prefs = getSharedPreferences("MisPreferencias",Context.MODE_PRIVATE);
        Latitud = Double.longBitsToDouble(prefs.getLong("Latitud", (long) 0.5));
        Longitud = Double.longBitsToDouble(prefs.getLong("Longitud", (long) 0.5));
        RadioNormal = prefs.getFloat("RadioNormal", (float) 0.5);
        RadioEmergencia = prefs.getFloat("RadioEmergencia", (float) 0.5);

        if (prefs.getBoolean("Notificaciones",false)){
            imageSMS.setImageResource(R.drawable.smsactivo);
        }else{
            imageSMS.setImageResource(R.drawable.smsdesactivo);
        }
        comenzarLocalizacion();

        configButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =
                        new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            Intent intent =
                    new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    private void comenzarLocalizacion()
    {
        //Obtenemos una referencia al LocationManager
        locManager =
                (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //Obtenemos la última posición conocida

        Location loc =
                locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                comprobarLocalizacion(location);
            }
            public void onProviderDisabled(String provider){
                context = getApplicationContext();
                CharSequence text = "GPS provider disabled";
                imageGPS.setImageResource(R.drawable.gpsoff);
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            public void onProviderEnabled(String provider){
                context = getApplicationContext();
                CharSequence text = "GPS provider enabled";
                int duration = Toast.LENGTH_SHORT;
                imageGPS.setImageResource(R.drawable.gpson);

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            public void onStatusChanged(String provider, int status, Bundle extras){
                switch (status) {
                    case LocationProvider.OUT_OF_SERVICE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Out of Service",
                                Toast.LENGTH_SHORT).show();
                        imageGPS.setImageResource(R.drawable.gpsoff);
                        break;
                    case LocationProvider.TEMPORARILY_UNAVAILABLE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Temporarily Unavailable",
                                Toast.LENGTH_SHORT).show();
                        imageGPS.setImageResource(R.drawable.gpsoff);
                        break;
                    case LocationProvider.AVAILABLE:
                        Toast.makeText(getApplicationContext(), "Status Changed: Available",
                                Toast.LENGTH_SHORT).show();
                        imageGPS.setImageResource(R.drawable.gpson);
                        break;
                }
            }
        };

        locManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 30000, 0, locListener);
    }

    private void comprobarLocalizacion(Location loc) {

        long latitud = Double.doubleToLongBits(loc.getLatitude());
        long longitud = Double.doubleToLongBits(loc.getAltitude());

        double LatActual = Double.longBitsToDouble(latitud);
        double LonActual = Double.longBitsToDouble(longitud);


        if (prefs.getBoolean("Notificaciones",true)) {
            if ((Latitud - LatActual) > RadioNormal / 111000 ||
                    ((Longitud - LonActual) > RadioNormal / 111000)) {
                if ((Latitud - LatActual) > RadioEmergencia / 111000 ||
                        ((Longitud - LonActual) > RadioEmergencia / 111000)) {
                    //Envio codigo de aviso leve
                    imageRango.setImageResource(R.drawable.fuera);
                    v.vibrate(1000);
                } else {
                    //Envio codigo de emergencia
                    //SmsManager sm = SmsManager.getDefault();
                    //String number = Integer.toString(prefs.getInt("Numero",0));
                    //String msg = "El usuario de la aplicacion ha salido del radio establecido";
                    //sm.sendTextMessage(number,null,msg,null,null);
                    imageRango.setImageResource(R.drawable.fuera);
                }
            }else{
                imageRango.setImageResource(R.drawable.dentro);
            }
        }
    }

}
