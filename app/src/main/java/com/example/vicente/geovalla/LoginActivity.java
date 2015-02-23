package com.example.vicente.geovalla;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by Vicente on 21/02/2015.
 */
public class LoginActivity extends Activity{

    private String user;
    private String pass;
    private EditText UserTxt;
    private EditText PassTxt;
    private Button BtnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        SharedPreferences prefs =
                getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        user = prefs.getString("Usuario",null);
        pass = prefs.getString("Password",null);

        UserTxt = (EditText)findViewById(R.id.TxtUsuario);
        PassTxt = (EditText)findViewById(R.id.TxtPassword);
        BtnLogin = (Button)findViewById(R.id.BtnAceptar);

        BtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean Accept = comprobarCredenciales();

                if (Accept){
                    Intent intent = new Intent(LoginActivity.this,ConfigurationActivity.class);

                    startActivity(intent);
                }else{
                    UserTxt.setHint("Usuario");
                    PassTxt.setHint("Password");
                    Toast.makeText(getApplicationContext(), "Usuario o password erroneos",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean comprobarCredenciales(){

        String UserTyped = UserTxt.getText().toString();
        String PassTyped = PassTxt.getText().toString();

        if (UserTyped.equals(user) && PassTyped.equals(pass)){
            return true;
        }else{
            return false;
        }
    }


}
