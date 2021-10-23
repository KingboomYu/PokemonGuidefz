package com.test.pokemonguide;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetWeb implements Runnable{
    private static final String TAG = "Task";
    Handler handler;

    public GetWeb(Handler handler){
        this.handler=handler;
    }
    ArrayList listItems = new ArrayList<HashMap<String,String>>();

    public void run(){

        Log.i(TAG, "run: run....");

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Document doc = Jsoup.connect("https://wiki.52poke.com/wiki/%E5%AE%9D%E5%8F%AF%E6%A2%A6%E5%88%97%E8%A1%A8%EF%BC%88%E6%8C%89%E5%85%A8%E5%9B%BD%E5%9B%BE%E9%89%B4%E7%BC%96%E5%8F%B7%EF%BC%89/%E7%AE%80%E5%8D%95%E7%89%88").get();
            Log.i(TAG, "run: tittle="+doc.title());
            Elements tables = doc.getElementsByTag("table");
            //获取第3张表
            Element gettable = tables.get(2);
            Elements trs = gettable.getElementsByTag("tr");
            for(int i=0;i<=2;i++){
                trs.remove(0);
            }
            trs.remove(151);
            trs.remove(251);
            trs.remove(386);
            trs.remove(493);
            trs.remove(649);
            trs.remove(721);
            trs.remove(809);

            //Log.i(TAG, "run: tr:"+trs);
            for(Element tr:trs){
                HashMap<String,String> map = new HashMap<String,String>();
                Elements tds = tr.getElementsByTag("td");
                String pokeNo = tds.get(0).text();
//                String pokeCN = tds.get(1).getElementsByTag("title").text();
//                String pokeEN = tds.get(3).getElementsByTag("title").text();
//                String pokeJP = tds.get(2).getElementsByTag("title").text();
                String pokeCN = tds.get(1).text();
                String pokeEN = tds.get(3).text();
                String pokeJP = tds.get(2).text();
                //Log.i(TAG, "run: pokeCN:"+pokeCN);
                map.put("pokeNo",pokeNo);
                map.put("pokeCN",pokeCN);
                map.put("pokeEN",pokeEN);
                map.put("pokeJP",pokeJP);
                listItems.add(map);
                //Log.i(TAG, "run: map:"+map);
            }
            //Log.i(TAG, "run: listItem:"+listItems);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Message msg = null;
        try {
            msg = handler.obtainMessage(12);
            msg.obj = listItems;
            handler.sendMessage(msg);
            Log.i(TAG, "run: run:消息已经发送");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
