package com.example.vicente.geovalla;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.content.SharedPreferences;
import android.widget.Toast;

/**
 * Created by Vicente on 21/02/2015.
 */
public class ChangeContactActivity extends Activity{

    private String Contacto;
    private int Numero;
    private EditText TxtNombre;
    private EditText TxtNumero;
    private Button BtnCambiarContacto;

    //SHARED PREFERENCES
    private SharedPreferences prefs;

    //NECESARIO PARA MODIFICAR LOS STRINGS
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacto);

        TxtNombre = (EditText) findViewById(R.id.editNombre);
        TxtNumero = (EditText) findViewById(R.id.editTlf);
        BtnCambiarContacto = (Button) findViewById(R.id.BtnAceptar);

        BtnCambiarContacto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                ///////////////////////// SHARED PREFERENCES

                //LOS DATOS SE GUARDAN EN UserPass, prefs se usa para sobreescribir UserPass
                prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);


                //COMO OBTENER EL VALOR DE user y pass
                String name = TxtNombre.getText().toString();
                String contact = TxtNumero.getText().toString();
                int number = Integer.parseInt(contact);

                //COMO SOBREESCRIBIR user y pass
                editor = prefs.edit();
                editor.putString("Contacto", name);
                editor.putInt("Numero", number);
                editor.commit();
                Toast.makeText(getApplicationContext(), prefs.getString("Contacto","juan"),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
