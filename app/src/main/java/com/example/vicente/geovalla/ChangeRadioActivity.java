package com.example.vicente.geovalla;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Vicente on 22/02/2015.
 */
public class ChangeRadioActivity extends Activity{

    private float radioNormal;
    private float radioEmergencia;
    private Button BtnAceptar;
    private TextView TxtRadioNormal;
    private TextView TxtRadioEmergencia;

    SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.radio);

        TxtRadioNormal = (TextView) findViewById(R.id.editRadioNormal);
        TxtRadioEmergencia = (TextView) findViewById(R.id.editRadioEmergencia);
        BtnAceptar = (Button) findViewById(R.id.BtnAceptar);
        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();

        BtnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioNormal = Float.parseFloat(TxtRadioNormal.getText().toString());
                radioEmergencia = Float.parseFloat(TxtRadioEmergencia.getText().toString());
                editor.putFloat("RadioNormal", radioNormal);
                editor.putFloat("RadioEmergencia", radioEmergencia);
                editor.commit();
                Toast.makeText(getApplicationContext(), "OK",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}
