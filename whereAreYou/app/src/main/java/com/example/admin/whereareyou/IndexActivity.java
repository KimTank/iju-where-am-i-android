package com.example.admin.whereareyou;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

public class IndexActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        //index화면에서 쓸 Glide->GIF넣어주는 곳
        ImageView indexGIF = findViewById(R.id.indexGIF);
        GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(indexGIF);
        Glide.with(this).load(R.raw.index).into(imageViewTarget);
        //로딩화면에서 메인화면(맵)으로 넘어가는곳
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);  // Intent 선언
                startActivity(intent);   // Intent 시작
                finish();
            }
        }, 4000);  // 로딩화면 시간
    }

}
