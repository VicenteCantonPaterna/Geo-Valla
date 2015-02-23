package com.example.vicente.geovalla;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.*;
import android.content.Intent;

/**
 * Created by Vicente on 20/02/2015.
 */

public class SplashActivity extends Activity{

    private SharedPreferences prefs;

    private final int DURACION_SPLASH = 3000;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        // Tenemos una plantilla llamada splash.xml donde mostraremos la información que queramos (logotipo, etc.)
        setContentView(R.layout.splash);

        new Handler().postDelayed(new Runnable(){
            public void run(){
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                if (prefs.getString("Usuario",null) != null){
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(SplashActivity.this, ConfigurationActivity.class);
                    startActivity(intent);
                }

                finish();
        };
        }, DURACION_SPLASH);
    }
}