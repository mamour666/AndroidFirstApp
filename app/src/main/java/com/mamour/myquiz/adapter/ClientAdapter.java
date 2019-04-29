package com.mamour.myquiz.adapter;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.mamour.myquiz.MainActivity;
import com.mamour.myquiz.Model.Client;
import com.mamour.myquiz.R;
import com.mamour.myquiz.loginActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.prefs.PreferenceChangeEvent;
import java.util.prefs.PreferenceChangeListener;
import java.util.zip.Inflater;
import io.realm.Realm;
import io.realm.RealmResults;


public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyviewHolder>  {
    Realm realm;
    private List<Client> clientList;
    Context context;


    public ClientAdapter(Context context,List<Client> clientList) {
        this.context = context;
        this.clientList = clientList;
    }

    @Override
    public MyviewHolder onCreateViewHolder( ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_client_layout,parent,false);
        return new MyviewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MyviewHolder myviewHolder, final int position) {
        final Client client = clientList.get(position);
        myviewHolder.name.setText(client.getFistname());
        myviewHolder.email.setText(client.getEmailid());
        myviewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RealmResults<Client> results = realm.where(Client.class).findAll();
                Client c = results.get(position);
                String title = c.getFistname();
                // All changes to data must happen in a transaction
                realm.beginTransaction();
                // remove single match
                results.deleteFromRealm(position);
                realm.commitTransaction();

                if (results.size() == 0) {
                   // Preference.with(context).setPreLoad(false);
                }
                Toast.makeText(context, title + " est supprime de Realm", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();
            }
        });

        myviewHolder.btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View content = inflater.inflate(R.layout.edit_item, null);
                final EditText editfirstname = content.findViewById(R.id.prenom);
                final EditText editlastname = content.findViewById(R.id.nom);
                final EditText editemail = content.findViewById(R.id.editmail);
                final EditText editpassword = content.findViewById(R.id.editpassword);
                final EditText seconeditpass = content.findViewById(R.id.editsecpasswords);

                editfirstname.setText(client.getFistname());
                editlastname.setText(client.getLastname());
                editemail.setText(client.getEmailid());
                editpassword.setText(client.getPassword());
                seconeditpass.setText(client.getSeconpassword());

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setView(content)
                        .setTitle("Edit Client")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                RealmResults<Client> results = realm.where(Client.class).findAll();
                                realm.beginTransaction();
                                results.get(position).setFistname(editfirstname.getText().toString());
                                results.get(position).setLastname(editlastname.getText().toString());
                                results.get(position).setEmailid(editemail.getText().toString());
                                results.get(position).setPassword(editpassword.getText().toString());
                                results.get(position).setSeconpassword(seconeditpass.getText().toString());
                                realm.commitTransaction();
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return clientList.size();
    }

    public void setFilter(List<Client> newlist){
        clientList = new ArrayList<>();
        clientList.addAll(newlist);
        notifyDataSetChanged();
    }

    class MyviewHolder extends RecyclerView.ViewHolder{
        TextView email;
        TextView name;
        Button btn;
        Button btn2;
        public MyviewHolder(View itemView) {
            super(itemView);
            email = itemView.findViewById(R.id.clientmail);
            name = itemView.findViewById(R.id.clientname);
            btn = itemView.findViewById(R.id.delete);
            btn2 = itemView.findViewById(R.id.upgrade);
            try {
                realm = Realm.getDefaultInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }





}
