package com.example.vicente.geovalla;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by Vicente on 22/02/2015.
 */
public class ChangeUsuarioActivity extends Activity {

    private EditText TxtNombre;
    private EditText TxtContrasenia;
    private Button BtnAceptar;

    //SHARED PREFERENCES
    private SharedPreferences prefs;

    //NECESARIO PARA MODIFICAR LOS STRINGS
    private SharedPreferences.Editor editor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        TxtNombre = (EditText) findViewById(R.id.TxtUsuario);
        TxtContrasenia = (EditText) findViewById(R.id.TxtPassword);
        BtnAceptar = (Button) findViewById(R.id.BtnAceptar);

        BtnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //LOS DATOS SE GUARDAN EN UserPass, prefs se usa para sobreescribir UserPass
                prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


                //COMO OBTENER EL VALOR DE user y pass
                String name = TxtNombre.getText().toString();
                String pass = TxtContrasenia.getText().toString();

                //COMO SOBREESCRIBIR user y pass
                editor = prefs.edit();
                editor.putString("Usuario", name);
                editor.putString("Password", pass);
                editor.commit();

                Toast.makeText(getApplicationContext(), "Usuario y pass cambiadas",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
