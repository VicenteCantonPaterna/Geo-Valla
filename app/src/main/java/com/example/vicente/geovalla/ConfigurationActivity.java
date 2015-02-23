package com.example.vicente.geovalla;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vicente on 21/02/2015.
 */
public class ConfigurationActivity extends Activity{

    private Button BtnCenter;
    private Button BtnRadio;
    private Button BtnContact;
    private Button BtnDisconnect;
    private Button BtnLogin;

    //SHARED PREFERENCES
    private SharedPreferences prefs;

    //NECESARIO PARA MODIFICAR LOS STRINGS
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuration);

        BtnCenter = (Button)findViewById(R.id.BtnCentro);
        BtnRadio = (Button)findViewById(R.id.BtnRadio);
        BtnContact = (Button)findViewById(R.id.BtnContacto);
        BtnDisconnect = (Button)findViewById(R.id.BtnDescon);
        BtnLogin = (Button) findViewById(R.id.BtnUsuario);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigurationActivity.this,ChangeUsuarioActivity.class);

                startActivity(intent);
            }
        });

        BtnCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigurationActivity.this,ChangeCenterActivity.class);

                startActivity(intent);
            }
        });

        BtnRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigurationActivity.this,ChangeRadioActivity.class);

                startActivity(intent);
            }
        });

        BtnContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConfigurationActivity.this,ChangeContactActivity.class);

                startActivity(intent);
            }
        });


        BtnDisconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
                editor = prefs.edit();

                if ((prefs.getBoolean("Notificaciones", false))){
                    editor.putBoolean("Notificaciones",false);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Notificaciones deshabilitadas",
                            Toast.LENGTH_LONG).show();
                }else{
                    editor.putBoolean("Notificaciones",true);
                    editor.commit();
                    Toast.makeText(getApplicationContext(), "Notificaciones habilitadas",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
