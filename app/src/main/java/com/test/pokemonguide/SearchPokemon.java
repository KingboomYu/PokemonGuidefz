package com.test.pokemonguide;

import android.content.Intent;
import android.os.Bundle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;


public class SearchPokemon extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "SearchPokemon";
    Handler handler;
    ListView list;
    SearchView mSearchView = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_pokemon);
        list = findViewById(R.id.listView);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==12){
                    ArrayList<HashMap<String,String>> listItems = (ArrayList<HashMap<String,String>>)msg.obj;
                    Log.i(TAG, "handleMessage: listItems"+listItems);
                    SimpleAdapter listAdapter = new SimpleAdapter(
                            SearchPokemon.this,
                            listItems,
                            R.layout.list_item2,
                            new String[]{"pokeCN"},
                            new int[]{R.id.pokeCN});
                    list.setAdapter(listAdapter);
                }
                super.handleMessage(msg);
            }
        };

        GetWeb dt=new GetWeb(handler);
        Thread t=new Thread(dt);
        t.start();

        list.setOnItemClickListener(this);
        mSearchView = (SearchView) findViewById(R.id.searchView);
        list = (ListView) findViewById(R.id.listView);
        list.setTextFilterEnabled(true);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            // 当点击搜索按钮时触发该方法
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            // 当搜索内容改变时触发该方法
            @Override
            public boolean onQueryTextChange(String newText) {
                if (!TextUtils.isEmpty(newText)){
                    list.setFilterText(newText);
                }else{
                    list.clearTextFilter();
                }
                return false;
            }
        });
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

        TextView pokeCN = (TextView) view.findViewById(R.id.pokeCN);
        String pokeCN1 = String.valueOf(pokeCN.getText());
        Log.i(TAG, "ListAll: pokeCN:"+pokeCN1);
        Intent config = new Intent(this,PicTest.class);
        Bundle bdl = new Bundle();
        bdl.putString("pokeCN",pokeCN1);
        config.putExtras(bdl);
        startActivityForResult(config,1);
    }

}