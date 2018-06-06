package com.example.admin.whereaunow;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import pl.polidea.view.ZoomView;

public class MainActivity extends AppCompatActivity {
    String TAG = "tiger";
    //테스트용 전역함수 추후 삭제
    EditText startX;
    EditText startY;
    EditText goalX;
    EditText goalY;
    LinearLayout testll;
    NaviCanvas nc;
    //-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //확대축소 가능하게해주는 ZoomView사용
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(2f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        zoomView.setMiniMapCaption("확인창"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.addView(zoomView);

    //-------------코드시작-------------------------------
        //테스트용 코드--------------------------------------
        startX = findViewById(R.id.startTextX);
        startY = findViewById(R.id.startTextY);
        goalX = findViewById(R.id.goalTextX);
        goalY = findViewById(R.id.goalTextY);
        testll = findViewById(R.id.testll);
        nc = findViewById(R.id.line);
    }

    public void test1(View view) {
        Log.d(TAG, "onTouch: DOWN");
        int sX = Integer.parseInt(startX.getText().toString());
        int sY = Integer.parseInt(startY.getText().toString());
        int gX = Integer.parseInt(goalX.getText().toString());
        int gY = Integer.parseInt(goalY.getText().toString());
        //NaviCanvas로 보내기위한
        nc.setsX(sX); nc.setsY(sY); nc.setgX(gX); nc.setgY(gY);
        nc.invalidate();
    }    //--------테스트코드끝-------------------------------
}
