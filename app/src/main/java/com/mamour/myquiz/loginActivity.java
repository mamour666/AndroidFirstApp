package com.mamour.myquiz;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.mamour.myquiz.Model.Client;
import io.realm.Realm;


public class loginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText emailId;
    private EditText passwordId;
    private Button defaultbuttonid;
    private Button noaccountid;
    Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();

        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailId = findViewById(R.id.emailId);
        passwordId = findViewById(R.id.passwordId);
        defaultbuttonid = findViewById(R.id.defaultbtnId);
        noaccountid = findViewById(R.id.noaccount);
        noaccountid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user clicked the button
                Intent mainActivityIntent = new Intent(loginActivity.this, MainActivity.class);
                startActivity(mainActivityIntent);
                finish();
            }
        });

        defaultbuttonid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (emailId.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre email", Toast.LENGTH_LONG).show();
                    return;
                }

                if (passwordId.getText().toString().trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre mot de passe", Toast.LENGTH_LONG).show();
                }
                else {
                    check();

                }
            }
        });

    }


    private void check(){
        Client user = realm.where(Client.class).equalTo("emailid", emailId.getText().toString())
                .equalTo("password",passwordId.getText().toString())
                .findFirst();
        if(user != null){
            Intent iLogin = new Intent(this, ViewDatabase.class);
            startActivity(iLogin);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);

            builder.setTitle("Erreur")
                    .setMessage("Identifant ou mot de passe incorrect !")
                    .setPositiveButton("OK",new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();
        }
    }


    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




}