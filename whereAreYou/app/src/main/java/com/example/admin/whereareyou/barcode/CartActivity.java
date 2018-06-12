package com.example.admin.whereareyou.barcode;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.admin.whereareyou.R;
import com.example.admin.whereareyou.cartlist.CartAdapter;
import com.example.admin.whereareyou.cartlist.CartInfo;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity{
    String TAG = "tiger";
    ListView listView;
    //---------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        //--------------------------------------
        listView = findViewById(R.id.listview);
        ArrayList<CartInfo> al = new ArrayList<>();
       al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));
        al.add(new CartInfo(R.drawable.bread_paris, R.drawable.bread_paris, "5,000", "빵쪼가리"));

        CartAdapter adapter = new CartAdapter(this, R.layout.list_goods, al);
        listView.setAdapter(adapter);


    }
}
