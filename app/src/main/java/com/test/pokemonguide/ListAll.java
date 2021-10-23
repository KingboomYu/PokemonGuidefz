package com.test.pokemonguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAll extends AppCompatActivity implements AdapterView.OnItemClickListener{
    private static final String TAG = "ListAll";
    Handler handler;
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all);
        list = findViewById(R.id.list);
        handler = new Handler(Looper.myLooper()){
            @Override
            public void handleMessage(Message msg){
                if(msg.what==12){
                    ArrayList<HashMap<String,String>> listItems = (ArrayList<HashMap<String,String>>)msg.obj;
                    Log.i(TAG, "handleMessage: listItems"+listItems);

                    SimpleAdapter listAdapter = new SimpleAdapter(
                            ListAll.this,
                            listItems,
                            R.layout.list_item,
                            new String[]{"pokeNo","pokeCN","pokeEN","pokeJP"},
                            new int[]{R.id.pokeNo,R.id.pokeCN,R.id.pokeEN,R.id.pokeJP});
                    list.setAdapter(listAdapter);
                }
                super.handleMessage(msg);
            }
        };

        GetWeb dt=new GetWeb(handler);
        Thread t=new Thread(dt);
        t.start();

        list.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        TextView pokeNo = (TextView) view.findViewById(R.id.pokeNo);
        TextView pokeCN = (TextView) view.findViewById(R.id.pokeCN);
        TextView pokeEN = (TextView) view.findViewById(R.id.pokeEN);
        String pokeNo1 = String.valueOf(pokeNo.getText());
        String pokeCN1 = String.valueOf(pokeCN.getText());
        String pokeEN1 = String.valueOf(pokeEN.getText());
        Log.i(TAG, "ListAll: pokeNo:"+pokeNo1);
        Log.i(TAG, "ListAll: pokeCN:"+pokeCN1);
        Log.i(TAG, "ListAll: pokeEN:"+pokeEN1);
        Intent config = new Intent(this,PicTest.class);
        Bundle bdl = new Bundle();
        bdl.putString("pokeNo",pokeNo1);
        bdl.putString("pokeCN",pokeCN1);
        bdl.putString("pokeEN",pokeEN1);
        config.putExtras(bdl);
        startActivityForResult(config,1);
    }
}