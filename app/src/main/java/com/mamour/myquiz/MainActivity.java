package com.mamour.myquiz;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import com.mamour.myquiz.Model.Client;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText firstname;
    private EditText name;
    private EditText password;
    private EditText secondpassword;
    private EditText mail;
    private TextView linkregister;
    private Button btn;
    private RadioButton radiomale;
    private RadioButton radiofemale;
    private RadioGroup radiogroup;
    private RadioButton radioButton;
    Realm realm;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname = findViewById(R.id.firstname);
        name = findViewById(R.id.name);
        mail = findViewById(R.id.email);
        password = findViewById(R.id.password);
        secondpassword = findViewById(R.id.secondpassword);
        btn = findViewById(R.id.defaultbtn);
        linkregister = findViewById(R.id.linkregister);
        radiofemale = findViewById(R.id.radiofemale);
        radiomale = findViewById(R.id.radiomale);
        radiogroup = findViewById(R.id.radiogroup);
        realm = Realm.getDefaultInstance();
        btn.setOnClickListener(this);




        btn.setOnClickListener(new View.OnClickListener(){

           @Override
           public void onClick(View view){
               // get selected radio button from radioGroup
               int selectedId = radiogroup.getCheckedRadioButtonId();
               radioButton = findViewById(selectedId);

               if(firstname.getText().toString().trim().length() == 0) {
                   Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre prenom", Toast.LENGTH_LONG).show();
                   return;
               }
               if(name.length() <= 2){
                   name.setError("Votre nom est trop court");
                   return;
               }
               if(name.getText().toString().trim().length() == 0) {
                   Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre nom", Toast.LENGTH_LONG).show();
                   return;
               }
               if(mail.getText().toString().trim().length() == 0 ) {
                   Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre email", Toast.LENGTH_LONG).show();
                   return;
               }
               if (!android.util.Patterns.EMAIL_ADDRESS.matcher(mail.getText().toString().trim()).matches()) {
                   mail.setError("Adresse email invalide");
                   return;
               }
               if(password.getText().toString().trim().length() == 0) {
                   Toast.makeText(getApplicationContext(), "S'il vous plait entrez votre mot de passe", Toast.LENGTH_LONG).show();
                   return;
               }
               if(password.length() < 6){
                   password.setError("Votre mot de passe est trop court");
                   return;
               }
               if(secondpassword.getText().toString().trim().length() == 0) {
                   Toast.makeText(getApplicationContext(), "S'il vous plait confirmez votre mot de passe", Toast.LENGTH_LONG).show();
                   return;
               }
               if(!password.getText().toString().equals(secondpassword.getText().toString())){
                   secondpassword.setError("Les deux mots de passes ne sont pas les memes");
                   return;
               }
               if(selectedId == -1){
                   Toast.makeText(getApplicationContext(), "S'il vous plait selectionnez votre sexe !", Toast.LENGTH_LONG).show();
                   return;
               }
               else{

                writeToDB(firstname.getText().toString().trim(),name.getText().toString().trim(),mail.getText().toString().trim(),password.getText().toString().trim()
                ,secondpassword.getText().toString().trim());
                   endGame();

               }


           }

        });

        linkregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //user clicked the button
                Intent loginActivityIntent = new Intent(MainActivity.this, loginActivity.class);
                startActivity(loginActivityIntent);
                finish();

            }
        });





    }




    @Override
    protected void onDestroy(){
        super.onDestroy();
        realm.close();
    }

    public void writeToDB(final String firstname, final String lastname, final String email, final String password, final String secondpassword){
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Client user = bgRealm.createObject(Client.class);
                user.setFistname(firstname);
                user.setLastname(lastname);
                user.setEmailid(email);
                user.setPassword(password);
                user.setSeconpassword(secondpassword);
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Log.v("Database", "Data inserted");
            }



        });


    }





    private void endGame() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Inscription reussi!")
                .setMessage("Bienvenue " + firstname.getText().toString()+" "+"Connetez vous a present !")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // End the activity
                        Intent loginActivityIntent = new Intent(MainActivity.this, loginActivity.class);
                        startActivity(loginActivityIntent);
                        finish();
                    }
                })
                .setCancelable(false)
                .create()
                .show();
    }



    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }




}
