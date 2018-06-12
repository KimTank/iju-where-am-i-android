package com.example.admin.whereareyou;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.admin.whereareyou.barcode.CartActivity;
import com.example.admin.whereareyou.barcode.GoodsActivity;
import com.example.admin.whereareyou.barcode.QrcreateActivity;
import com.github.clans.fab.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
    FloatingActionButton menu_qrCreate, menu_qrScan, menu_qrCart;
    //-----------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //-------Floating버튼에 intent연결
        menu_qrCart = findViewById(R.id.menu_cart);
        menu_qrCreate = findViewById(R.id.menu_qrcreate);
        menu_qrScan = findViewById(R.id.menu_qrscan);
        menu_qrCreate.setOnClickListener(event);
        menu_qrScan.setOnClickListener(event);
        menu_qrCart.setOnClickListener(event);

        //-----------------------------------

        //확대축소 가능하게해주는 ZoomView사용//만약 focus로 가는곳을 지정하지 못한다면 SurfaceView로 제어할수도 있는거 같음
        View v = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.zoom_item, null, false);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        ZoomView zoomView = new ZoomView(this);
        zoomView.addView(v);
        zoomView.setLayoutParams(layoutParams);
        //zoomView.setMiniMapEnabled(true); // 좌측 상단 검은색 미니맵 설정
        zoomView.setMaxZoom(2f); // 줌 Max 배율 설정  1f 로 설정하면 줌 안됩니다.
        //zoomView.setMiniMapCaption("확인창"); //미니 맵 내용
        zoomView.setMiniMapCaptionSize(20); // 미니 맵 내용 글씨 크기 설정

        RelativeLayout container = (RelativeLayout) findViewById(R.id.container);
        container.addView(zoomView);
        //-------------코드시작-------------------------------
        //테스트용 코드--------------------------------------
        /*startX = findViewById(R.id.startTextX);
        startY = findViewById(R.id.startTextY);
        goalX = findViewById(R.id.goalTextX);
        goalY = findViewById(R.id.goalTextY);
        testll = findViewById(R.id.testll);*/
        nc = findViewById(R.id.line);//NaviCanvas객체
    }
    //맵그려주는곳
    /*public void test1(View view) {
        Log.d(TAG, "onTouch: DOWN");
        int sX = Integer.parseInt(startX.getText().toString());
        int sY = Integer.parseInt(startY.getText().toString());
        int gX = Integer.parseInt(goalX.getText().toString());
        int gY = Integer.parseInt(goalY.getText().toString());
        //NaviCanvas로 보내기위한
        nc.setsX(sX);
        nc.setsY(sY);
        nc.setgX(gX);
        nc.setgY(gY);
        nc.invalidate();}*/
    //--------테스트코드끝-------------------------------
    View.OnClickListener event = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()) {
                //-----------------권한승인
                case R.id.menu_qrcreate :
                    TelephonyManager tel = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
                    int permissionChk = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE);
                    if(permissionChk == PackageManager.PERMISSION_DENIED) {
                        Toast.makeText(MainActivity.this, "권한을 거부할 시 마일리지 적립이 되지 않습니다.", Toast.LENGTH_LONG).show();
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
                    } else {
                        String tellNum = "";
                        try {
                            tellNum = tel.getLine1Number().toString();
                            Intent intent = new Intent(MainActivity.this,QrcreateActivity.class);
                            intent.putExtra("data",tellNum);
                            startActivity(intent);
                        } catch(Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                //QR스캔---------------------------------------------------------------------------------
                case R.id.menu_qrscan :
                    //나중에 다른 Activity로 넘어가게 만들어서 상품정보띄울 수 있어야됨
                    Log.d(TAG, "onClick: 스캔누름");
                    Intent intent1 = new Intent(MainActivity.this, GoodsActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.menu_cart :
                    Log.d(TAG, "onClick: 장바구니누름");
                    Intent intent2 = new Intent(MainActivity.this, CartActivity.class);
                    startActivity(intent2);
                    break;
            }
        }
    };
}
