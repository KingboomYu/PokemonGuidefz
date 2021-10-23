package com.test.pokemonguide;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PicTest extends AppCompatActivity {
    private static final String TAG = "PicShow";
    TextView pokename, pokeindroduce;

    //    Glide.with(PicTest.this).load("wiki.52poke.com/wiki/File:"+pokeNo+pokeEN+".png").into(pictest1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_test);
        ImageView pictest1 = findViewById(R.id.pokepic);
        pokename = (TextView) findViewById(R.id.pokename);
        pokeindroduce = (TextView) findViewById(R.id.pokeindroduce);
        pokeindroduce.setMovementMethod(ScrollingMovementMethod.getInstance());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String pokeCN = bundle.getString("pokeCN");

        pokename.setText(pokeCN);

        try {
            Document doc = Jsoup.connect("https://wiki.52poke.com/wiki/" + pokeCN).get();
            //获取图片
            String picurl = null;
            Element img = doc.getElementsByClass("image").first();
            Log.i(TAG, "onCreate: div:" + img);
            Pattern p_src = Pattern.compile("data-url=\"(.*?)\"");
            Matcher m_src = p_src.matcher(img.toString());
            while (m_src.find()) {
                if (m_src.group(1).contains("//media.52poke.com/wiki/")) {
                    picurl = m_src.group(1);
                }
            }
            Log.i(TAG, "onCreate: pics:" + picurl);
            Glide.with(PicTest.this).load("https:/" + picurl).into(pictest1);

            //获取介绍
            String introduce = null;
            Element typ1 = doc.getElementsByClass("mw-content-ltr").first();
            Element prodc = typ1.getElementsByTag("p").get(1);
            Log.i(TAG, "onCreate: prodc:" + prodc.text());
            introduce = prodc.text();
            pokeindroduce.setText(introduce);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

