package com.mamour.myquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import com.mamour.myquiz.Model.Client;
import com.mamour.myquiz.adapter.ClientAdapter;
import java.util.ArrayList;
import java.util.List;
import io.realm.Realm;
import io.realm.RealmResults;


public class ViewDatabase extends AppCompatActivity {
    private RealmResults<Client> resultclient;
    Realm realm;
    private RecyclerView.LayoutManager layoutManager;
    RecyclerView customer;
    ClientAdapter adapter;
    List<Client> clientList;
    Client client;
    Button btn;
    SearchView searchView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_database);
        customer = findViewById(R.id.myclient);
        layoutManager = new LinearLayoutManager(this);
        customer.setLayoutManager(layoutManager);

        try {
            realm = Realm.getDefaultInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        clientList = new ArrayList<>();
        clientList = getAllClient();

        adapter = new ClientAdapter(this, clientList);
        customer.setAdapter(adapter);



    }


    public List<Client> getAllClient() {
        RealmResults<Client> clientresult = realm.where(Client.class).findAll();
        return clientresult;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        final MenuItem disconnect = menu.findItem(R.id.action_disconnect);
        disconnect.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPreferences preferences = getSharedPreferences("loginPrefs",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                System.out.println(preferences.getString("username",""));
                System.out.println(preferences.getString("password",""+"null"));
                Intent loginActivityIntent = new Intent(ViewDatabase.this, loginActivity.class);
                startActivity(loginActivityIntent);
                return true;
            }
        });
        searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified()){
                    searchView.setIconified(true);
                }
                searchItem.collapseActionView();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Client> filtermodellist = filter(clientList,newText);
                adapter.setFilter(filtermodellist);
                return true;
            }
        });
        return true;
    }
    private List<Client> filter(List<Client> pl,String query){
        query = query.toLowerCase();
        final List<Client> filtredModelList = new ArrayList<>();
        for (Client model:pl){
            final String text = model.getFistname().toLowerCase();
            final String text1 = model.getEmailid().toLowerCase();
            System.out.println(model.getFistname());
            if(text.contains(query) || text1.contains(query)){
                filtredModelList.add(model);
            }
        }
        return filtredModelList;
    }
}

